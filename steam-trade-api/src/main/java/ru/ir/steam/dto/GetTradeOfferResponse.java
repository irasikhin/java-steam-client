package ru.ir.steam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class GetTradeOfferResponse implements Serializable {

    @JsonProperty("response")
    private GetTradeOffer response;

}
