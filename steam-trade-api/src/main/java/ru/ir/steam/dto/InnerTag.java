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

}
