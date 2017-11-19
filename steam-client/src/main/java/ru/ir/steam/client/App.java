package ru.ir.steam.client;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import ru.ir.steam.SteamAuthApi;
import ru.ir.steam.SteamTradeApi;
import ru.ir.steam.client.config.ConfigLoader;
import ru.ir.steam.client.module.SteamClientModule;
import ru.ir.steam.client.service.MainService;

public class App {

    public static void main(final String... args) {
        try {
            final Injector injector = Guice.createInjector(new SteamClientModule());
            final Config config = ConfigFactory.load();
            final ConfigLoader configLoader = new ConfigLoader();
            final Config usersConfig = configLoader.load();
            new MainService(
                    config,
                    usersConfig,
                    injector.getInstance(SteamAuthApi.class),
                    injector.getInstance(SteamTradeApi.class)
            ).start();
        } catch (final Throwable th) {
            th.printStackTrace();
            System.exit(1);
        }
    }

}