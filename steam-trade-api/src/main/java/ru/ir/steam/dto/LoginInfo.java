package ru.ir.steam.dto;

import java.io.Serializable;

public class LoginInfo implements Serializable {

    private String username = "";

    private String password = "";

    private String timestamp = "";

    private String captchaGid = "-1";

    private String captchaText = "";

    private String emailSteamId = "";

    private String emailAuth = "";

    public LoginInfo() {
    }

    public LoginInfo(String username, String password, String timestamp) {
        this.username = username;
        this.password = password;
        this.timestamp = timestamp;
    }

    public LoginInfo(String username, String password, String timestamp, String captchaGid, String captchaText) {
        this.username = username;
        this.password = password;
        this.timestamp = timestamp;
        this.captchaGid = captchaGid;
        this.captchaText = captchaText;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCaptchaGid() {
        return captchaGid;
    }

    public void setCaptchaGid(String captchaGid) {
        this.captchaGid = captchaGid;
    }

    public String getCaptchaText() {
        return captchaText;
    }

    public void setCaptchaText(String captchaText) {
        this.captchaText = captchaText;
    }

    public String getEmailSteamId() {
        return emailSteamId;
    }

    public void setEmailSteamId(String emailSteamId) {
        this.emailSteamId = emailSteamId;
    }

    public String getEmailAuth() {
        return emailAuth;
    }

    public void setEmailAuth(String emailAuth) {
        this.emailAuth = emailAuth;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", captchaGid='" + captchaGid + '\'' +
                ", captchaText='" + captchaText + '\'' +
                ", emailSteamId='" + emailSteamId + '\'' +
                ", emailAuth='" + emailAuth + '\'' +
                '}';
    }
}
