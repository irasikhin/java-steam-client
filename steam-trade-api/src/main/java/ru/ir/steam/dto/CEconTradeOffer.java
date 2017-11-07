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

    public String getTradeid() {
        return tradeid;
    }

    public void setTradeid(String tradeid) {
        this.tradeid = tradeid;
    }

    public String getTradeOfferId() {
        return tradeOfferId;
    }

    public void setTradeOfferId(String tradeOfferId) {
        this.tradeOfferId = tradeOfferId;
    }

    public Long getAccountIdOther() {
        return accountIdOther;
    }

    public void setAccountIdOther(Long accountIdOther) {
        this.accountIdOther = accountIdOther;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Integer getTradeOfferState() {
        return tradeOfferState;
    }

    public void setTradeOfferState(Integer tradeOfferState) {
        this.tradeOfferState = tradeOfferState;
    }

    public List<CEconAsset> getItemsToGive() {
        return itemsToGive;
    }

    public void setItemsToGive(List<CEconAsset> itemsToGive) {
        this.itemsToGive = itemsToGive;
    }

    public List<CEconAsset> getItemsToReceive() {
        return itemsToReceive;
    }

    public void setItemsToReceive(List<CEconAsset> itemsToReceive) {
        this.itemsToReceive = itemsToReceive;
    }

    public Boolean getOurOffer() {
        return isOurOffer;
    }

    public void setOurOffer(Boolean ourOffer) {
        isOurOffer = ourOffer;
    }

    public Long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Long timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Long getTimeUpdated() {
        return timeUpdated;
    }

    public void setTimeUpdated(Long timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

    public Boolean getFromRealTimeTrade() {
        return fromRealTimeTrade;
    }

    public void setFromRealTimeTrade(Boolean fromRealTimeTrade) {
        this.fromRealTimeTrade = fromRealTimeTrade;
    }

    public Long getEscrowEndDate() {
        return escrowEndDate;
    }

    public void setEscrowEndDate(Long escrowEndDate) {
        this.escrowEndDate = escrowEndDate;
    }

    public Long getConfirmationMethod() {
        return confirmationMethod;
    }

    public void setConfirmationMethod(Long confirmationMethod) {
        this.confirmationMethod = confirmationMethod;
    }
}
