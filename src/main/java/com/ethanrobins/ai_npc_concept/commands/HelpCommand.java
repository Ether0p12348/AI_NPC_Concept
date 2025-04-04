package com.ethanrobins.ai_npc_concept.commands;

import com.ethanrobins.ai_npc_concept.utils.Color;
import com.ethanrobins.ai_npc_concept.utils.Console;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <b>HelpCommand</b> - Displays usage information for all available commands or a specific command.
 * <p>This command provides dynamic help output. When called with no arguments, it lists all available
 * commands with their descriptions. When called with a specific command name, it displays detailed help for that command.</p>
 *
 * <br><b>Methods:</b>
 * <ul>
 *     <li>{@link #execute(CommandString)}</li>
 * </ul>
 *
 * <br><b>Usage:</b>
 * <pre>{@code > help [command_name|tagset_name|tag_name]}</pre>
 *
 * <br><b>Command Info:</b>
 * <ul>
 *   <li>Name: {@code help}</li>
 *   <li>Description: {@code Displays CLI information.}</li>
 *   <li>Usage: {@code help [command_name|tagset_name|tag_name]}</li>
 * </ul>
 */
public class HelpCommand extends Command {

    /**
     * Constructs a {@link HelpCommand} with default name, description, and usage.
     */
    public HelpCommand() {
        super("help", "Displays CLI information.", "help [command_name|tagset_name|tag_name]");
    }

    /**
     * Executes the help logic based on provided arguments.
     * <ul>
     *   <li>If no arguments are provided, all available commands are listed with descriptions.</li>
     *   <li>If a command, tagset, or tag name is specified, detailed help is shown for that item.</li>
     *   <li>If the specified command, tagset, or tag does not exist, a warning is shown.</li>
     * </ul>
     *
     * @param input The parsed {@link CommandString} representing user input.
     */
    @Override
    public void execute(CommandString input) {
        // TODO: UNFINISHED - FIX THE REDUNDANCIES. THIS METHOD IS NASTY.
        /*
        > help         - Full list of commands, tag sets, tags
        > help command - command information, includes allowedTag(Sets)
        > help -tagset - tagset information, includes tags
        > help --tag   - tag information
         */
        List<String> args = Arrays.stream(input.getArgs()).toList();
        List<Command> commands = Command.commands;
        commands.sort(Comparator.comparing(Command::getName));
        if (args.isEmpty()) {
            Console.log(commands.stream().map(c -> c.getName() + " - " + c.getDescription()).collect(Collectors.joining("\n")));
        } else {
            if (!args.getFirst().isEmpty()) {
                boolean found = false;

                if (args.getFirst().startsWith("-") && !args.getFirst().startsWith("--")) {
                    for (TagSet<?> set : TagSet.getAllTagSets()) {
                        if (set.getName().equalsIgnoreCase(args.getFirst())) {
                            found = true;

                            StringBuilder sb = new StringBuilder();
                        }
                    }
                } else if (args.getFirst().startsWith("--")) {

                } else {
                    for (Command c : commands) {
                        if (c.getName().equalsIgnoreCase(args.getFirst())) {
                            found = true;

                            StringBuilder sb = new StringBuilder();
                            if (!c.getAllowedTags().isEmpty()) {
                                sb.append(Color.colorize("Tags: ", Color.BLUE.fg()));

                                List<Taggable<?>> allowedTags = c.getAllowedTags().stream().sorted(Comparator.comparingInt(tag -> {
                                    if (tag instanceof TagSet) return 0;
                                    if (tag instanceof Tag) return 1;
                                    return 2;
                                })).toList();
                                StringBuilder tagSb = new StringBuilder();
                                List<String> usedTags = new ArrayList<>();
                                for (Taggable<?> t : allowedTags) {
                                    if (t instanceof TagSet<?> set) {
                                        StringBuilder tagSb1 = new StringBuilder();
                                        tagSb1.append("\n");
                                        for (Tag<?> tt : set.getTags()) {
                                            tagSb1.append(indentMultiline(set.getName() + ": ", tt.getName() + "\n"));
                                            usedTags.add(tt.getName());
                                        }
                                        tagSb.append(Color.colorize(set.getName() + ": [" + set.getInclusivity().name() + "]", Color.YELLOW.fg()));
                                        tagSb.append(indentMultiline(set.getName() + ": ", tagSb1.toString()));
                                    } else if (t instanceof Tag<?> tag) {
                                        if (!usedTags.contains(tag.getName())) {
                                            tagSb.append(tag.getName());
                                            usedTags.add(tag.getName());
                                        }
                                    }
                                }
                                sb.append(indentMultiline("Tags: ", tagSb.toString()));
                            }

                            Console.log(
                                    Color.colorize("Help for '" + c.getName() + "':", Color.BRIGHT_WHITE.fg()) + "\n" +
                                            Color.colorize("Description: ", Color.BLUE.fg()) + c.getDescription() + "\n" +
                                            Color.colorize("Usage: ", Color.BLUE.fg()) + indentMultiline("Usage: ", c.getUsage()) + "\n" +
                                            sb
                            );
                        }
                    }
                }

                if (!found) {
                    Console.warn(args.getFirst() + " is not a valid command.");
                }
            }
        }
    }

    private static String indentMultiline(String indent, String message) {
        String[] lines = message.split("\n");
        if (lines.length <= 1) return message;

        StringBuilder builder = new StringBuilder(lines[0]);
        String padding = " ".repeat(Console.removeAnsiCodes(indent).length());

        for (int i = 1; i < lines.length; i++) {
            builder.append("\n").append(padding).append(lines[i]);
        }

        return builder.toString();
    }
}
