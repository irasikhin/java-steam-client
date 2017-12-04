package ru.ir.steam.dto;

import lombok.Data;
import org.apache.http.client.protocol.HttpClientContext;

import java.io.Serializable;

@Data
public class AuthorizedSteamUser implements Serializable {

    private final String username;

    private final HttpClientContext context;

}