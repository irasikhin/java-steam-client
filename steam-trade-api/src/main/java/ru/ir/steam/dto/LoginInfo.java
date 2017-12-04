package ru.ir.steam.dto;

import lombok.Data;

import java.io.Serializable;

@Data
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

}
