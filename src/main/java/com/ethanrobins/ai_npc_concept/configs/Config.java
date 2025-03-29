package com.ethanrobins.ai_npc_concept.configs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Config {
    private Logging logging;
    private Defaults defaults;

    public Logging getLogging() {
        return logging;
    }

    public void setLogging(Logging logging) {
        this.logging = logging;
    }

    public Defaults getDefaults() {
        return defaults;
    }

    public void setDefaults(Defaults defaults) {
        this.defaults = defaults;
    }

    public static class Logging {
        private String level;

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        @Override
        public String toString() {
            return "Logging{" +
                    "level='" + level + '\'' +
                    '}';
        }
    }

    public static class Defaults {
        @JsonProperty("assistant_id")
        private String assistantId;
        @JsonProperty("player_id")
        private String playerId;
        private double temperature;

        public String getAssistantId() {
            return assistantId;
        }

        public void setAssistantId(String assistantId) {
            this.assistantId = assistantId;
        }

        public String getPlayerId() {
            return playerId;
        }

        public void setPlayerId(String playerId) {
            this.playerId = playerId;
        }

        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }

        @Override
        public String toString() {
            return "Defaults{" +
                    "assistantId='" + assistantId + '\'' +
                    "playerId='" + playerId + '\'' +
                    ", temperature=" + temperature +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Config{" +
                "logging=" + logging +
                ", defaults=" + defaults +
                '}';
    }
}
