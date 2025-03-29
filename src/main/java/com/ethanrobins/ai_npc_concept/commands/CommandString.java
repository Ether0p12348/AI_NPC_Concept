package com.ethanrobins.ai_npc_concept.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandString {
    private final String command;
    private final String[] args;
    private final Tag[] tags;

    public CommandString(String fullCommand) {
        String[] split = fullCommand.split(" ");

        this.command = split[0];

        List<String> argsList = new ArrayList<>();
        List<Tag> tagsList = new ArrayList<>();

        boolean isParsingTags = false;
        for (int i = 1; i < split.length; i++) {
            String curr = split[i];

            if (curr.startsWith("--")) {
                isParsingTags = true;
                String tagName = curr;
                String tagValue = null;
                TagType tagType = TagType.NO_VALUE;

                if (i + 1 < split.length) {
                    String next = split[i + 1];

                    if (next.startsWith("\"") || next.startsWith("'")) {
                        StringBuilder stringValue = new StringBuilder(next);

                        while ((!stringValue.toString().endsWith("\"") && !stringValue.toString().endsWith("'")) && i + 1 < split.length) {
                            i++;

                            stringValue.append(" ").append(split[i]);
                        }

                        tagValue = stringValue.substring(1, stringValue.length() - 1);
                        tagType = TagType.STRING;
                    } else if (isNumberic(next)) {
                        tagValue = next;
                        tagType = TagType.NUMBER;
                        i++;
                    } else if (isBoolean(next)) {
                        tagValue = next;
                        tagType = TagType.BOOLEAN;
                    }
                }

                tagsList.add(new Tag(tagName, tagValue, tagType));
            } else if (!isParsingTags) {
                argsList.add(curr);
            }
        }

        this.args = argsList.toArray(new String[0]);
        this.tags = tagsList.toArray(new Tag[0]);
    }

    private boolean isNumberic(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isBoolean(String str) {
        return "true".equalsIgnoreCase(str) || "false".equalsIgnoreCase(str);
    }

    public String getCommand() {
        return this.command;
    }

    public String[] getArgs() {
        return this.args;
    }

    public Tag[] getTags() {
        return this.tags;
    }

    public static class Tag {
        private final String name;
        private final String value;
        private final TagType type;

        public Tag(@NotNull String tagName, @Nullable String tagValue, @Nullable TagType tagType) {
            this.name = tagName;
            this.value = tagValue;
            this.type = tagType != null ? tagType : TagType.NO_VALUE;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        public TagType getType() {
            return type;
        }
    }

    public enum TagType {
        STRING,
        NUMBER,
        BOOLEAN,
        NO_VALUE
    }
}
