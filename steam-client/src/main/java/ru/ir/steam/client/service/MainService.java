package ru.ir.steam.client.service;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import akka.util.Timeout;
import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ir.steam.SteamAuthApi;
import ru.ir.steam.SteamTradeApi;
import ru.ir.steam.client.actor.SteamUserActor;
import ru.ir.steam.client.cmd.Command;
import ru.ir.steam.client.http.HttpApi;
import ru.ir.steam.dto.SecretData;
import ru.ir.steam.dto.SteamUser;
import ru.ir.steam.internal.SecretDataCache;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static akka.pattern.PatternsCS.ask;

public class MainService {

    private static final Logger logger = LoggerFactory.getLogger(MainService.class);

    private final Config config;

    private final Config usersConfig;

    private final SecretDataCache secretDataCache;

    private final SteamAuthApi steamAuthApi;

    private final SteamTradeApi steamTradeApi;

    private final ActorSystem system = ActorSystem.create("steam-client");

    private final ActorMaterializer materializer = ActorMaterializer.create(system);

    private final Http http = Http.get(system);

    public MainService(Config config, Config usersConfig, SteamAuthApi steamAuthApi, SteamTradeApi steamTradeApi) {
        this.config = config;
        this.usersConfig = usersConfig;
        this.steamAuthApi = steamAuthApi;
        this.steamTradeApi = steamTradeApi;
        this.secretDataCache = new SecretDataCache(usersConfig);
    }

    public void start() throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, IOException {
        logger.info("Application starting...");
        initHttpsIfNeed();

        final Map<String, ActorRef> actors = new HashMap<>();
        final HttpApi httpApi = new HttpApi(actors);
        if (usersConfig != null) {
            final List<? extends Config> userConfigs = usersConfig.getConfigList("users");
            if (userConfigs.size() == 0) {
                throw new RuntimeException("Can't found users settings");
            }
            for (final Config userConfig : userConfigs) {
                String username = userConfig.getString("steam.username");
                try {
                    actors.put(username, authorization(username));
                    Thread.sleep(10000);
                } catch (Exception ex) {
                    logger.error("Error during user " + username + " authorization", ex);
                }
            }
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
                logger.info("Something very bad happened ", failure);
                return null;
            });
        }
        logger.info("Application started");
    }

    private ActorRef authorization(String username) throws ExecutionException, InterruptedException {
        logger.info("Authorization {}", username);
        final SteamUser steamUser = new SteamUser(username, "[]");
        final SecretData data = secretDataCache.getSecretData(username);
        final Props props = Props.create(SteamUserActor.class, steamUser, data, steamAuthApi, steamTradeApi);
        final ActorRef actorRef = system.actorOf(props, username);
        CompletionStage<Void> autorization = ask(actorRef, Command.LOGIN, new Timeout(5, TimeUnit.MINUTES)).thenAccept(response -> {
            if (!(response instanceof String) || !"AUTORIZED".equals(response)) {
                throw new RuntimeException("User not autorized");
            }
        }).exceptionally(th -> {
            throw new RuntimeException("User not autorized", th);
        });
        autorization.toCompletableFuture().get();
        logger.info("User {} logged to steam", username);
        return actorRef;
    }

    private void initHttpsIfNeed() throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, IOException {
        if (config.getBoolean("steam.client.http.ssl.enable")) {
            http.setDefaultClientHttpsContext(HttpsInitializer.makeHttpsContext(
                    config.getString("steam.client.http.ssl.keystore.path"),
                    config.getString("steam.client.http.ssl.keystore.password"),
                    config.getString("steam.client.http.ssl.truststore.path"),
                    config.getString("steam.client.http.ssl.truststore.password"),
                    system
            ));
        }
    }

}
