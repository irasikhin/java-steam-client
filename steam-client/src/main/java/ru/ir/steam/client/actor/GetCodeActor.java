package ru.ir.steam.client.actor;

import akka.actor.ActorRef;
import akka.actor.Status;
import akka.actor.UntypedActor;
import ru.ir.steam.dto.SecretData;
import ru.ir.steam.dto.SteamUser;
import ru.ir.steam.internal.CodeGenerator;

import static ru.ir.steam.client.cmd.Command.GET_CODE;

public class GetCodeActor extends UntypedActor {

    private final SteamUser steamUser;

    private final SecretData secretData;

    public GetCodeActor(SteamUser steamUser, SecretData secretData) {
        this.steamUser = steamUser;
        this.secretData = secretData;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        try {
            CodeGenerator codeGenerator = new CodeGenerator();
            if (GET_CODE.equals(message)) {
                getSender().tell(codeGenerator.getCode(secretData.getSharedSecret()), getSelf());
            } else {
                unhandled(message);
            }
        } catch (Exception ex) {
            getSender().tell(new Status.Failure(ex), ActorRef.noSender());
        }
    }
}
