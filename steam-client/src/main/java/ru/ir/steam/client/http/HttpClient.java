package ru.ir.steam.client.http;

import akka.dispatch.ExecutionContexts;
import akka.http.javadsl.Http;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.model.HttpEntities;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.unmarshalling.Unmarshaller;
import akka.stream.Materializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.ir.steam.client.cmd.SendTradeOfferMessage;
import ru.ir.steam.dto.GetTradeOfferResponse;
import ru.ir.steam.dto.GetTradeOffersResponse;
import ru.ir.steam.dto.SteamInventory;

import java.util.concurrent.CompletableFuture;

public class HttpClient {

    private final Http http;

    private final Materializer materializer;

    private final String urlPrefix;

    public HttpClient(final String host, final int port, final Http http, final Materializer materializer) {
        this.http = http;
        this.materializer = materializer;
        this.urlPrefix = "http://" + host + ":" + port;
    }

    public CompletableFuture<Boolean> accept(final String username, final String tradeofferid) {
        final String url = createUrl(username, "accept_trade") + tradeofferid;
        return http.singleRequest(HttpRequest.GET(url)).thenCompose(response ->
                Unmarshaller.entityToString().unmarshal(response.entity(), ExecutionContexts.global(), materializer).thenApply(Boolean::parseBoolean)
        ).toCompletableFuture();
    }

    public CompletableFuture<String> decline(final String username, final String tradeofferid) {
        final String url = createUrl(username, "decline_trade") + tradeofferid;
        return http.singleRequest(HttpRequest.GET(url)).thenCompose(response ->
                Unmarshaller.entityToString().unmarshal(response.entity(), ExecutionContexts.global(), materializer)
        ).toCompletableFuture();
    }

    public CompletableFuture<GetTradeOfferResponse> getTrade(final String username, final String tradeofferid) {
        final String url = createUrl(username, "get_trade") + tradeofferid;
        return http.singleRequest(HttpRequest.GET(url)).thenCompose(response ->
                Jackson.unmarshaller(GetTradeOfferResponse.class).unmarshal(response.entity(), ExecutionContexts.global(), materializer)
        ).toCompletableFuture();
    }

    public CompletableFuture<SteamInventory> getInventory(final String username, final String gameId) {
        final String url = createUrl(username, "get_inventory") + gameId;
        return http.singleRequest(HttpRequest.GET(url)).thenCompose(response -> {
            return Jackson.unmarshaller(SteamInventory.class).unmarshal(response.entity(), ExecutionContexts.global(), materializer);
        }).toCompletableFuture();
    }

    public CompletableFuture<GetTradeOffersResponse> getActiveTrades(final String username) {
        final String url = createUrl(username, "get_active_trades");
        return http.singleRequest(HttpRequest.GET(url)).thenCompose(response ->
                Jackson.unmarshaller(GetTradeOffersResponse.class).unmarshal(response.entity(), ExecutionContexts.global(), materializer)
        ).toCompletableFuture();
    }

    public CompletableFuture<Boolean> sendOffer(final String username, final SendTradeOfferMessage message) throws JsonProcessingException {
        final String url = createUrl(username, "send_trade");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(message);
        final HttpRequest request = HttpRequest.POST(url).withEntity(HttpEntities.create(ContentTypes.APPLICATION_JSON, json));
        return http.singleRequest(request).thenCompose(response ->
                Unmarshaller.entityToString().unmarshal(response.entity(), ExecutionContexts.global(), materializer).thenApply(Boolean::parseBoolean)
        ).toCompletableFuture();
    }

    public CompletableFuture<String> getCode(final String username) {
        final String url = createUrl(username, "get_code");
        return http.singleRequest(HttpRequest.GET(url)).thenCompose(response ->
                Unmarshaller.entityToString().unmarshal(response.entity(), ExecutionContexts.global(), materializer)
        ).toCompletableFuture();
    }

    private String createUrl(final String username, final String path) {
        return urlPrefix + "/client/" + username + "/" + path;
    }

}
