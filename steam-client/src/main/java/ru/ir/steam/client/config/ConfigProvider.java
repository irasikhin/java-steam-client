package ru.ir.steam.client.config;

import com.typesafe.config.Config;

public interface ConfigProvider {

    Config getUsersConfig();

}
