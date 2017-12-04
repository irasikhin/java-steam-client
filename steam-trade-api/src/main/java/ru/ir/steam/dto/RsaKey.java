package ru.ir.steam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RsaKey implements Serializable {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("publickey_mod")
    private String publicKeyMod;

    @JsonProperty("publickey_exp")
    private String publicKeyExp;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("token_gid")
    private String tokenGid;

}
