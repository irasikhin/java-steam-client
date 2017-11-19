package ru.ir.steam.client.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import ru.ir.steam.SteamAuthApi;
import ru.ir.steam.SteamTradeApi;

import javax.inject.Named;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SteamClientModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    @Named("main")
    public Config config() {
        return ConfigFactory.load();
    }

    @Provides
    @Singleton
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Provides
    @Singleton
    public CloseableHttpClient httpClient(@Named("main") Config config) throws UnknownHostException {
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        if (config.hasPath("local.address")) {
            configBuilder.setLocalAddress(InetAddress.getByName(config.getString("local.address")));
        }
        return HttpClients.custom()
                .setDefaultRequestConfig(configBuilder.build())
                .setUserAgent("Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36")
                .build();
    }

    @Provides
    @Singleton
    public SteamAuthApi steamAuthApi(CloseableHttpClient client, ObjectMapper objectMapper) {
        return new SteamAuthApi("https://steamcommunity.com", client, objectMapper);
    }

    @Provides
    @Singleton
    public SteamTradeApi steamTradeApi(CloseableHttpClient client, ObjectMapper objectMapper) {
        return new SteamTradeApi("https://steamcommunity.com", "http://api.steampowered.com", client, objectMapper);
    }
}
