package ru.ir.steam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class InnerAction implements Serializable {

    @JsonProperty("link")
    private String link;

    @JsonProperty("name")
    private String name;

}
