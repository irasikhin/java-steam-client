package ru.ir.steam.client.cmd;

import lombok.Data;

import java.io.Serializable;

@Data
public class Cancel implements Serializable {
    private final String tradeofferid;

    public Cancel(String tradeofferid) {
        this.tradeofferid = tradeofferid;
    }

    public String getTradeofferid() {
        return tradeofferid;
    }
}
