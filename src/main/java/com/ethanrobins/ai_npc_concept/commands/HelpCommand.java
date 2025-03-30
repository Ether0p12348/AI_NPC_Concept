package com.ethanrobins.ai_npc_concept.commands;

import com.ethanrobins.ai_npc_concept.utils.Console;

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
        List<String> args = Arrays.stream(input.getArgs()).toList();
        List<Command> commands = Command.commands;
        commands.sort(Comparator.comparing(Command::getName));
        if (args.isEmpty()) {
            Console.log(commands.stream().map(c -> c.getName() + " - " + c.getDescription()).collect(Collectors.joining("\n")));
        } else {
            if (!args.getFirst().isEmpty()) {
                boolean found = false;

                for (Command c : commands) {
                    if (c.getName().equalsIgnoreCase(args.getFirst())) {
                        found = true;

//                        String allowedTagsStr = "";
//                        for (Tag t : c.getAllowedTags()) {
//                            allowedTagsStr += " --" + t.name();
//                        }

                        Console.log(
                                "Help for '" + c.getName() + "':\n" +
                                "Description: " + c.getDescription() + "\n" +
                                "Usage: " + c.getUsage() + "\n"
                                //"Allowed Tags: " +  + "\n"
                        );
                    }
                }

                if (!found) {
                    Console.warn(args.getFirst() + " is not a valid command.");
                }
            }
        }
    }
}
