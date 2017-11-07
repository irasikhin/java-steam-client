package ru.ir.steam.client;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.*;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.TLSClientAuth;
import akka.stream.javadsl.Flow;
import akka.util.Timeout;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import ru.ir.steam.SteamAuthApi;
import ru.ir.steam.SteamTradeApi;
import ru.ir.steam.client.actor.SteamUserActor;
import ru.ir.steam.client.cmd.Command;
import ru.ir.steam.client.config.ConfigLoader;
import ru.ir.steam.client.http.HttpApi;
import ru.ir.steam.client.module.SteamClientModule;
import ru.ir.steam.dto.SecretData;
import ru.ir.steam.dto.SteamUser;
import ru.ir.steam.internal.SecretDataCache;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import static akka.pattern.PatternsCS.ask;

public class App {

    public static void main(final String... args) {
        try {
            final Injector injector = Guice.createInjector(new SteamClientModule());
            final Config config = ConfigFactory.load();
            final ConfigLoader configLoader = new ConfigLoader();
            final Config usersConfig = configLoader.load();
            final SecretDataCache secretDataCache = new SecretDataCache(usersConfig);
            final Map<String, ActorRef> actors = new HashMap<>();
            if (usersConfig != null) {
                final ActorSystem system = ActorSystem.create("steam-client");
                final List<? extends Config> userConfigs = usersConfig.getConfigList("users");
                if (userConfigs.size() == 0) {
                    throw new RuntimeException("Не указаны настройки пользователей");
                }
                for (final Config userConfig : userConfigs) {
                    final String username = userConfig.getString("steam.username");
                    final SteamUser steamUser = new SteamUser();
                    steamUser.setId(1L);
                    steamUser.setUsername(username);
                    steamUser.setCookies("[]");
                    steamUser.setStatus("ACTIVE");
                    final SecretData data = secretDataCache.getSecretData(username);
                    final Props props = Props.create(SteamUserActor.class, steamUser, data, injector.getInstance(SteamAuthApi.class), injector.getInstance(SteamTradeApi.class));
                    final ActorRef actorRef = system.actorOf(props, username);
                    ask(actorRef, Command.LOGIN, new Timeout(1, TimeUnit.MINUTES)).thenAccept(response -> {
                        if (response instanceof String && "AUTORIZED".equals(response)) {
                            System.out.println("User autorized");
                        } else {
                            throw new RuntimeException("Ошибка в процессе авторизации пользователя");
                        }
                    }).exceptionally(th -> {
                        throw new RuntimeException("Ошибка в процессе авторизации пользователя", th);
                    });
                    actors.put(username, actorRef);
                }
                final ActorMaterializer materializer = ActorMaterializer.create(system);
                final HttpApi httpApi = new HttpApi(actors);
                final Http http = Http.get(system);
                http.setDefaultServerHttpContext(useHttps(config, system));
                final Flow<HttpRequest, HttpResponse, NotUsed> handler = httpApi.getRoute().flow(system, materializer);
                final CompletionStage<ServerBinding> binding = http.bindAndHandle(
                        handler,
                        ConnectHttp.toHost(
                                config.getString("steam.client.http.host"),
                                config.getInt("steam.client.http.port")
                        ),
                        materializer
                );
                binding.exceptionally(failure -> {
                    system.log().info("Something Fvery bad happened! " + failure.getMessage());
                    return null;
                });
            }
        } catch (final Throwable th) {
            th.printStackTrace();
            System.exit(1);
        }
    }

    public static HttpsConnectionContext useHttps(final Config config, final ActorSystem system) throws KeyManagementException, NoSuchAlgorithmException, UnrecoverableKeyException, CertificateException, KeyStoreException, IOException {
        final HttpsConnectionContext https;
        try {
            final char[] password = config.getString("steam.client.http.ssl.ks.password").toCharArray();
            final KeyStore ks = KeyStore.getInstance("PKCS12");
            try (final InputStream keystore = Files.newInputStream(Paths.get(config.getString("steam.client.http.ssl.ks.path")))) {
                ks.load(keystore, password);
            }
            final KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(ks, password);

            final KeyStore ts = KeyStore.getInstance("JKS");
            final char[] psPswd = config.getString("steam.client.http.ssl.ts.password").toCharArray();
            try (final InputStream truststore = Files.newInputStream(Paths.get(config.getString("steam.client.http.ssl.ts.path")))) {
                ts.load(truststore, psPswd);
            }
            final TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ts);

            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), tmf.getTrustManagers(), new SecureRandom());

            https = ConnectionContext.https(
                    sslContext,
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.of(TLSClientAuth.need()),
                    Optional.empty());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            system.log().error("Exception while configuring HTTPS.", e);
            throw e;
        } catch (CertificateException | KeyStoreException | UnrecoverableKeyException | IOException e) {
            system.log().error("Exception while ", e);
            throw e;
        }

        return https;
    }
}