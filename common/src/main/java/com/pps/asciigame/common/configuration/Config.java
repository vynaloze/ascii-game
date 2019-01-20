package com.pps.asciigame.common.configuration;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Config {
    private static final Logger LOGGER = LogManager.getLogger(Config.class);
    private static XMLConfiguration config;

    static {
        try {
            config = new Configurations().xml("configuration.xml");
        } catch (ConfigurationException e) {
            LOGGER.error(e);
        }
    }

    public static String hostname() {
        return config.getString("server.hostname");
    }

    public static int port() {
        return config.getInt("server.port");
    }

    public static int updatePeriod() {
        return config.getInt("game.updatePeriod");
    }
}
