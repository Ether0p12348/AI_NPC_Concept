package com.ethanrobins.ai_npc_concept.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <b>TagSet Class</b> - Represents a named collection of {@link Tag} instances grouped under a shared {@link Inclusivity} rule.
 * <p>This class implements {@link Taggable} and acts as a container for validating combinations of tags within
 * a command string or filtering system. TagSets are globally registered and accessible via static lookup methods.</p>
 *
 * <br><b>Fields:</b>
 * <ul>
 *   <li>{@link TagSet#tags}</li>
 *   <li>{@link TagSet#name}</li>
 *   <li>{@link TagSet#description}</li>
 *   <li>{@link TagSet#usage}</li>
 *   <li>{@link TagSet#inclusivity}</li>
 * </ul>
 *
 * <br><b>Static Registry:</b>
 * <ul>
 *   <li>{@link TagSet#tagSets}</li>
 *   <li>{@link TagSet#getAllTagSets()}</li>
 *   <li>{@link TagSet#getTagSet(String)}</li>
 * </ul>
 *
 * <br><b>Methods:</b>
 * <ul>
 *   <li>{@link TagSet#getTags()}</li>
 *   <li>{@link TagSet#addTag(Tag[])}</li>
 *   <li>{@link TagSet#clearTags()}</li>
 *   <li>{@link TagSet#setTags(List)}</li>
 *   <li>{@link TagSet#validate(CommandString.Tag[], Inclusivity)}</li>
 *   <li>{@link TagSet#getName()}, {@link TagSet#getDescription()}, {@link TagSet#setDescription(String)}</li>
 *   <li>{@link TagSet#getUsage()}, {@link TagSet#setUsage(String)}</li>
 * </ul>
 *
 * @param <T> The output type expected from tags inside this tag set.
 */
public abstract class TagSet<T> implements Taggable<T>, CommandPart {
    /**
     * A synchronized list of all registered {@link TagSet} instances.
     */
    public static List<TagSet<?>> tagSets = Collections.synchronizedList(new ArrayList<>());

    /**
     * The list of tags associated with this set.
     */
    protected List<Tag<?>> tags = new ArrayList<>();
    /**
     * The unique name of this tag set.
     */
    protected String name;
    /**
     * A human-readable description of this tag set’s purpose.
     */
    protected String description;
    /**
     * A usage string (format or help text) associated with this tag set.
     */
    protected String usage;
    /**
     * The inclusivity rule applied when validating tags in this set.
     */
    protected Inclusivity inclusivity;

    /**
     * Constructs a new {@link TagSet} with the given parameters.
     *
     * @param name The name of the tag set.
     * @param description A description of its behavior or purpose.
     * @param usage Usage/help text for formatting guidance.
     * @param inclusivity The inclusion rule used for validation.
     * @param tags One or more tags to include in the set.
     * @throws IllegalArgumentException if a tagset with this name already exists.
     */
    @SafeVarargs
    public TagSet(@NotNull String name, @Nullable String description, @Nullable String usage, @Nullable Inclusivity inclusivity, Tag<?>... tags) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.tags.addAll(List.of(tags));
        this.inclusivity = inclusivity != null ? inclusivity : Inclusivity.INCLUSIVE;

        if (tagSets.stream().noneMatch(tagSet -> tagSet.getName().equalsIgnoreCase(name))) {
            tagSets.add(this);
        } else {
            throw new IllegalArgumentException("TagSet with name \"" + name + "\" already exists.");
        }
    }

    //public abstract T use(String[] args);

    /**
     * Retrieves the name of this tag set.
     *
     * @return {@link #name}
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the description of this tag set.
     *
     * @return {@link #description}
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets or updates the description of this tag set.
     *
     * @param description {@link #description}
     * @return this {@link TagSet}
     */
    public TagSet<T> setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Retrieves the usage or formatting string for this tag set.
     *
     * @return {@link #usage}
     */
    public String getUsage() {
        return usage;
    }

    /**
     * Sets or updates the usage/help string.
     *
     * @param usage {@link #usage}
     * @return this {@link TagSet}
     */
    public TagSet<T> setUsage(String usage) {
        this.usage = usage;
        return this;
    }

    /**
     * Retrieves all tags in this tag set.
     *
     * @return A new list containing all {@link Tag} objects.
     */
    public List<Tag<?>> getTags() {
        return new ArrayList<>(tags);
    }

    /**
     * Appends one or more tags to this tag set.
     *
     * @param tag One or more tags to add.
     * @return this {@link TagSet}
     */
    @SafeVarargs
    public final TagSet<T> addTag(Tag<T>... tag) {
        this.tags.addAll(List.of(tag));
        return this;
    }

    /**
     * Removes all tags from this tag set.
     *
     * @return this {@link TagSet}
     */
    public TagSet<T> clearTags() {
        this.tags.clear();
        return this;
    }

    /**
     * Replaces the internal tag list with a new one.
     *
     * @param tags A list of {@link Tag} objects.
     * @return this {@link TagSet}
     */
    public TagSet<?> setTags(List<Tag<?>> tags) {
        this.tags = tags;
        return this;
    }

    /**
     * Returns a list of all registered tag sets.
     *
     * @return A copy of the global tag set list.
     */
    public static List<TagSet<?>> getAllTagSets() {
        return new ArrayList<>(tagSets);
    }

    /**
     * Retrieves a tag set by name (case-insensitive).
     *
     * @param name The name of the tag set to search for.
     * @return The matching {@link TagSet}, or {@code null} if not found.
     */
    public static TagSet<?> getTagSet(String name) {
        for (TagSet<?> t : tagSets) {
            if (t.getName().equalsIgnoreCase(name)) {
                return t;
            }
        }

        return null;
    }

    /**
     * Retrieves the {@link Inclusivity} rule for this object.
     * @return {@link #inclusivity}
     */
    public Inclusivity getInclusivity() {
        return this.inclusivity;
    }

    /**
     * Validates a list of provided tags against this set's {@link Inclusivity} rule or a provided override.
     *
     * @param providedTags The list of tags to validate.
     * @param overrideInclusivity An optional override rule; if null, this tag set’s rule is used.
     * @return {@code true} if the tag set passes the rule, {@code false} otherwise.
     */
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
