package ru.ir.steam.internal;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.typesafe.config.Config;
import ru.ir.steam.dto.SecretData;

import java.util.List;

public class SecretDataCache {

    private final LoadingCache<String, SecretData> cache;

    public SecretDataCache(Config config) {
        List<? extends Config> users = config.getConfigList("users");
        this.cache = Caffeine.newBuilder().build(username -> {
            for (Config userConfig : users) {
                if (userConfig.hasPath("steam.username") && username.equals(userConfig.getString("steam.username"))) {
                    return new SecretData(
                            userConfig.getString("steam.key"),
                            userConfig.getString("steam.device.id"),
                            userConfig.getString("steam.password"),
                            userConfig.getString("steam.shared_secret"),
                            userConfig.getString("steam.identity_secret"),
                            userConfig.getString("steam.id"),
                            userConfig.getString("steam.machineAuthKey"),
                            userConfig.getString("steam.machineAuthValue")
                    );
                }
            }
            return null;
        });
    }

    public SecretData getSecretData(String username) {
        return cache.get(username);
    }

}
