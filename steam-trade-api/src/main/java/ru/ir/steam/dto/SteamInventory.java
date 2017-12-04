package ru.ir.steam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SteamInventory implements Serializable {

    @JsonProperty("success")
    private int success;

    @JsonProperty("total_inventory_count")
    private int totalInventoryCount;

    @JsonProperty("rwgrsn")
    private int rwgrsn;

    @JsonProperty("assets")
    private List<InventoryAsset> assets;

    @JsonProperty("descriptions")
    private List<InventoryDescription> descriptions;

    @JsonProperty("error")
    private String error;

}
