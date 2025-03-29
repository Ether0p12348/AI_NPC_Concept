package com.ethanrobins.ai_npc_concept.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Tag<T> implements Taggable<T> {
    public static List<Tag<?>> tags = Collections.synchronizedList(new ArrayList<>());

    protected String name;
    protected String description;
    protected String usage;

    public Tag(String name, String description, String usage) {
        this.name = name;
        this.description = description;
        this.usage = usage;

        if (tags.stream().noneMatch(tag -> tag.getName().equalsIgnoreCase(name))) {
            tags.add(this);
        }
    }

    public abstract T use(String value);

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Tag<T> setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String getUsage() {
        return usage;
    }

    @Override
    public Tag<T> setUsage(String usage) {
        this.usage = usage;
        return this;
    }

    public static List<Tag<?>> getAllTags() {
        return new ArrayList<>(tags);
    }

    public static Tag<?> getTag(String name) {
        for (Tag<?> t : tags) {
            if (t.getName().equalsIgnoreCase(name)) {
                return t;
            }
        }

        return null;
    }
}
