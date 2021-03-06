package ru.ir.steam.client.actor;

import akka.actor.AbstractActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ir.steam.SteamAuthApi;
import ru.ir.steam.SteamTradeApi;
import ru.ir.steam.client.cmd.*;
import ru.ir.steam.dto.*;
import ru.ir.steam.internal.CodeGenerator;

public class SteamUserActor extends AbstractActor {

    private Logger logger = LoggerFactory.getLogger(SteamUserActor.class);

    private final SteamUser steamUser;

    private final SecretData secretData;

    private final SteamAuthApi steamAuthApi;

    private final CodeGenerator codeGenerator;

    private final AbstractActor.Receive authorizedState;

    private AuthorizedSteamUser authorizedUser;

    public SteamUserActor(SteamUser steamUser, SecretData secretData, SteamAuthApi steamAuthApi, SteamTradeApi steamTradeApi) {
        this.steamUser = steamUser;
        this.secretData = secretData;
        this.steamAuthApi = steamAuthApi;
        this.codeGenerator = new CodeGenerator();
        this.authorizedState = receiveBuilder().match(Accept.class, accept -> {
            getSender().tell(steamTradeApi.acceptOffer(accept.getTradeofferid(), authorizedUser, secretData), getSelf());
        }).match(GetTrade.class, getTrade -> {
            getSender().tell(steamTradeApi.getOffer(secretData.getApiKey(), getTrade.getTradeofferid(), getTrade.getLanguage()), getSelf());
        }).match(Decline.class, decline -> {
            getSender().tell(steamTradeApi.declineOffer(secretData.getApiKey(), decline.getTradeofferid()), getSelf());
        }).match(Cancel.class, cancel -> {
            getSender().tell(steamTradeApi.cancelOffer(secretData.getApiKey(), cancel.getTradeofferid()), getSelf());
        }).matchEquals(Command.GET_ACTIVE_TRADES, getActiveTrades -> {
            GetTradeOffersResponse response = steamTradeApi.getActiveOffers(secretData.getApiKey(), authorizedUser);
            getSender().tell(response, getSelf());
        }).match(GetInventory.class, getInventory -> {
            SteamInventory inventory = steamTradeApi.getInventory(secretData.getSteamId(), getInventory.getGameId(), authorizedUser);
            getSender().tell(inventory, getSelf());
        }).matchEquals(Command.GET_CODE, getCode -> {
            getSender().tell(codeGenerator.getCode(secretData.getSharedSecret()), getSelf());
        }).matchEquals(Command.GET_CONFIRMATION_URL, getConfirmationUrl -> {
            getSender().tell(codeGenerator.getConfirmationUrl(secretData.getIdentitySecret(), secretData.getDeviceId(), secretData.getSteamId()), getSelf());
        }).match(SendTradeOfferMessage.class, message -> {
            getSender().tell(steamTradeApi.sendTradeOffer(message.getTradeOffer(), authorizedUser, secretData), getSelf());
        }).build();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().matchEquals(Command.LOGIN, login -> {
            authorizedUser = steamAuthApi.login(steamUser, secretData);
            getSender().tell("AUTORIZED", getSelf());
            getContext().become(authorizedState, true);
        }).build();
    }

}
