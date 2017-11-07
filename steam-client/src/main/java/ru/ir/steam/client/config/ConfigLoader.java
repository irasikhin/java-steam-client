package ru.ir.steam.client.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ConfigLoader {

    private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);

    public Config load() {
        Config config = ConfigFactory.load();
        Config usersConfig;
        if (config.hasPath("app.home")) {
            logger.debug("Загружаем конфиг из директории: {}", config.getString("app.home") + "/conf");
            String appHome = config.getString("app.home");
            usersConfig = ConfigFactory.parseFile(new File(appHome + "/conf/users.conf")).resolve();
        } else {
            logger.debug("Загружаем конфиг из classpath: users.conf");
            usersConfig = ConfigFactory.load("users.conf").resolve();
        }

        return usersConfig;
    }

}
