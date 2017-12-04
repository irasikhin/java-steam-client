package ru.ir.steam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OfferJsonData {

    @JsonProperty("newversion")
    private final boolean newversion = true;

    @JsonProperty("version")
    private final int version = 2;

    @JsonProperty("me")
    private final OfferPersonData me;

    @JsonProperty("them")
    private final OfferPersonData them;

}
