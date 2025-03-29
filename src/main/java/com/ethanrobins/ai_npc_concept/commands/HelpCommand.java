package com.ethanrobins.ai_npc_concept.commands;

import com.ethanrobins.ai_npc_concept.utils.Console;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help", "Displays available commands.", "help [command_name]");
    }

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
