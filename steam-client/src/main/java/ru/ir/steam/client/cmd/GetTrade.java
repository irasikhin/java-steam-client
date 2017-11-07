package ru.ir.steam.client.cmd;

import lombok.Data;

import java.io.Serializable;

@Data
public class GetTrade implements Serializable {
    private final String tradeofferid;
    private final String language;

    public GetTrade(String tradeofferid) {
        this(tradeofferid, "ru");
    }

    public GetTrade(String tradeofferid, String language) {
        this.tradeofferid = tradeofferid;
        this.language = language;
    }

    public String getTradeofferid() {
        return tradeofferid;
    }

    public String getLanguage() {
        return language;
    }
}
