package com.ethanrobins.ai_npc_concept.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>CommandString</b> - Parses and stores a raw command string into structured components.
 * <p>This class is responsible for interpreting a full command input (e.g., user input or CLI) and extracting the base
 * command, its arguments, and any tag-value pairs.</p>
 *
 * <br><b>Fields:</b>
 * <ul>
 *   <li>{@link CommandString#command}</li>
 *   <li>{@link CommandString#args}</li>
 *   <li>{@link CommandString#tags}</li>
 * </ul>
 *
 * <br><b>Methods:</b>
 * <ul>
 *   <li>{@link CommandString#getCommand()}</li>
 *   <li>{@link CommandString#getArgs()}</li>
 *   <li>{@link CommandString#getTags()}</li>
 * </ul>
 *
 * @see CommandString.Tag
 * @see CommandString.TagType
 */
public class CommandString {
    /**
     * The base command name (e.g., "greet", "attack").
     */
    private final String command;
    /**
     * The arguments provided after the command but before any tag section.
     */
    private final String[] args;
    /**
     * The list of parsed {@link Tag} objects representing flags or tagged options.
     */
    private final Tag[] tags;

    /**
     * Constructs a {@link CommandString} by parsing the raw command input.
     * <p>The first word is interpreted as the command, followed by arguments and optional tags in the format:
     * <pre>{@code > mycmd arg1 arg2 --tagName "tag value" --anotherTag 25}</pre></p>
     *
     * @param fullCommand The full string input to parse.
     */
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
                    } else if (isNumeric(next)) {
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

    /**
     * Determines whether the given string can be parsed as a number.
     *
     * @param str The string to check.
     * @return {@code true} if it can be parsed as a number; otherwise {@code false}.
     */
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Determines whether the given string is a boolean value.
     *
     * @param str The string to check.
     * @return {@code true} if it is "true" or "false" (case-insensitive).
     */
    private boolean isBoolean(String str) {
        return "true".equalsIgnoreCase(str) || "false".equalsIgnoreCase(str);
    }

    /**
     * Retrieves the base command name.
     *
     * @return {@link CommandString#command}
     */
    public String getCommand() {
        return this.command;
    }

    /**
     * Retrieves the array of command arguments.
     *
     * @return {@link CommandString#args}
     */
    public String[] getArgs() {
        return this.args;
    }

    /**
     * Retrieves the array of parsed {@link Tag} objects.
     *
     * @return {@link CommandString#tags}
     */
    public Tag[] getTags() {
        return this.tags;
    }

    /**
     * <b>CommandString.Tag</b> - Represents a parsed tag from the input string.
     * <p>Tags typically follow the format {@code --name value} and may be strings, numbers, booleans, or bare flags.</p>
     *
     * <br><b>Fields:</b>
     * <ul>
     *   <li>{@link CommandString.Tag#name}</li>
     *   <li>{@link CommandString.Tag#value}</li>
     *   <li>{@link CommandString.Tag#type}</li>
     * </ul>
     *
     * <br><b>Methods:</b>
     * <ul>
     *   <li>{@link CommandString.Tag#getName()}</li>
     *   <li>{@link CommandString.Tag#getValue()}</li>
     *   <li>{@link CommandString.Tag#getType()}</li>
     * </ul>
     *
     * @see CommandString
     * @see CommandString.TagType
     */
    public static class Tag {
        /**
         * The name of the tag (e.g. "--debug", "--verbose").
         */
        private final String name;
        /**
         * The value assigned to the tag, if present (e.g. "true", "42", "hello world").
         * <p>This is {@code null} for {@link TagType#NO_VALUE} tags.</p>
         */
        private final String value;
        /**
         * The type of the tag's value.
         */
        private final TagType type;

        /**
         * Constructs a {@link Tag} with name, value, and type.
         *
         * @param tagName The tag’s name (e.g., "--silent").
         * @param tagValue The parsed value, if present. Forced to {@code null} if {@link #type} == {@link TagType#NO_VALUE}.
         * @param tagType The type of the value (STRING, NUMBER, BOOLEAN, or NO_VALUE).
         */
        public Tag(@NotNull String tagName, @Nullable String tagValue, @Nullable TagType tagType) {
            this.name = tagName;
            this.type = tagType != null ? tagType : TagType.NO_VALUE;
            this.value = this.type != TagType.NO_VALUE ? tagValue : null;
        }

        /**
         * Retrieves the tag's name.
         *
         * @return {@link #name}
         */
        public String getName() {
            return name;
        }

        /**
         * Retrieves the tag's value.
         *
         * @return {@link #value}
         */
        public String getValue() {
            return value;
        }

        /**
         * Retrieves the tag's type.
         *
         * @return {@link #type}
         */
        public TagType getType() {
            return type;
        }
    }

    /**
     * <b>CommandString.TagType</b> - Describes the data type of a parsed tag.
     * <p>Used to classify whether a tag value is a {@code STRING}, {@code NUMBER}, {@code BOOLEAN}, or {@code NO_VALUE} (bare flag).</p>
     *
     * <br><b>Constants:</b>
     * <ul>
     *   <li>{@link CommandString.TagType#STRING}</li>
     *   <li>{@link CommandString.TagType#NUMBER}</li>
     *   <li>{@link CommandString.TagType#BOOLEAN}</li>
     *   <li>{@link CommandString.TagType#NO_VALUE}</li>
     * </ul>
     */
    public enum TagType {
        /**
         * Tag value is a string enclosed in quotes.
         */
        STRING,
        /**
         * Tag value is a number (int, float, etc.).
         */
        NUMBER,
        /**
         * Tag value is a boolean literal: "true" or "false".
         */
        BOOLEAN,
        /**
         * Tag has no value (e.g., just a presence flag like "--debug").
         */
        NO_VALUE
    }
}
