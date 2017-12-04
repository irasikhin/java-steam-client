package ru.ir.steam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CEconTradeOffer implements Serializable {

    @JsonProperty("tradeid")
    private String tradeid;

    @JsonProperty("tradeofferid")
    private String tradeOfferId;

    @JsonProperty("accountid_other")
    private Long accountIdOther;

    @JsonProperty("message")
    private String message;

    @JsonProperty("expiration_time")
    private Long expirationTime;

    @JsonProperty("trade_offer_state")
    private Integer tradeOfferState;

    @JsonProperty("items_to_give")
    private List<CEconAsset> itemsToGive;

    @JsonProperty("items_to_receive")
    private List<CEconAsset> itemsToReceive;

    @JsonProperty("is_our_offer")
    private Boolean isOurOffer;

    @JsonProperty("time_created")
    private Long timeCreated;

    @JsonProperty("time_updated")
    private Long timeUpdated;

    @JsonProperty("from_real_time_trade")
    private Boolean fromRealTimeTrade;

    @JsonProperty("escrow_end_date")
    private Long escrowEndDate;

    @JsonProperty("confirmation_method")
    private Long confirmationMethod;

}
