package ru.ir.steam.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClients;
import ru.ir.steam.dto.QueryTimeRoot;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static org.apache.http.client.methods.RequestBuilder.post;

public class CodeGenerator {

    private final CloseableHttpClient client;

    private static final String QUERY_TIME_URL = "https://api.steampowered.com/ITwoFactorService/QueryTime/v0001";

    private final ObjectMapper objectMapper;

    public CodeGenerator() {
        this.client = HttpClients.custom().setRedirectStrategy(new DefaultRedirectStrategy()).build();
        this.objectMapper = new ObjectMapper();
    }

    public String getCode(String secret) {
        return TwoFactorToken.generateSteamGuardCodeForTime(secret, getServerTime());
    }

    public String getConfirmationUrl(String identitySecret, String phoneId, String steamId) {
        Long serverSeconds = getServerTime();
        String confirmationUrl = "https://steamcommunity.com/mobileconf/conf";
        return String.format(
                "%s?p=%s&a=%s&k=%s&t=%d&m=android&tag=%s",
                confirmationUrl,
                phoneId,
                steamId,
                base64encryptedConfirmationHash(identitySecret, serverSeconds, "conf"),
                serverSeconds,
                "conf"
        );
    }

    public String getTaggedConfirmationUrlParams(String identitySecret, String phoneId, String steamId, String str) {
        long serverSeconds = getServerTime();
        return String.format("p=%s&a=%s&k=%s&t=%d&m=android&tag=%s", phoneId, steamId, base64encryptedConfirmationHash(identitySecret, serverSeconds, str), serverSeconds, str);
    }

    private String base64encryptedConfirmationHash(String identitySecret, long j, String str) {
        if (identitySecret == null) {
            return "";
        }
        byte[] decode = Base64.getDecoder().decode(identitySecret.getBytes());
        int length = str != null ? str.length() > 32 ? 40 : str.length() + 8 : 8;
        byte[] obj = new byte[length];
        int i = 8;
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                break;
            }
            obj[i2] = (byte) ((int) j);
            j >>>= 8;
            i = i2;
        }
        if (str != null) {
            System.arraycopy(str.getBytes(), 0, obj, 8, length - 8);
        }
        Key secretKeySpec = new SecretKeySpec(decode, "HmacSHA1");
        try {
            Mac instance = Mac.getInstance("HmacSHA1");
            instance.init(secretKeySpec);
            return percentEncodeUrlUnsafeChars(Base64.getEncoder().encodeToString(instance.doFinal(obj)));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            return null;
        }
    }


    private String percentEncodeUrlUnsafeChars(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt == '+' || charAt == '/' || charAt == '=') {
                stringBuilder.append(String.format("%%%02x", (int) charAt));
            } else {
                stringBuilder.append(charAt);
            }
        }
        return stringBuilder.toString();
    }


    private Long getServerTime() {
        try {
            long currentTime = System.currentTimeMillis() / 1000;
            String entity = client.execute(HttpHost.create("https://api.steampowered.com"), post(QUERY_TIME_URL)
                    .addHeader("content-length", "0").build(), new BodyResponseHandler(), null);
            QueryTimeRoot queryTimeRoot = objectMapper.readValue(entity, QueryTimeRoot.class);
            return (System.currentTimeMillis() / 1000) + (currentTime - Long.valueOf(queryTimeRoot.getResponse().getServerTime()));
        } catch (Exception ex) {
            throw new RuntimeException("Exception during server time receive", ex);
        }
    }

}
