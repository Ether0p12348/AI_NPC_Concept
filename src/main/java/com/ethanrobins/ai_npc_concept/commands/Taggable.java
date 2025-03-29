package com.ethanrobins.ai_npc_concept.commands;

public interface Taggable<T> {
    String getName();
    String getDescription();
    Taggable<T> setDescription(String description);
    String getUsage();
    Taggable<T> setUsage(String usage);
}
