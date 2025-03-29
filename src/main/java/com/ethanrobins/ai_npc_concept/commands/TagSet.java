package com.ethanrobins.ai_npc_concept.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class TagSet<T> implements Taggable<T> {
    public static List<TagSet<?>> tagSets = Collections.synchronizedList(new ArrayList<>());

    protected List<Tag<?>> tags = new ArrayList<>();
    protected String name;
    protected String description;
    protected String usage;
    protected Inclusivity inclusivity;

    @SafeVarargs
    public TagSet(@NotNull String name, @Nullable String description, @Nullable String usage, @Nullable Inclusivity inclusivity, Tag<?>... tags) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.tags.addAll(List.of(tags));
        this.inclusivity = inclusivity != null ? inclusivity : Inclusivity.INCLUSIVE;

        if (tagSets.stream().noneMatch(tagSet -> tagSet.getName().equalsIgnoreCase(name))) {
            tagSets.add(this);
        }
    }

    //public abstract T use(String[] args);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public TagSet<T> setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUsage() {
        return usage;
    }

    public TagSet<T> setUsage(String usage) {
        this.usage = usage;
        return this;
    }

    public List<Tag<?>> getTags() {
        return new ArrayList<>(tags);
    }

    @SafeVarargs
    public final TagSet<T> addTag(Tag<T>... tag) {
        this.tags.addAll(List.of(tag));
        return this;
    }

    public TagSet<T> clearTags() {
        this.tags.clear();
        return this;
    }

    public TagSet<?> setTags(List<Tag<?>> tags) {
        this.tags = tags;
        return this;
    }

    public static List<TagSet<?>> getAllTagSets() {
        return new ArrayList<>(tagSets);
    }

    public static TagSet<?> getTagSet(String name) {
        for (TagSet<?> t : tagSets) {
            if (t.getName().equalsIgnoreCase(name)) {
                return t;
            }
        }

        return null;
    }

    public boolean validate (@NotNull CommandString.Tag[] providedTags, @Nullable Inclusivity overrideInclusivity) {
        Inclusivity rule = overrideInclusivity != null ? overrideInclusivity : this.inclusivity;
        long matchingTagCount = Arrays.stream(providedTags).toList().stream().filter(tag -> Arrays.stream(providedTags).toList().stream().anyMatch(provided -> provided.getName().equalsIgnoreCase(tag.getName()))).count();

        return switch (rule) {
            case INCLUSIVE -> true;
            case EXCLUSIVE -> matchingTagCount <= 1;
            case REQUIRED_INCLUSIVE -> matchingTagCount >= 1;
            case REQUIRED_EXCLUSIVE -> matchingTagCount == 1;
            case RESTRICTED -> matchingTagCount == 0;
            default -> throw new IllegalStateException("Unexpected inclusivity rule: " + inclusivity);
        };
    }
}
