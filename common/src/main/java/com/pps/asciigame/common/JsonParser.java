package com.pps.asciigame.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class JsonParser {
    private static final Logger LOGGER = LogManager.getLogger(JsonParser.class);
    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
    }

    public static String stringify(final Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return null;
    }

    public static <T> T asObject(final String string, final Class<T> type) {
        try {
            return MAPPER.readerFor(type).readValue(string);
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return null;
    }
}
