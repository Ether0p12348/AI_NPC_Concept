package com.ethanrobins.ai_npc_concept.configs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

/**
 * <b>Secret</b> - Represents sensitive API credential and configuration data for external services.
 * <p>This class is typically loaded from a secure configuration file (e.g., {@code secret.json}) and includes
 * settings for connecting to services such as OpenAI.</p>
 *
 * <br><b>Fields:</b>
 * <ul>
 *   <li>{@link #openai} - Encapsulates OpenAI-specific configuration data.</li>
 * </ul>
 *
 * <br><b>Methods:</b>
 * <ul>
 *   <li>{@link #getOpenai()}</li>
 * </ul>
 *
 * <b>Usage:</b>
 * <p>Used internally to supply API keys, model configuration, and connection details to the AI layer.</p>
 *
 * @see Secret.OpenAI
 */
public class Secret {
    /**
     * OpenAI-specific configuration settings.
     */
    private final OpenAI openai;

    /**
     * Constructs a {@link Secret} object from JSON.
     * @param openai {@link #openai}
     */
    @JsonCreator
    public Secret(@JsonProperty("openai") @Nullable OpenAI openai) {
        this.openai = openai != null ? openai : new OpenAI(null, null, null, null);
    }

    /**
     * Retrieves the OpenAI configuration block.
     *
     * @return {@link #openai}
     */
    public OpenAI getOpenai() {
        return this.openai;
    }

    /**
     * <b>Secret.OpenAI</b> - Represents configuration settings required for accessing the OpenAI API.
     * <p>Includes the API endpoint, secret key, model identifier, and maximum token usage per request.</p>
     *
     * <br><b>Fields:</b>
     * <ul>
     *   <li>{@link #url}</li>
     *   <li>{@link #key}</li>
     *   <li>{@link #model}</li>
     *   <li>{@link #maxTokens}</li>
     * </ul>
     *
     * <br><b>Methods:</b>
     * <ul>
     *   <li>{@link #getUrl()}</li>
     *   <li>{@link #getKey()}</li>
     *   <li>{@link #getModel()}</li>
     *   <li>{@link #getMaxTokens()}</li>
     * </ul>
     *
     * @see Secret
     */
    public static class OpenAI {
        /**
         * The full API URL endpoint for OpenAI.
         */
        private final String url;
        /**
         * The secret API key used for authentication.
         */
        private final String key;
        /**
         * The model identifier to use (e.g., {@code gpt-4o-mini}).
         */
        private final String model;
        /**
         * The maximum number of tokens allowed per API request.
         */
        private final int maxTokens;

        /**
         * Constructs an {@link OpenAI} object from JSON.
         *
         * @param url {@link #url}
         * @param key {@link #key}
         * @param model {@link #model}
         * @param maxTokens {@link #maxTokens}
         *
         * @throws NullPointerException if {@link #key} is {@code null}.
         */
        @JsonCreator
        public OpenAI(@JsonProperty("url") @Nullable String url, @JsonProperty("key") String key, @JsonProperty("model") @Nullable String model, @JsonProperty("max_tokens") @Nullable Integer maxTokens) throws NullPointerException {
            this.url = url != null ? url : "https://api.openai.com/v1/assistants/";
            if (key == null) throw new NullPointerException("OpenAI API key cannot be null.");
            this.key = key;
            this.model = model != null ? model : "gpt-4o-mini";
            this.maxTokens = maxTokens != null ? maxTokens : 800;
        }

        /**
         * Gets the OpenAI API URL.
         *
         * @return {@link #url}
         */
        public String getUrl() {
            return this.url;
        }

        /**
         * Gets the secret OpenAI API key.
         *
         * @return {@link #key}
         */
        public String getKey() {
            return this.key;
        }

        /**
         * Gets the model name used for completions or chat.
         *
         * @return {@link #model}
         */
        public String getModel() {
            return this.model;
        }

        /**
         * Gets the maximum number of tokens per API call.
         *
         * @return {@link #maxTokens}
         */
        public int getMaxTokens() {
            return this.maxTokens;
        }

        @Override
        public String toString() {
            return "OpenAI{" +
                    "url='" + url + '\'' +
                    ", key='" + key + '\'' +
                    ", model='" + model + '\'' +
                    ", maxTokens=" + maxTokens +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Secret{" +
                "openai=" + openai +
                '}';
    }
}
