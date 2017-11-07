package ru.ir.steam;

import com.fasterxml.jackson.databind.ObjectMapper;
import one.util.streamex.StreamEx;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ir.steam.dto.*;
import ru.ir.steam.internal.BodyResponseHandler;
import ru.ir.steam.internal.CodeGenerator;
import ru.ir.steam.utils.PlainUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.apache.http.client.methods.RequestBuilder.get;
import static org.apache.http.client.methods.RequestBuilder.post;

public class SteamTradeApi {

    private static final Logger logger = LoggerFactory.getLogger(SteamTradeApi.class);

    private final String steamUrl;

    private final String steamApiUrl;

    private final CloseableHttpClient client;

    private final ObjectMapper objectMapper;

    private final CodeGenerator codeGenerator;

    private final HttpHost steamHost;

    public SteamTradeApi(String steamUrl, String steamApiUrl, CloseableHttpClient client, ObjectMapper objectMapper) {
        this.steamUrl = steamUrl;
        this.steamApiUrl = steamApiUrl;
        this.client = client;
        this.objectMapper = objectMapper;
        this.codeGenerator = new CodeGenerator();
        this.steamHost = HttpHost.create(steamUrl);
    }

    public SteamInventory getInventory(String steamId, String gameId, AuthorizedSteamUser steamUser) throws IOException {
        String content = client.execute(get(steamUrl + "/inventory/" + steamId + "/" + gameId + "/2")
                        .addParameter("l", "english")
                        .addParameter("count", "5000")
                        .build(),
                new BodyResponseHandler(), steamUser.getContext());
        return objectMapper.readValue(content, SteamInventory.class);
    }

    public GetTradeOffersResponse getActiveOffers(String apiKey, AuthorizedSteamUser steamUser) throws IOException {
        String content = client.execute(get(steamApiUrl + "/IEconService/GetTradeOffers/v1/")
                        .addParameter("get_received_offers", "1")
                        .addParameter("active_only", "1")
                        .addParameter("historical_only", "0")
                        .addParameter("key", apiKey).build(),
                new BodyResponseHandler(), steamUser.getContext());
        return objectMapper.readValue(content, GetTradeOffersResponse.class);
    }

    public GetTradeOfferResponse getOffer(String apiKey, String tradeofferid, String language) throws IOException {
        String content = client.execute(get(steamApiUrl + "/IEconService/GetTradeOffer/v1/")
                        .addParameter("tradeofferid", tradeofferid)
                        .addParameter("language", language)
                        .addParameter("key", apiKey).build(),
                new BodyResponseHandler());
        return objectMapper.readValue(content, GetTradeOfferResponse.class);
    }

    public String declineOffer(String apiKey, String tradeofferid) throws IOException {
        return client.execute(get(steamApiUrl + "/IEconService/DeclineTradeOffer/v1/")
                .addParameter("tradeofferid", tradeofferid)
                .addParameter("key", apiKey).build(), new BodyResponseHandler());
    }

    public Boolean cancelOffer(String apiKey, String tradeofferid) throws IOException {
        String content = client.execute(get(steamApiUrl + "/IEconService/CancelTradeOffer/v1/")
                .addParameter("tradeofferid", tradeofferid)
                .addParameter("key", apiKey).build(), new BodyResponseHandler());

        return objectMapper.readValue(content, Boolean.class);
    }

    public boolean acceptOffer(String tradeofferid, AuthorizedSteamUser steamUser, SecretData secretData) throws IOException {
        logger.info("Accept order: " + tradeofferid);
        HttpClientContext context = steamUser.getContext();
        HttpUriRequest request = post(steamUrl + "/tradeoffer/" + tradeofferid + "/accept")
                .addParameter("sessionid", getSessionId(context.getCookieStore().getCookies()))
                .addParameter("tradeofferid", tradeofferid)
                .addParameter("serverid", "1")
                .addHeader("Referer", steamUrl + "/tradeoffer/" + tradeofferid + "/").build();

        HttpResponse httpResponse = client.execute(request, context);
        AcceptResponse response = objectMapper.readValue(EntityUtils.toString(httpResponse.getEntity()), AcceptResponse.class);
        if (PlainUtils.isNotEmpty(response.getStrError())) {
            throw new RuntimeException(response.getStrError());
        } else if (PlainUtils.isNotNullAndTrue(response.getNeedsMobileConfirmation())) {
            logger.debug("Need confirm: " + tradeofferid);
            return confirm(tradeofferid, steamUser, secretData);
        } else {
            logger.debug("Mobile confirmation dont need");
            return true;
        }
    }

    private boolean confirm(String tradeofferid, AuthorizedSteamUser steamUser, SecretData secretData) throws IOException {
        String deviceId = secretData.getDeviceId();
        String identitySecret = secretData.getIdentitySecret();
        String steamId = secretData.getSteamId();
        String url = codeGenerator.getConfirmationUrl(identitySecret, deviceId, steamId);

        String html = EntityUtils.toString(client.execute(get(url).build(), steamUser.getContext()).getEntity());
        Document document = Jsoup.parse(html);
        Elements elements = document.select("div .mobileconf_list_entry");
        for (Element element : elements) {
            String confid = element.attr("data-confid");
            String key = element.attr("data-key");
            String tradeofferidData = element.attr("data-creator");
            if (tradeofferidData.equals(tradeofferid)) {
                String confirmUrl = steamUrl + "/mobileconf/ajaxop?op=allow&" + codeGenerator.getTaggedConfirmationUrlParams(identitySecret, deviceId, steamId, "allow") +
                        "&cid=" + confid + "&ck=" + key;
                String confirmContent = client.execute(steamHost, get(confirmUrl).build(), new BasicResponseHandler(), steamUser.getContext());
                ConfirmResponse confirmResponse = objectMapper.readValue(confirmContent, ConfirmResponse.class);
                if (!confirmResponse.isSuccess()) {
                    logger.warn("Подтверждене для трейда " + tradeofferid + " либо не найдено либо выполненно некорректно");
                    return false;
                }
            }
        }

        return true;
    }

    private String getSessionId(List<Cookie> cookies) {
        Optional<Cookie> cookieOpt = StreamEx.of(cookies).filter(cookie -> "sessionid".equals(cookie.getName()) && "steamcommunity.com".equals(cookie.getDomain())).findFirst();
        if (cookieOpt.isPresent()) {
            return cookieOpt.get().getValue();
        } else {
            throw new RuntimeException("SessionId not found");
        }
    }

}
