package ru.ir.steam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class InventoryAsset implements Serializable {

    @JsonProperty("appid")
    private String appId;

    @JsonProperty("contextid")
    private String contextId;

    @JsonProperty("assetid")
    private String assetId;

    @JsonProperty("classid")
    private String classId;

    @JsonProperty("instanceid")
    private String instanceId;

    @JsonProperty("amount")
    private BigDecimal amount;

}
