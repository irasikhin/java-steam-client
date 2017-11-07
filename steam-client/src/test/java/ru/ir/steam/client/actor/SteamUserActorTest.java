package ru.ir.steam.client.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.util.Timeout;
import org.apache.http.client.protocol.HttpClientContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.ir.steam.SteamAuthApi;
import ru.ir.steam.SteamTradeApi;
import ru.ir.steam.client.cmd.Command;
import ru.ir.steam.dto.AuthorizedSteamUser;
import ru.ir.steam.dto.SecretData;
import ru.ir.steam.dto.SteamUser;
import scala.concurrent.duration.Duration;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import static akka.pattern.PatternsCS.ask;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SteamUserActorTest {

    private ActorSystem system;

    @Before
    public void setUp() throws Exception {
        system = ActorSystem.create("test");
    }

    @Test
    public void testAuthorization() throws Exception {
        SteamUser steamUser = new SteamUser();
        steamUser.setId(1L);
        steamUser.setUsername("nettierichards18");
        steamUser.setCookies("[]");
        steamUser.setStatus("ACTIVE");
        SteamAuthApi steamAuthApi = mock(SteamAuthApi.class);
        SteamTradeApi steamTradeApi = mock(SteamTradeApi.class);
        SecretData secretData = mock(SecretData.class);
        when(steamAuthApi.login(steamUser, secretData)).thenReturn(new AuthorizedSteamUser(steamUser.getUsername(), new HttpClientContext()));

        Props props = Props.create(SteamUserActor.class, steamUser, secretData, steamAuthApi, steamTradeApi);
        ActorRef steamUserActor = system.actorOf(props);
        CompletionStage<Object> future = ask(steamUserActor, Command.LOGIN, new Timeout(Duration.create(10, TimeUnit.SECONDS)));
        future.thenAccept(result -> Assert.assertEquals("AUTORIZED", result));
    }

    @After
    public void tearDown() throws Exception {
        system.terminate();
    }

}