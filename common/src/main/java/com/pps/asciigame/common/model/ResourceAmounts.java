package com.pps.asciigame.common.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ResourceAmounts {
    private final Map<ResourceType, Double> resources;

    private ResourceAmounts(@JsonProperty("resources") final Map<ResourceType, Double> resources) {
        this.resources = resources;
    }

    public double getAmount(final ResourceType type) {
        return resources.getOrDefault(type, 0.0);
    }


    public static class Builder {
        private final Map<ResourceType, Double> resources = new HashMap<>();

        public Builder withType(final ResourceType type, final double amount) {
            this.resources.put(type, amount);
            return this;
        }

        public ResourceAmounts build() {
            return new ResourceAmounts(resources);
        }
    }
}
