package ru.ir.steam.client.http;

import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import ru.ir.steam.client.cmd.SendTradeOfferMessage;
import ru.ir.steam.dto.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

public class HttpApiTest {

    //{"newversion":true,"version":2,"me":{"assets":[{"appid":730,"contextid":"2","amount":1,"assetid":"11323961556"}],"currency":[],"ready":false},"them":{"assets":[],"currency":[],"ready":false}}

    @Test
    public void test() throws JsonProcessingException, ExecutionException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        SendTradeOfferMessage message = new SendTradeOfferMessage(
                new SendTradeOffer(
                        "76561198217862989",
                        "HI",
                        new OfferJsonData(
                                new OfferPersonData(
                                        Collections.singletonList(
                                                new SendOfferAsset(
                                                        "730",
                                                        "2",
                                                        "11323961556",
                                                        BigDecimal.ONE
                                                )
                                        )
                                ),
                                new OfferPersonData(Collections.emptyList())
                        ),
                        new TradeOfferCreateParams("")
                )
        );

        ActorSystem actorSystem = ActorSystem.create("test");
        Materializer materializer = ActorMaterializer.create(actorSystem);
        Http http = Http.get(actorSystem);
        HttpClient client = new HttpClient("localhost", 8080, http, materializer);
        client.sendOffer("leonafloyd825", message).whenComplete((resp, th) -> {
            if (th != null) {
                th.printStackTrace();
            } else {
                System.out.println("RESP: " + resp);
            }
        }).get();
        System.out.println("MESSAGE: " + mapper.writeValueAsString(message));
    }

}