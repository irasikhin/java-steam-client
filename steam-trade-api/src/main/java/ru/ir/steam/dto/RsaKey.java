package ru.ir.steam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getPublicKeyMod() {
        return publicKeyMod;
    }

    public void setPublicKeyMod(String publicKeyMod) {
        this.publicKeyMod = publicKeyMod;
    }

    public String getPublicKeyExp() {
        return publicKeyExp;
    }

    public void setPublicKeyExp(String publicKeyExp) {
        this.publicKeyExp = publicKeyExp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTokenGid() {
        return tokenGid;
    }

    public void setTokenGid(String tokenGid) {
        this.tokenGid = tokenGid;
    }

    @Override
    public String toString() {
        return "RsaKey{" +
                "success=" + success +
                ", publicKeyMod='" + publicKeyMod + '\'' +
                ", publicKeyExp='" + publicKeyExp + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", tokenGid='" + tokenGid + '\'' +
                '}';
    }
}
