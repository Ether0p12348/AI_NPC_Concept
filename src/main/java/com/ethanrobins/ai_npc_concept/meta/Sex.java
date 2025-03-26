package com.ethanrobins.ai_npc_concept.meta;

public enum Sex {
    MALE("male"),
    FEMALE("female"),
    UNKNOWN("queer");

    private final String description;

    Sex (String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
