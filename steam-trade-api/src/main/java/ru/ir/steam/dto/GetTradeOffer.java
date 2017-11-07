package ru.ir.steam.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetTradeOffer implements Serializable {

    @JsonProperty("offer")
    private CEconTradeOffer offer;

    public CEconTradeOffer getOffer() {
        return offer;
    }

    public void setOffer(CEconTradeOffer offer) {
        this.offer = offer;
    }
}
