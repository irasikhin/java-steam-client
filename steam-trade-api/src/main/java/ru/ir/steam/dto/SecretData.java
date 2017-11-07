package ru.ir.steam.dto;

import java.io.Serializable;

public class SecretData implements Serializable {

    private final String apiKey;

    private final String deviceId;

    private final String password;

    private final String sharedSecret;

    private final String identitySecret;

    private final String steamId;

    private final String machineAuthKey;

    private final String machineAuthValue;

    private final String gmailImapAddress;

    private final String gmailAddress;

    private final String gmailLabel;

    private final String gmailPassword;

    public SecretData(String apiKey, String deviceId, String password, String sharedSecret, String identitySecret, String steamId, String machineAuthKey, String machineAuthValue,
                      String gmailImapAddress, String gmailAddress, String gmailLabel, String gmailPassword) {
        this.apiKey = apiKey;
        this.deviceId = deviceId;
        this.password = password;
        this.sharedSecret = sharedSecret;
        this.identitySecret = identitySecret;
        this.steamId = steamId;
        this.machineAuthKey = machineAuthKey;
        this.machineAuthValue = machineAuthValue;
        this.gmailImapAddress = gmailImapAddress;
        this.gmailAddress = gmailAddress;
        this.gmailLabel = gmailLabel;
        this.gmailPassword = gmailPassword;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getPassword() {
        return password;
    }

    public String getSharedSecret() {
        return sharedSecret;
    }

    public String getIdentitySecret() {
        return identitySecret;
    }

    public String getSteamId() {
        return steamId;
    }

    public String getMachineAuthKey() {
        return machineAuthKey;
    }

    public String getMachineAuthValue() {
        return machineAuthValue;
    }

    public String getGmailImapAddress() {
        return gmailImapAddress;
    }

    public String getGmailAddress() {
        return gmailAddress;
    }

    public String getGmailLabel() {
        return gmailLabel;
    }

    public String getGmailPassword() {
        return gmailPassword;
    }

    @Override
    public String toString() {
        return "SecretData{" +
                "apiKey='" + apiKey + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", password='" + password + '\'' +
                ", sharedSecret='" + sharedSecret + '\'' +
                ", identitySecret='" + identitySecret + '\'' +
                ", steamId='" + steamId + '\'' +
                ", machineAuthKey='" + machineAuthKey + '\'' +
                ", machineAuthValue='" + machineAuthValue + '\'' +
                ", gmailImapAddress='" + gmailImapAddress + '\'' +
                ", gmailAddress='" + gmailAddress + '\'' +
                ", gmailLabel='" + gmailLabel + '\'' +
                ", gmailPassword='" + gmailPassword + '\'' +
                '}';
    }
}
