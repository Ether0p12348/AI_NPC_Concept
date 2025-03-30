package com.ethanrobins.ai_npc_concept.configs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

/**
 * <b>Config</b> - Represents general application configuration settings.
 * <p>This class is deserialized from {@code config.json} and includes settings such as logging preferences
 * and default assistant/player behavior.</p>
 *
 * <br><b>Fields:</b>
 * <ul>
 *   <li>{@link #logging} - Logging configuration values.</li>
 *   <li>{@link #defaults} - Default assistant, player, and temperature settings.</li>
 * </ul>
 *
 * <br><b>Methods:</b>
 * <ul>
 *   <li>{@link #getLogging()}</li>
 *   <li>{@link #getDefaults()}</li>
 * </ul>
 *
 * @see Logging
 * @see Defaults
 */
public class Config {
    /**
     * Logging configuration block.
     */
    private final Logging logging;
    /**
     * Default configuration values such as assistant ID and temperature.
     */
    private final Defaults defaults;

    /**
     * Constructs a {@link Config} object from JSON.
     *
     * @param logging {@link Config#logging}
     * @param defaults {@link Config#defaults}
     */
    @JsonCreator
    public Config(@JsonProperty("logging") @Nullable Logging logging, @JsonProperty("defaults") @Nullable Defaults defaults) {
        this.logging = logging != null ? logging : new Logging(null);
        this.defaults = defaults != null ? defaults : new Defaults(null, null, null);
    }

    /**
     * Gets the logging configuration.
     *
     * @return {@link #logging}
     */
    public Logging getLogging() {
        return logging;
    }

    /**
     * Gets the default behavior configuration.
     *
     * @return {@link #defaults}
     */
    public Defaults getDefaults() {
        return defaults;
    }

    /**
     * <b>Config.Logging</b> - Defines the logging level for the application.
     * <p>This is used to determine the verbosity of log output (e.g., INFO or DEBUG).</p>
     *
     * <br><b>Fields:</b>
     * <ul>
     *   <li>{@link #level}</li>
     * </ul>
     *
     * <br><b>Methods:</b>
     * <ul>
     *   <li>{@link #getLevel()}</li>
     * </ul>
     *
     * @see Config
     */
    public static class Logging {
        /**
         * The verbosity level for log output (e.g., "info", "debug").
         */
        private final String level;

        /**
         * Constructs a {@link Logging} object from JSON.
         *
         * @param level {@link #level}
         */
        @JsonCreator
        public Logging(@JsonProperty("level") @Nullable String level) {
            this.level = level != null ? level : "INFO";
        }

        /**
         * Retrieves the configured log level.
         *
         * @return {@link Config.Logging#level}
         */
        public String getLevel() {
            return level;
        }

        @Override
        public String toString() {
            return "Logging{" +
                    "level='" + level + '\'' +
                    '}';
        }
    }

    /**
     * <b>Config.Defaults</b> - Specifies default values for assistant, player, and temperature.
     * <p>This block allows predefining fallback behavior when no values are supplied at runtime.</p>
     *
     * <br><b>Fields:</b>
     * <ul>
     *   <li>{@link #assistantId}</li>
     *   <li>{@link #playerId}</li>
     *   <li>{@link #temperature}</li>
     * </ul>
     *
     * <br><b>Methods:</b>
     * <ul>
     *   <li>{@link #getAssistantId()}</li>
     *   <li>{@link #getPlayerId()}</li>
     *   <li>{@link #getTemperature()}</li>
     * </ul>
     *
     * @see Config
     */
    public static class Defaults {
        /**
         * Default assistant ID to use at startup or when none is selected.
         */
        private final String assistantId;
        /**
         * Default player ID to use at startup or when none is selected.
         */
        private final String playerId;
        /**
         * Default temperature used in language model configuration.
         */
        private final double temperature;

        /**
         * Constructs a {@link Defaults} object from JSON.
         *
         * @param assistantId {@link #assistantId}
         * @param playerId {@link #playerId}
         * @param temperature {@link #temperature}
         */
        @JsonCreator
        public Defaults(@JsonProperty("assistant_id") @Nullable String assistantId, @JsonProperty("player_id") @Nullable String playerId, @JsonProperty("temperature") @Nullable Double temperature) {
            this.assistantId = assistantId != null ? assistantId : "001";
            this.playerId = playerId != null ? playerId : "001";
            this.temperature = temperature != null ? temperature : 0.7;
        }

        /**
         * Retrieves the default assistant ID used at startup.
         *
         * @return {@link Config.Defaults#assistantId}
         */
        public String getAssistantId() {
            return assistantId;
        }

        /**
         * Retrieves the default player ID used at startup.
         *
         * @return {@link Config.Defaults#playerId}
         */
        public String getPlayerId() {
            return playerId;
        }

        /**
         * Retrieves the default temperature used in AI generation.
         *
         * @return {@link Config.Defaults#temperature}
         */
        public double getTemperature() {
            return temperature;
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
    // TODO: Create 'builder' methods and 'from' methods for config classes
}
