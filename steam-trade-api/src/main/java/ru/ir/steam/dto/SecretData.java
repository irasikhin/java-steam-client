package ru.ir.steam.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SecretData implements Serializable {

    private final String apiKey;

    private final String deviceId;

    private final String password;

    private final String sharedSecret;

    private final String identitySecret;

    private final String steamId;

}
