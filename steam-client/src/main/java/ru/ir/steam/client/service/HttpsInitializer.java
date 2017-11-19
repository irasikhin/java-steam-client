package ru.ir.steam.client.service;

import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectionContext;
import akka.http.javadsl.HttpsConnectionContext;
import akka.stream.TLSClientAuth;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Optional;

public class HttpsInitializer {

    public static HttpsConnectionContext makeHttpsContext(final String ksPath, final String ksPass, final String tsPath, final String tsPass, final ActorSystem system) throws KeyManagementException, NoSuchAlgorithmException, UnrecoverableKeyException, CertificateException, KeyStoreException, IOException {
        final HttpsConnectionContext https;
        try {
            final char[] ksPassChars = ksPass.toCharArray();
            final KeyStore ks = KeyStore.getInstance("PKCS12");
            try (final InputStream keystore = Files.newInputStream(Paths.get(ksPath))) {
                ks.load(keystore, ksPassChars);
            }
            final KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(ks, ksPassChars);

            final KeyStore ts = KeyStore.getInstance("JKS");
            final char[] tsPassChars = tsPass.toCharArray();
            try (final InputStream truststore = Files.newInputStream(Paths.get(tsPath))) {
                ts.load(truststore, tsPassChars);
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
