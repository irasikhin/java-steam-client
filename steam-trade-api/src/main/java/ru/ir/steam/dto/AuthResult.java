package ru.ir.steam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

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

        public String getSteamId() {
            return steamId;
        }

        public void setSteamId(String steamId) {
            this.steamId = steamId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getAuth() {
            return auth;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }

        public Boolean getRememberLogin() {
            return rememberLogin;
        }

        public void setRememberLogin(Boolean rememberLogin) {
            this.rememberLogin = rememberLogin;
        }

        public String getWebCookie() {
            return webCookie;
        }

        public void setWebCookie(String webCookie) {
            this.webCookie = webCookie;
        }

        public String getTokenSecure() {
            return tokenSecure;
        }

        public void setTokenSecure(String tokenSecure) {
            this.tokenSecure = tokenSecure;
        }

        @Override
        public String toString() {
            return "TransferParameters{" +
                    "steamId='" + steamId + '\'' +
                    ", token='" + token + '\'' +
                    ", auth='" + auth + '\'' +
                    ", rememberLogin=" + rememberLogin +
                    ", webCookie='" + webCookie + '\'' +
                    ", tokenSecure='" + tokenSecure + '\'' +
                    '}';
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getCaptchaNeeded() {
        return captchaNeeded;
    }

    public void setCaptchaNeeded(Boolean captchaNeeded) {
        this.captchaNeeded = captchaNeeded;
    }

    public String getCaptchaGid() {
        return captchaGid;
    }

    public void setCaptchaGid(String captchaGid) {
        this.captchaGid = captchaGid;
    }

    public Boolean getEmailAuthNeeded() {
        return emailAuthNeeded;
    }

    public void setEmailAuthNeeded(Boolean emailAuthNeeded) {
        this.emailAuthNeeded = emailAuthNeeded;
    }

    public String getEmailSteamId() {
        return emailSteamId;
    }

    public void setEmailSteamId(String emailSteamId) {
        this.emailSteamId = emailSteamId;
    }

    public Boolean getRequiresTwoFactor() {
        return requiresTwoFactor;
    }

    public void setRequiresTwoFactor(Boolean requiresTwoFactor) {
        this.requiresTwoFactor = requiresTwoFactor;
    }

    public Boolean getLoginComplete() {
        return loginComplete;
    }

    public void setLoginComplete(Boolean loginComplete) {
        this.loginComplete = loginComplete;
    }

    public List<String> getTransferUrls() {
        return transferUrls;
    }

    public void setTransferUrls(List<String> transferUrls) {
        this.transferUrls = transferUrls;
    }

    public TransferParameters getTransferParameters() {
        return transferParameters;
    }

    public void setTransferParameters(TransferParameters transferParameters) {
        this.transferParameters = transferParameters;
    }

    @Override
    public String toString() {
        return "AuthResult{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", captchaNeeded=" + captchaNeeded +
                ", captchaGid='" + captchaGid + '\'' +
                ", emailAuthNeeded=" + emailAuthNeeded +
                ", emailSteamId='" + emailSteamId + '\'' +
                ", requiresTwoFactor=" + requiresTwoFactor +
                ", loginComplete=" + loginComplete +
                ", transferUrls=" + transferUrls +
                ", transferParameters=" + transferParameters +
                '}';
    }
}
