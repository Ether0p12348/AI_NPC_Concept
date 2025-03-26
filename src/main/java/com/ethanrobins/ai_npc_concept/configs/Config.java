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
        @JsonProperty("persona_id")
        private String personaId;
        private double temperature;

        public String getPersonaId() {
            return personaId;
        }

        public void setPersonaId(String personaId) {
            this.personaId = personaId;
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
                    "personaId='" + personaId + '\'' +
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
