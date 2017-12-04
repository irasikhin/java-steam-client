package ru.ir.steam.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InventoryDescription implements Serializable {

    @JsonProperty("appid")
    private int appId;

    @JsonProperty("classid")
    private String classId;

    @JsonProperty("instanceid")
    private String instanceId;

    @JsonProperty("currency")
    private int currency;

    @JsonProperty("background_color")
    private String backgroundColor;

    @JsonProperty("icon_url")
    private String iconUrl;

    @JsonProperty("icon_url_large")
    private String iconUrlLarge;

    @JsonProperty("descriptions")
    private List<InnerDescription> descriptions;

    @JsonProperty("tradable")
    private int tradable;

    @JsonProperty("actions")
    private List<InnerAction> actions;

    @JsonProperty("name")
    private String name;

    @JsonProperty("name_color")
    private String nameColor;

    @JsonProperty("type")
    private String type;

    @JsonProperty("market_name")
    private String marketName;

    @JsonProperty("market_hash_name")
    private String marketHashName;

    @JsonProperty("market_actions")
    private List<InnerAction> marketActions;

    @JsonProperty("commodity")
    private Integer commodity;

    @JsonProperty("market_tradable_restriction")
    private Integer marketTradableRestriction;

    @JsonProperty("marketable")
    private Integer marketable;

    @JsonProperty("tags")
    private List<InnerTag> tags;

}
