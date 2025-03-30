package com.ethanrobins.ai_npc_concept.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <b>Command</b> - Abstract base class for executable commands.
 * <p>This class provides a standard structure for defining user or system commands that can be executed dynamically
 * based on parsed {@link CommandString} input. It supports metadata through {@link Taggable} elements
 * and a static command registry for lookup and invocation.</p>
 *
 * <br><b>Fields:</b>
 * <ul>
 *   <li>{@link Command#name}</li>
 *   <li>{@link Command#description}</li>
 *   <li>{@link Command#usage}</li>
 *   <li>{@link Command#allowedTags}</li>
 * </ul>
 *
 * <br><b>Static:</b>
 * <ul>
 *   <li>{@link Command#commands}</li>
 *   <li>{@link Command#getCommand(String)}</li>
 * </ul>
 *
 * <br><b>Methods:</b>
 * <ul>
 *   <li>{@link Command#execute(CommandString)}</li>
 *   <li>{@link Command#getName()}</li>
 *   <li>{@link Command#getDescription()}</li>
 *   <li>{@link Command#setDescription(String)}</li>
 *   <li>{@link Command#getUsage()}</li>
 *   <li>{@link Command#setUsage(String)}</li>
 *   <li>{@link Command#getAllowedTags()}</li>
 *   <li>{@link Command#getAllowedTag(String)}</li>
 *   <li>{@link Command#addAllowedTag(Taggable[])}</li>
 *   <li>{@link Command#clearAllowedTags()}</li>
 *   <li>{@link Command#setAllowedTags(List)}</li>
 * </ul>
 *
 * <b>Usage:</b>
 * <p>Extend this class to implement specific command logic. Commands are automatically registered by name
 * into a global synchronized list and can be retrieved statically.</p>
 */
public abstract class Command {
    /**
     * A global list of all registered {@link Command} instances.
     */
    public static List<Command> commands = Collections.synchronizedList(new ArrayList<>());

    /**
     * The name of the command (e.g. "help", "greet").
     */
    protected String name;
    /**
     * A short description of what this command does.
     */
    protected String description;
    /**
     * A formatted usage string showing syntax or options.
     */
    protected String usage;
    /**
     * A list of tags that are valid for this command.
     */
    protected List<Taggable<?>> allowedTags = new ArrayList<>();

    /**
     * Constructs a new {@link Command} and registers it if not already present.
     *
     * @param name The name of the command.
     * @param description A description of what it does (optional).
     * @param usage A usage/help string (optional).
     * @param allowedTags Tags that this command accepts (optional).
     */
    public Command(@NotNull String name, @Nullable String description, @Nullable String usage, Taggable<?>... allowedTags) {
        this.name = name;
        this.description = description != null ? description : "";
        for (Taggable<?> t : allowedTags) {
            if (t != null) {
                this.allowedTags.add(t);
            }
        }
        this.usage = usage != null ? usage : "";

        if (commands.stream().noneMatch(command -> command.getName().equalsIgnoreCase(name))) {
            commands.add(this);
        }
    }

    /**
     * Executes the command using the provided {@link CommandString} input.
     *
     * @param input The parsed input containing the command, arguments, and tags.
     */
    public abstract void execute(CommandString input);

    /**
     * Retrieves the name of this command.
     *
     * @return {@link #name}
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the command’s description.
     *
     * @return {@link #description}
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the command’s description.
     *
     * @param description {@link #description}
     * @return this {@link Command}
     */
    public Command setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Retrieves the usage string for this command.
     *
     * @return {@link #usage}
     */
    public String getUsage() {
        return usage;
    }

    /**
     * Sets the usage/help string for this command.
     *
     * @param usage {@link #usage}
     * @return this {@link Command}
     */
    public Command setUsage(String usage) {
        this.usage = usage;
        return this;
    }

    /**
     * Retrieves all tags that are allowed for this command.
     *
     * @return {@link #allowedTags}
     */
    public List<Taggable<?>> getAllowedTags() {
        return new ArrayList<>(allowedTags);
    }

    /**
     * Gets a specific tag by name (case-insensitive).
     *
     * @param name The tag name to search for.
     * @return The matching tag, or {@code null} if not found.
     */
    @Nullable
    public Taggable<?> getAllowedTag(String name) {
        for (Taggable<?> t : allowedTags) {
            if (t != null && t.getName().equalsIgnoreCase(name)) {
                return t;
            }
        }
        return null;
    }

    /**
     * Adds one or more tags to the allowed tags list.
     *
     * @param tag One or more {@link Taggable} instances.
     * @return this {@link Command}
     */
    public Command addAllowedTag(Taggable<?>... tag) {
        for (Taggable<?> t : allowedTags) {
            if (t != null && !this.allowedTags.contains(t)) {
                this.allowedTags.add(t);
            }
        }
        return this;
    }

    /**
     * Clears all allowed tags from this command.
     *
     * @return this {@link Command}
     */
    public Command clearAllowedTags() {
        allowedTags.clear();
        return this;
    }

    /**
     * Replaces the allowed tags list with a new one.
     *
     * @param tags The new list of {@link Taggable} elements.
     * @return this {@link Command}
     */
    public Command setAllowedTags(List<Taggable<?>> tags) {
        allowedTags = tags;
        return this;
    }

    /**
     * Retrieves a registered {@link Command} by name (case-insensitive).
     *
     * @param name The name of the command.
     * @return The matching command or {@code null} if not found.
     */
    @Nullable
    public static Command getCommand(String name) {
        for (Command command : commands) {
            if (command.getName().equalsIgnoreCase(name)) {
                return command;
            }
        }

        return null;
    }
}
