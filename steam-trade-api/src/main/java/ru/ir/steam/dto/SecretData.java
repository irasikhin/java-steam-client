package ru.ir.steam.dto;

import java.io.Serializable;

public class SecretData implements Serializable {

    private final String apiKey;

    private final String deviceId;

    private final String password;

    private final String sharedSecret;

    private final String identitySecret;

    private final String steamId;

    public SecretData(String apiKey, String deviceId, String password, String sharedSecret, String identitySecret, String steamId) {
        this.apiKey = apiKey;
        this.deviceId = deviceId;
        this.password = password;
        this.sharedSecret = sharedSecret;
        this.identitySecret = identitySecret;
        this.steamId = steamId;
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

    @Override
    public String toString() {
        return "SecretData{" +
                "apiKey='" + apiKey + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", password='" + password + '\'' +
                ", sharedSecret='" + sharedSecret + '\'' +
                ", identitySecret='" + identitySecret + '\'' +
                ", steamId='" + steamId + '\'' +
                '}';
    }
}
