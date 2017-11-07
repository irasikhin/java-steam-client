package ru.ir.steam.client.cmd;

import lombok.Data;

import java.io.Serializable;

@Data
public class Decline implements Serializable {
    private final String tradeofferid;

    public Decline(String tradeofferid) {
        this.tradeofferid = tradeofferid;
    }

    public String getTradeofferid() {
        return tradeofferid;
    }
}
