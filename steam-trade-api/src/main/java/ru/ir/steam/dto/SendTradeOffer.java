package ru.ir.steam.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SendTradeOffer implements Serializable {

    private final String partner;

    private final String message;

    private final OfferJsonData data;

    private final TradeOfferCreateParams tradeOfferCreateParams;

}
