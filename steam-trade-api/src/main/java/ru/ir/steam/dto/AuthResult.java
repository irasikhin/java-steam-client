package ru.ir.steam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AuthResult implements Serializable {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("message")
    private String message;

    @JsonProperty("captcha_needed")
    private Boolean captchaNeeded;

    @JsonProperty("captcha_gid")
    private String captchaGid;

    @JsonProperty("emailauth_needed")
    private Boolean emailAuthNeeded;

    @JsonProperty("emailsteamid")
    private String emailSteamId;

    @JsonProperty("requires_twofactor")
    private Boolean requiresTwoFactor;

    @JsonProperty("login_complete")
    private Boolean loginComplete;

    @JsonProperty("transfer_urls")
    private List<String> transferUrls;

    @JsonProperty("transfer_parameters")
    private TransferParameters transferParameters;

    @Data
    public static final class TransferParameters {

        @JsonProperty("steamid")
        private String steamId;

        @JsonProperty("token")
        private String token;

        @JsonProperty("auth")
        private String auth;

        @JsonProperty("remember_login")
        private Boolean rememberLogin;

        @JsonProperty("webcookie")
        private String webCookie;

        @JsonProperty("token_secure")
        private String tokenSecure;

    }

}
