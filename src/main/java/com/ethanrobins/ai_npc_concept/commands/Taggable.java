package com.ethanrobins.ai_npc_concept.commands;

/**
 * <b>Taggable Interface</b> - Represents a metadata-capable object that can be labeled with a name, description, and usage.
 * <p>Implement this interface for any object (e.g., commands, definitions, features) that should be annotated with
 * human-readable tags and descriptions for documentation, introspection, or help systems.</p>
 *
 * <br><b>Methods:</b>
 * <ul>
 *   <li>{@link Taggable#getName()}</li>
 *   <li>{@link Taggable#getDescription()}</li>
 *   <li>{@link Taggable#setDescription(String)}</li>
 *   <li>{@link Taggable#getUsage()}</li>
 *   <li>{@link Taggable#setUsage(String)}</li>
 * </ul>
 *
 * <b>Usage:</b>
 * <p>This interface is intended to provide consistent access to metadata properties across tagged components.
 * Ideal for building CLI tools, registries, or reflective systems that rely on user-facing identifiers.</p>
 *
 * @param <T> The implementing type, used to support method chaining with fluent-style setters.
 */
public interface Taggable<T> {
    /**
     * Retrieves the name associated with this taggable item.
     *
     * @return A unique name or identifier string.
     */
    String getName();

    /**
     * Retrieves the description explaining the purpose or behavior of this taggable item.
     *
     * @return A human-readable description.
     */
    String getDescription();

    /**
     * Sets or updates the description for this taggable item.
     *
     * @param description The new description to assign.
     * @return this {@link Taggable} instance (for chaining).
     */
    Taggable<T> setDescription(String description);

    /**
     * Retrieves usage instructions or format for how this taggable item should be used.
     *
     * @return A usage string (e.g., CLI syntax or command format).
     */
    String getUsage();

    /**
     * Sets or updates the usage string for this taggable item.
     *
     * @param usage The usage format to assign.
     * @return this {@link Taggable} instance (for chaining).
     */
    Taggable<T> setUsage(String usage);
}
