package ru.ir.steam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GetTradeOffersOutput implements Serializable {

    @JsonProperty("trade_offers_sent")
    private List<CEconTradeOffer> tradeOffersSent;

    @JsonProperty("trade_offers_received")
    private List<CEconTradeOffer> tradeOffersReceived;

    public List<CEconTradeOffer> getTradeOffersSent() {
        return tradeOffersSent;
    }

    public void setTradeOffersSent(List<CEconTradeOffer> tradeOffersSent) {
        this.tradeOffersSent = tradeOffersSent;
    }

    public List<CEconTradeOffer> getTradeOffersReceived() {
        return tradeOffersReceived;
    }

    public void setTradeOffersReceived(List<CEconTradeOffer> tradeOffersReceived) {
        this.tradeOffersReceived = tradeOffersReceived;
    }
}
