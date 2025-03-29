package com.ethanrobins.ai_npc_concept.commands;

import com.ethanrobins.ai_npc_concept.Assistant;
import com.ethanrobins.ai_npc_concept.Main;
import com.ethanrobins.ai_npc_concept.Player;
import com.ethanrobins.ai_npc_concept.utils.Console;

import java.util.Arrays;

public class SessionCommand extends Command {
    private final SessionTagSet sessionTagSet;

    public SessionCommand() {
        super("session", "Sets environment values for test executions.", "session switch [--npc, --player]\nsession info", new SessionTagSet()); // TODO: May have to make sure things like this complies with the singleton system and init SessionTagSet in the Main method.

        this.sessionTagSet = (SessionTagSet) this.getAllowedTag("session");
    }

    @Override
    public void execute(CommandString input) {
        try {
            if (input.getArgs().length == 0) {
                throw new IllegalArgumentException("No arguments provided for the " + this.name + " command.");
            }
            if (Arrays.stream(input.getArgs()).toList().getFirst().equalsIgnoreCase("switch")) {
                boolean isValid = sessionTagSet.validate(input.getTags(), Inclusivity.REQUIRED_INCLUSIVE);

                if (!isValid) {
                    throw new IllegalArgumentException("Invalid usage of tags in the " + this.name + " command.");
                }

                Arrays.stream(input.getTags()).toList().forEach(t -> {
                    try {
                        Tag<?> tag = Tag.getTag(t.getName());
                        if (tag != null) {
                            Object value = tag.use(t.getValue());
                            if (value instanceof Assistant) {
                                Main.currentAssistant = (Assistant) value;
                            } else if (value instanceof Player) {
                                Main.currentPlayer = (Player) value;
                            }
                        } else {
                            throw new NullPointerException("No tag found with name: " + t.getName());
                        }
                    } catch (NullPointerException e) {
                        Console.warn(e);
                    }
                });

                sendSessionInfo();
            } else if (Arrays.stream(input.getArgs()).toList().getFirst().equalsIgnoreCase("info")) {
                sendSessionInfo();
            } else {
                Console.warn("Invalid command argument: " + input.getArgs()[0]);
            }
        } catch (IllegalArgumentException e) {
            Console.error(e);
        }
    }

    private void sendSessionInfo() {
        Console.log("Session info:\n" +
                "- Player: " + Main.currentPlayer.getName() + " (" + Main.currentPlayer.getId() + ")\n" +
                "- Assistant: " + Main.currentAssistant.getName() + " (" + Main.currentAssistant.getId() + ")"
        );
    }
}
