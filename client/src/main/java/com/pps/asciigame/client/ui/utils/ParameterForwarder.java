package com.pps.asciigame.client.ui.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ParameterForwarder {
    private final Map<Class, Object> params = new HashMap<>();

    public <T> void pass(final T object, final Class<T> clazz) {
        params.put(clazz, object);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(final Class<T> clazz) {
        return (T) params.get(clazz);
    }

    public <T> void remove(final Class<T> clazz) {
        params.remove(clazz);
    }
}
