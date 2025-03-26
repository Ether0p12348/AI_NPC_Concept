package com.ethanrobins.ai_npc_concept.configs;

import com.ethanrobins.ai_npc_concept.Main;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public enum ConfigType {
    SECRET(Secret.class, "secret.json"),
    CONFIG(Config.class, "config.json"),
    ASSISTANTS_CONFIG(AssistantsConfig.class, "assistants.json"),
    PLAYER_CONFIG(PlayerConfig.class, "players.json"),
    MOOD_DEFS(MoodDefs.class, "tags/mood.json"),
    OPINION_DEFS(OpinionDefs.class, "tags/opinion.json"),
    PERSONALITY_DEFS(PersonalityDefs.class, "tags/personality.json"),
    STYLES_DEFS(StylesDefs.class, "tags/styles.json");

    private final Class<?> loader;
    private final String path;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    ConfigType(Class<?> loader, String path) {
        this.loader = loader;
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    public Class<?> getLoader() {
        return this.loader;
    }

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
