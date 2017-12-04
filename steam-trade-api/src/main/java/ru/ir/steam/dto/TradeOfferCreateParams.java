package ru.ir.steam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TradeOfferCreateParams {

    @JsonProperty("trade_offer_access_token")
    private final String tradeOfferAccessToken;

}
