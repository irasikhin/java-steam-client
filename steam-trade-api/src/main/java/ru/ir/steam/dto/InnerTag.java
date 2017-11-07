package ru.ir.steam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class InnerTag implements Serializable {

    @JsonProperty("category")
    private String category;

    @JsonProperty("internal_name")
    private String internalName;

    @JsonProperty("localized_category_name")
    private String localizedCategoryName;

    @JsonProperty("localized_tag_name")
    private String localizedTagName;

    @JsonProperty("color")
    private String color;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getInternalName() {
        return internalName;
    }

    public void setInternalName(String internalName) {
        this.internalName = internalName;
    }

    public String getLocalizedCategoryName() {
        return localizedCategoryName;
    }

    public void setLocalizedCategoryName(String localizedCategoryName) {
        this.localizedCategoryName = localizedCategoryName;
    }

    public String getLocalizedTagName() {
        return localizedTagName;
    }

    public void setLocalizedTagName(String localizedTagName) {
        this.localizedTagName = localizedTagName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
