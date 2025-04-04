package com.ethanrobins.ai_npc_concept.commands;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <b>Tag Class</b> - Represents a named, configurable metadata tag that performs a transformation based on a string input.
 * <p>This abstract class implements {@link Taggable} and serves as a base for creating named command-line tags or
 * configuration keys with optional usage/help metadata. Each tag defines a {@link #use(String)} operation that
 * performs a transformation or parsing operation on the input string.</p>
 *
 * <br><b>Fields:</b>
 * <ul>
 *   <li>{@link Tag#name}</li>
 *   <li>{@link Tag#description}</li>
 *   <li>{@link Tag#usage}</li>
 * </ul>
 *
 * <br><b>Static Registry:</b>
 * <ul>
 *   <li>{@link Tag#tags} - A global synchronized list of all registered tags.</li>
 *   <li>{@link Tag#getAllTags()}</li>
 *   <li>{@link Tag#getTag(String)}</li>
 * </ul>
 *
 * <br><b>Methods:</b>
 * <ul>
 *   <li>{@link Tag#getName()}</li>
 *   <li>{@link Tag#getDescription()}</li>
 *   <li>{@link Tag#setDescription(String)}</li>
 *   <li>{@link Tag#getUsage()}</li>
 *   <li>{@link Tag#setUsage(String)}</li>
 *   <li>{@link Tag#use(String)}</li>
 * </ul>
 *
 * <b>Usage:</b>
 * <p>Extend this class to implement custom tags that apply logic or data parsing to input strings.
 * Tags are globally registered if their names are unique (case-insensitive) and can be retrieved statically
 * via {@link #getTag(String)}.</p>
 *
 * @param <T> The output type produced by this tag’s {@link #use(String)} method.
 */
public abstract class Tag<T> implements Taggable<T>, CommandPart {
    /**
     * A global, synchronized list of all unique tags registered in the system.
     */
    public static List<Tag<?>> tags = Collections.synchronizedList(new ArrayList<>());

    /**
     * The unique name of this tag.
     */
    protected String name;
    /**
     * A description of what this tag does or represents.
     */
    protected String description;
    /**
     * A string explaining the usage or expected format of this tag.
     */
    protected String usage;

    /**
     * Constructs a new {@link Tag} instance and registers it if it is unique by name (case-insensitive).
     *
     * @param name {@link #name}
     * @param description {@link #description}
     * @param usage {@link #usage}
     * @throws IllegalArgumentException if a tag with this name already exists.
     */
    public Tag(String name, String description, String usage) {
        this.name = name;
        this.description = description;
        this.usage = usage;

        if (tags.stream().noneMatch(tag -> tag.getName().equalsIgnoreCase(name))) {
            tags.add(this);
        } else {
            throw new IllegalArgumentException("Tag with name \"" + name + "\" already exists.");
        }
    }

    /**
     * Defines the logic this tag applies to a given input string.
     *
     * @param value The input string to be processed or interpreted.
     * @return A transformed or parsed value of type {@code T}.
     */
    public abstract T use(String value);

    /**
     * Retrieves the name of this tag.
     *
     * @return {@link #name}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Retrieves the description of this tag.
     *
     * @return {@link #description}
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of this tag.
     *
     * @param description {@link #description}
     * @return this {@link Tag}
     */
    @Override
    public Tag<T> setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Retrieves the usage string for this tag.
     *
     * @return {@link #usage}
     */
    @Override
    public String getUsage() {
        return usage;
    }

    /**
     * Sets the usage string for this tag.
     *
     * @param usage {@link #usage}
     * @return this {@link Tag}
     */
    @Override
    public Tag<T> setUsage(String usage) {
        this.usage = usage;
        return this;
    }

    /**
     * Retrieves an immutable list of all globally registered {@link Tag} instances.
     *
     * @return A new list containing all registered tags.
     */
    public static List<Tag<?>> getAllTags() {
        return new ArrayList<>(tags);
    }

    /**
     * Retrieves a tag by its name, ignoring case.
     *
     * @param name The name of the tag to search for.
     * @return The matching tag, or {@code null} if no match is found.
     */
    @Nullable
    public static Tag<?> getTag(String name) {
        for (Tag<?> t : tags) {
            if (t.getName().equalsIgnoreCase(name)) {
                return t;
            }
        }

        return null;
    }
}
