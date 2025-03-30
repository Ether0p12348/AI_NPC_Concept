package com.ethanrobins.ai_npc_concept.configs;

import com.ethanrobins.ai_npc_concept.Main;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * <b>ConfigType Enum</b> - Represents the different configuration files and their associated object types.
 * <p>This enum defines a mapping between configuration JSON files and the Java classes used to deserialize them.
 * It is used to simplify access to config resources throughout the application.</p>
 *
 * <br><b>Enum Constants:</b>
 * <ul>
 *   <li>{@link #SECRET}</li>
 *   <li>{@link #CONFIG}</li>
 *   <li>{@link #ASSISTANTS_CONFIG}</li>
 *   <li>{@link #PLAYER_CONFIG}</li>
 *   <li>{@link #MOOD_DEFS}</li>
 *   <li>{@link #OPINION_DEFS}</li>
 *   <li>{@link #PERSONALITY_DEFS}</li>
 *   <li>{@link #STYLES_DEFS}</li>
 * </ul>
 *
 * <b>Usage:</b>
 * <p>Use this enum to fetch a strongly typed config object from its associated JSON file.</p>
 */
public enum ConfigType {
    /**
     * Loads {@code secret.json} as a {@link Secret}.
     */
    SECRET(Secret.class, "secret.json"),
    /**
     * Loads {@code config.json} as a {@link Config}.
     */
    CONFIG(Config.class, "config.json"),
    /**
     * Loads {@code assistants.json} as a {@link AssistantConfig}.
     */
    ASSISTANTS_CONFIG(AssistantConfig.class, "assistants.json"),
    /**
     * Loads {@code players.json} as a {@link PlayerConfig}.
     */
    PLAYER_CONFIG(PlayerConfig.class, "players.json"),
    /**
     * Loads {@code tags/mood.json} as a {@link MoodDefs}.
     */
    MOOD_DEFS(MoodDefs.class, "tags/mood.json"),
    /**
     * Loads {@code tags/opinion.json} as a {@link OpinionDefs}.
     */
    OPINION_DEFS(OpinionDefs.class, "tags/opinion.json"),
    /**
     * Loads {@code tags/personalities.json} as a {@link PersonalityDefs}.
     */
    PERSONALITY_DEFS(PersonalityDefs.class, "tags/personality.json"),
    /**
     * Loads {@code tags/styles.json} as a {@link StylesDefs}.
     */
    STYLES_DEFS(StylesDefs.class, "tags/styles.json");

    /**
     * The class type used to deserialize the JSON content.
     */
    private final Class<?> loader;
    /**
     * The resource path to the config file.
     */
    private final String path;

    /**
     * Shared Jackson object mapper used to load configuration files.
     */
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Constructs a {@link ConfigType} enum constant with its loader and path.
     *
     * @param loader The target class to deserialize the config file into.
     * @param path The path to the config file in the resources directory.
     * @see ConfigType
     */
    ConfigType(Class<?> loader, String path) {
        this.loader = loader;
        this.path = path;
    }

    /**
     * Retrieves the resource path of the configuration file.
     *
     * @return {@link ConfigType#path}
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Retrieves the class type used to deserialize this configuration.
     *
     * @return {@link ConfigType#loader}
     */
    public Class<?> getLoader() {
        return this.loader;
    }

    /**
     * Loads and returns the deserialized configuration object.
     *
     * <br><b>Behavior:</b>
     * <ul>
     *   <li>Attempts to load the config from the classpath.</li>
     *   <li>If the file is not found or unreadable, throws an exception.</li>
     * </ul>
     *
     * @return The deserialized configuration object as {@link Object}.
     * @throws IOException If the config file cannot be loaded or parsed.
     */
    public Object get() throws IOException {
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(this.path)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Resource not found: " + this.path);
            }
            return objectMapper.readValue(inputStream, this.loader);
        } catch (IOException e) {
            throw new IOException("Could not load config file: " + this.path, e);
        }
    }
}
