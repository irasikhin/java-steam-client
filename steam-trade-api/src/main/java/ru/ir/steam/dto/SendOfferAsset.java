package ru.ir.steam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SendOfferAsset implements Serializable {

    @JsonProperty("appid")
    private final String appId;

    @JsonProperty("contextid")
    private final String contextId;

    @JsonProperty("assetid")
    private final String assetId;

    @JsonProperty("amount")
    private final BigDecimal amount;

}
