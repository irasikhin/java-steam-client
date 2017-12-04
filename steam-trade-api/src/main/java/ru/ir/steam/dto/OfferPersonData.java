package ru.ir.steam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class OfferPersonData {

    @JsonProperty("currency")
    private final List<String> currency = Collections.emptyList();

    @JsonProperty("ready")
    private final boolean ready = false;

    @JsonProperty("assets")
    private final List<SendOfferAsset> assets;

}
