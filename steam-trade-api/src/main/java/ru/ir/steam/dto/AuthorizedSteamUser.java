package ru.ir.steam.dto;

import org.apache.http.client.protocol.HttpClientContext;

import java.io.Serializable;

public class AuthorizedSteamUser implements Serializable {

    private final String username;

    private final HttpClientContext context;

    public AuthorizedSteamUser(String username, HttpClientContext context) {
        this.username = username;
        this.context = context;
    }

    public String getUsername() {
        return username;
    }

    public HttpClientContext getContext() {
        return context;
    }

    @Override
    public String toString() {
        return "AuthorizedSteamUser{" +
                "username='" + username + '\'' +
                ", context=" + context +
                '}';
    }
}
