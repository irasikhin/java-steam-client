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

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getIconUrlLarge() {
        return iconUrlLarge;
    }

    public void setIconUrlLarge(String iconUrlLarge) {
        this.iconUrlLarge = iconUrlLarge;
    }

    public List<InnerDescription> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<InnerDescription> descriptions) {
        this.descriptions = descriptions;
    }

    public int getTradable() {
        return tradable;
    }

    public void setTradable(int tradable) {
        this.tradable = tradable;
    }

    public List<InnerAction> getActions() {
        return actions;
    }

    public void setActions(List<InnerAction> actions) {
        this.actions = actions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameColor() {
        return nameColor;
    }

    public void setNameColor(String nameColor) {
        this.nameColor = nameColor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getMarketHashName() {
        return marketHashName;
    }

    public void setMarketHashName(String marketHashName) {
        this.marketHashName = marketHashName;
    }

    public List<InnerAction> getMarketActions() {
        return marketActions;
    }

    public void setMarketActions(List<InnerAction> marketActions) {
        this.marketActions = marketActions;
    }

    public Integer getCommodity() {
        return commodity;
    }

    public void setCommodity(Integer commodity) {
        this.commodity = commodity;
    }

    public Integer getMarketTradableRestriction() {
        return marketTradableRestriction;
    }

    public void setMarketTradableRestriction(Integer marketTradableRestriction) {
        this.marketTradableRestriction = marketTradableRestriction;
    }

    public Integer getMarketable() {
        return marketable;
    }

    public void setMarketable(Integer marketable) {
        this.marketable = marketable;
    }

    public List<InnerTag> getTags() {
        return tags;
    }

    public void setTags(List<InnerTag> tags) {
        this.tags = tags;
    }
}
