package com.ethanrobins.ai_npc_concept.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Command {
    public static List<Command> commands = Collections.synchronizedList(new ArrayList<>());

    protected String name;
    protected String description;
    protected List<Taggable<?>> allowedTags = new ArrayList<>();
    protected String usage;

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

    public abstract void execute(CommandString input);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Command setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUsage() {
        return usage;
    }

    public Command setUsage(String usage) {
        this.usage = usage;
        return this;
    }

    public List<Taggable<?>> getAllowedTags() {
        return new ArrayList<>(allowedTags);
    }

    @Nullable
    public Taggable<?> getAllowedTag(String name) {
        for (Taggable<?> t : allowedTags) {
            if (t != null && t.getName().equalsIgnoreCase(name)) {
                return t;
            }
        }
        return null;
    }

    public Command addAllowedTag(Taggable<?>... tag) {
        for (Taggable<?> t : allowedTags) {
            if (t != null && !this.allowedTags.contains(t)) {
                this.allowedTags.add(t);
            }
        }
        return this;
    }

    public Command clearAllowedTags() {
        allowedTags.clear();
        return this;
    }

    public Command setAllowedTags(List<Taggable<?>> tags) {
        allowedTags = tags;
        return this;
    }

    public static Command getCommand(String name) {
        for (Command command : commands) {
            if (command.getName().equalsIgnoreCase(name)) {
                return command;
            }
        }

        return null;
    }
}
