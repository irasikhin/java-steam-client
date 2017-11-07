package ru.ir.steam;

import com.typesafe.config.ConfigFactory;
import ru.ir.steam.dto.SecretData;
import ru.ir.steam.internal.SecretDataCache;

public class SteamApp {

    public static void main(String... args) {
        SecretDataCache secretDataCache = new SecretDataCache(ConfigFactory.load());

        SecretData secretData = secretDataCache.getSecretData("nettierichards18");
        System.out.println("secretDate: " + secretData);
    }

}
