package ru.ir.steam.client.http;

import akka.actor.ActorRef;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.RejectionHandler;
import akka.http.javadsl.server.Route;
import akka.pattern.PatternsCS;
import akka.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ir.steam.client.cmd.*;

import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static akka.http.javadsl.server.PathMatchers.segment;

public class HttpApi extends AllDirectives {

    private static final Timeout timeout = new Timeout(30, TimeUnit.SECONDS);

    private Logger logger = LoggerFactory.getLogger(HttpApi.class);

    private final Map<String, ActorRef> actors;

    private final Route route;

    private final RejectionHandler totallyMissingHandler = RejectionHandler.newBuilder()
            .handleNotFound(complete(StatusCodes.NOT_FOUND, "Page not found"))
            .build();

    public HttpApi(Map<String, ActorRef> actors) {
        this.actors = actors;
        this.route = createRoutes();
    }

    public Route getRoute() {
        return route;
    }

    private Route checkUserActorRef(String username, Function<ActorRef, Route> route) {
        ActorRef ref = actors.get(username);
        if (ref == null) {
            return reject();
        }
        return route.apply(ref);
    }

    private Route createRoutes() {
        return handleRejections(totallyMissingHandler, () -> route(
                pathPrefix(segment("client").slash(segment()), username -> checkUserActorRef(username, (ref) -> route(
                        path(segment("accept_trade").slash(segment()), tradeofferid -> {
                            CompletionStage<Object> resp = PatternsCS.ask(ref, new Accept(tradeofferid), timeout);
                            return completeOKWithFuture(resp, Jackson.marshaller());
                        }),
                        path(segment("decline_trade").slash(segment()), tradeofferid -> {
                            CompletionStage<Object> resp = PatternsCS.ask(ref, new Decline(tradeofferid), timeout);
                            return completeOKWithFuture(resp, Jackson.marshaller());
                        }),
                        path(segment("get_trade").slash(segment()), tradeofferid -> {
                            CompletionStage<Object> resp = PatternsCS.ask(ref, new GetTrade(tradeofferid), timeout);
                            return completeOKWithFuture(resp, Jackson.marshaller());
                        }),
                        path(segment("get_inventory").slash(segment()), gameId -> {
                            CompletionStage<Object> resp = PatternsCS.ask(ref, new GetInventory(gameId), timeout);
                            return completeOKWithFuture(resp, Jackson.marshaller());
                        }),
                        path("get_active_trades", () -> {
                            CompletionStage<Object> resp = PatternsCS.ask(ref, Command.GET_ACTIVE_TRADES, timeout);
                            return completeOKWithFuture(resp, Jackson.marshaller());
                        }),
                        path("get_code", () -> {
                            CompletionStage<Object> resp = PatternsCS.ask(ref, Command.GET_CODE, timeout);
                            return completeOKWithFuture(resp, Jackson.marshaller());
                        }),
                        path("get_confirmation_url", () -> {
                            CompletionStage<Object> resp = PatternsCS.ask(ref, Command.GET_CONFIRMATION_URL, timeout);
                            return completeOKWithFuture(resp, Jackson.marshaller());
                        }),
                        path("send_trade", () -> post(() ->
                                entity(Jackson.unmarshaller(SendTradeOfferMessage.class), message -> {
                                    System.out.println("message: " + message);
                                    CompletionStage<Object> resp = PatternsCS.ask(ref, message, timeout);
                                    return completeOKWithFuture(resp, Jackson.marshaller());
                                })
                        ))
                )))
        ));
    }

}
