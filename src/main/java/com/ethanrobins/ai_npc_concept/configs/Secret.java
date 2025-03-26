package com.ethanrobins.ai_npc_concept.configs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Secret {
    private OpenAI openai;

    public OpenAI getOpenai() {
        return this.openai;
    }

    public void setOpenai(OpenAI openai) {
        this.openai = openai;
    }

    public static class OpenAI {
        private String url;
        private String key;
        private String model;
        @JsonProperty("max_tokens")
        private int maxTokens;

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getKey() {
            return this.key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getModel() {
            return this.model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public int getMaxTokens() {
            return this.maxTokens;
        }

        public void setMaxTokens(int maxTokens) {
            this.maxTokens = maxTokens;
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
