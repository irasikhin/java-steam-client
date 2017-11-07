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

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getTotalInventoryCount() {
        return totalInventoryCount;
    }

    public void setTotalInventoryCount(int totalInventoryCount) {
        this.totalInventoryCount = totalInventoryCount;
    }

    public int getRwgrsn() {
        return rwgrsn;
    }

    public void setRwgrsn(int rwgrsn) {
        this.rwgrsn = rwgrsn;
    }

    public List<InventoryAsset> getAssets() {
        return assets;
    }

    public void setAssets(List<InventoryAsset> assets) {
        this.assets = assets;
    }

    public List<InventoryDescription> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<InventoryDescription> descriptions) {
        this.descriptions = descriptions;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
