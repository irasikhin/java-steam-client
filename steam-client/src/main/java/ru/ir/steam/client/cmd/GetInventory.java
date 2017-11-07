package ru.ir.steam.client.cmd;

import lombok.Data;

import java.io.Serializable;

@Data
public class GetInventory implements Serializable {
    private final String gameId;

    public GetInventory(String gameId) {
        this.gameId = gameId;
    }

    public String getGameId() {
        return gameId;
    }
}
