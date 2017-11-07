package ru.ir.steam;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import one.util.streamex.StreamEx;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ir.steam.dto.*;
import ru.ir.steam.internal.BodyResponseHandler;
import ru.ir.steam.internal.CodeGenerator;
import ru.ir.steam.internal.GmailService;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.apache.http.client.methods.RequestBuilder.*;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;

public class SteamAuthApi {

    private static final Logger logger = LoggerFactory.getLogger(SteamAuthApi.class);

    private final String steamUrl;

    private final CloseableHttpClient client;

    private final ObjectMapper objectMapper;

    private final CodeGenerator codeGenerator;

    private final HttpHost host;

    private final GmailService gmailService;

    public SteamAuthApi(String steamUrl, CloseableHttpClient client, ObjectMapper objectMapper) {
        this.steamUrl = steamUrl;
        this.host = HttpHost.create(steamUrl);
        this.client = client;
        this.objectMapper = objectMapper;
        this.codeGenerator = new CodeGenerator();
        this.gmailService = new GmailService();
    }

    public AuthorizedSteamUser login(SteamUser steamUser, SecretData secretData) throws Exception {
        HttpClientContext httpContext = new HttpClientContext();
        BasicCookieStore basicCookieStore = new BasicCookieStore();
        basicCookieStore.clear();
        httpContext.setCookieStore(basicCookieStore);
        if (!isAuthorized(httpContext)) {
            RsaKey rsaKey = getRsaKey(steamUser.getUsername(), httpContext);
            String encodedPassword = encodePassword(secretData.getPassword(), rsaKey);
            signIn(new LoginInfo(steamUser.getUsername(), encodedPassword, rsaKey.getTimestamp()), secretData, httpContext);
            List<Cookie> submittedCookies = submit(secretData, httpContext);
            httpContext.getCookieStore().clear();
            submittedCookies.forEach(c -> httpContext.getCookieStore().addCookie(c));
            return new AuthorizedSteamUser(steamUser.getUsername(), httpContext);
        } else {
            return new AuthorizedSteamUser(steamUser.getUsername(), httpContext);
        }
    }

    private void signIn(LoginInfo loginInfo, SecretData secretData, HttpClientContext httpContext) throws ExecutionException, InterruptedException, IOException {
        String sharedSecret = secretData.getSharedSecret();
        logger.debug("Try to authorize with data: {}", loginInfo);
        HttpUriRequest request = post(steamUrl + "/login/dologin/")
                .addParameter("donotcache", String.valueOf(System.currentTimeMillis()))
                .addParameter("password", loginInfo.getPassword())
                .addParameter("username", loginInfo.getUsername())
                .addParameter("twofactorcode", codeGenerator.getCode(sharedSecret))
                .addParameter("emailauth", loginInfo.getEmailAuth())
                .addParameter("loginfriendlyname", "")
                .addParameter("captchagid", loginInfo.getCaptchaGid())
                .addParameter("captcha_text", loginInfo.getCaptchaText())
                .addParameter("emailsteamid", loginInfo.getEmailSteamId())
                .addParameter("rsatimestamp", loginInfo.getTimestamp())
                .addParameter("remember_login", "true")
                .build();

        HttpResponse response = client.execute(request, httpContext);
        String content = EntityUtils.toString(response.getEntity());
        AuthResult authResult = objectMapper.readValue(content, AuthResult.class);
        if (!authResult.isSuccess()) {
            throw new IllegalStateException("Auth not complete: " + authResult);
        }

        AuthResult.TransferParameters transferParameters = authResult.getTransferParameters();
        if (transferParameters != null) {
            for (String transferUrl : authResult.getTransferUrls()) {
                HttpUriRequest transferRequest = post(transferUrl)
                        .addParameter("steamid", transferParameters.getSteamId())
                        .addParameter("token", transferParameters.getToken())
                        .addParameter("auth", transferParameters.getAuth())
                        .addParameter("remember_login", String.valueOf(transferParameters.getRememberLogin()))
                        .addParameter("webcookie", transferParameters.getWebCookie())
                        .addParameter("token_secure", transferParameters.getTokenSecure())
                        .build();

                client.execute(transferRequest, httpContext);
            }
        }
    }

    private List<Cookie> submit(SecretData secretData, HttpClientContext context) throws ExecutionException, InterruptedException, IOException {
        String machineAuthKey = secretData.getMachineAuthKey();
        String machineAuthValue = secretData.getMachineAuthValue();
        logger.info("Submit data to steam");

        client.execute(post(steamUrl).build(), context);
        logger.debug("Current cookies: " + context.getCookieStore().getCookies());
        if (isNotEmpty(machineAuthKey) && isNotEmpty(machineAuthValue)) {
            logger.debug("MachineAuthKey: {}, machineAuthValue: {}", machineAuthKey, machineAuthValue);
            return StreamEx.of(context.getCookieStore().getCookies()).map(cookie -> {
                if (cookie.getName().startsWith("steamMachineAuth")) {
                    BasicClientCookie basicClientCookie = new BasicClientCookie(machineAuthKey, machineAuthValue);
                    basicClientCookie.setDomain(cookie.getDomain());
                    return basicClientCookie;
                } else {
                    return cookie;
                }
            }).toList();
        }

        return context.getCookieStore().getCookies();
    }

    private List<Cookie> getCookiesFromJson(String cookies) throws IOException {
        return objectMapper.readValue(cookies, new TypeReference<List<CookieWrapper>>() {
        });
    }

    private Boolean isAuthorized(HttpClientContext httpClientContext) throws IOException {
        client.execute(host, head(steamUrl).build(), httpClientContext);

        return StreamEx.of(httpClientContext.getCookieStore().getCookies()).filter(cookie ->
                "steamLogin".equals(cookie.getName()) && cookie.getValue() != null && cookie.getValue().length() > 0 &&
                        !"deleted".equals(cookie.getValue())).findAny().isPresent();
    }

    private RsaKey getRsaKey(String username, HttpClientContext httpClientContext) throws IOException {
        String content = client.execute(host, get(steamUrl + "/login/getrsakey").addParameter("username", username).build(),
                new BodyResponseHandler(), httpClientContext);

        return objectMapper.readValue(content, RsaKey.class);
    }

    private String encodePassword(String password, RsaKey rsaKey) throws Exception {
        BigInteger modulus = new BigInteger(rsaKey.getPublicKeyMod(), 16);
        BigInteger exp = new BigInteger(rsaKey.getPublicKeyExp(), 16);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus, exp);
        PublicKey publicKey = keyFactory.generatePublic(rsaPublicKeySpec);
        return RSA.encryptB64(publicKey, password);
    }

}
