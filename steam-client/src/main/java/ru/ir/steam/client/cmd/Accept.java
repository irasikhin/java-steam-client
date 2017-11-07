package ru.ir.steam.client.cmd;

import lombok.Data;

import java.io.Serializable;

@Data
public class Accept implements Serializable {
    private final String tradeofferid;

    public Accept(String tradeofferid) {
        this.tradeofferid = tradeofferid;
    }

    public String getTradeofferid() {
        return tradeofferid;
    }
}
