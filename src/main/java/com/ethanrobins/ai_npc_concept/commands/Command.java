package com.ethanrobins.ai_npc_concept.commands;

import com.ethanrobins.ai_npc_concept.utils.CommandString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {
    public static List<Command> commands = new ArrayList<>();

    protected String name;
    protected String description;
    protected List<Tag> allowedTags = new ArrayList<>();
    protected String usage;

    public Command(@NotNull String name, @Nullable String description, @Nullable String usage, Tag... allowedTags) {
        this.name = name;
        this.description = description != null ? description : "";
        this.allowedTags.addAll(List.of(allowedTags));
        this.usage = usage != null ? usage : "";
        commands.add(this);
    }

    public Command(@NotNull String name, @Nullable String description, @Nullable String usage) {
        this.name = name;
        this.description = description != null ? description : "";
        this.usage = usage != null ? usage : "";
        commands.add(this);
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

    public Tag[] getAllowedTags() {
        return allowedTags.toArray(new Tag[0]);
    }

    public Command addAllowedTag(Tag... tag) {
        allowedTags.addAll(List.of(tag));
        return this;
    }

    public Command clearAllowedTags() {
        allowedTags.clear();
        return this;
    }

    public Command setAllowedTags(List<Tag> tags) {
        allowedTags = tags;
        return this;
    }

    public String getUsage() {
        return usage;
    }

    public Command setUsage(String usage) {
        this.usage = usage;
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
