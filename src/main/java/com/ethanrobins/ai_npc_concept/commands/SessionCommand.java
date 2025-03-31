package com.ethanrobins.ai_npc_concept.commands;

import com.ethanrobins.ai_npc_concept.Assistant;
import com.ethanrobins.ai_npc_concept.Main;
import com.ethanrobins.ai_npc_concept.Player;
import com.ethanrobins.ai_npc_concept.SessionData;
import com.ethanrobins.ai_npc_concept.utils.Console;

import java.util.Arrays;

/**
 * <b>SessionCommand</b> - A command for managing session-related configuration, such as switching the current assistant or player.
 * <p>This command allows switching the active session participants (assistant/player) using tags, or viewing detailed session
 * information with or without tag filters. It uses a {@link SessionTagSet} to validate command input and apply configuration.</p>
 *
 * <br><b>Fields:</b>
 * <ul>
 *     <li>{@link #sessionTagSet}</li>
 * </ul>
 *
 * <br><b>Methods:</b>
 * <ul>
 *     <li>{@link #execute(CommandString)}</li>
 *     <li>{@link #sendSessionInfo()} (private)</li>
 * </ul>
 *
 * <br><b>Usage:</b>
 * <pre>{@code
 * > session switch {tags}
 * Available tags (REQUIRED_INCLUSIVE)
 * --player "{id}"
 * --npc "{id}"
 *
 * > session info [tag]
 * Available tags (EXCLUSIVE)
 * --player ["{id}"]
 * --npc ["{id}"]
 * }</pre>
 *
 * <br><b>Command Info:</b>
 * <ul>
 *   <li>Name: {@code session}</li>
 *   <li>Description: {@code Sets environment values for test executions.}</li>
 *   <li>Usage: {@code session switch {--npc, --player}}|{@code session info [--npc | --player]}</li>
 * </ul>
 *
 * <b>Tags:</b>
 * <ul>
 *   <li>{@code --npc} - The {@link Assistant} session target.</li>
 *   <li>{@code --player} - The {@link Player} session target.</li>
 * </ul>
 *
 * @see SessionTagSet
 * @see SessionData
 */
public class SessionCommand extends Command {
    /**
     * The tag set used to select between available {@link Player} and {@link Assistant} session targets.
     */
    private final SessionTagSet sessionTagSet;

    /**
     * Constructs a new {@link SessionCommand} and initializes tag support using {@link SessionTagSet}.
     */
    public SessionCommand() {
        super("session", "Sets environment values for test executions.", "session switch {--npc, --player}\nsession info [--npc | --player]", new SessionTagSet()); // TODO: May have to make sure things like this complies with the singleton system and init SessionTagSet in the Main method.

        this.sessionTagSet = (SessionTagSet) this.getAllowedTag("session");
    }

    /**
     * Executes the session command.
     * <ul>
     *   <li>{@code session switch [--npc, --player]}: Switches the active {@link Main#currentAssistant} or {@link Main#currentPlayer} based on tags.</li>
     *   <li>{@code session info}: Prints the current session configuration.</li>
     *   <li>If an invalid argument is passed or no argument is provided, logs a warning or error message.</li>
     * </ul>
     *
     * @param input The {@link CommandString} input containing the command, arguments, and tags.
     */
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
                    Tag<?> tag = Tag.getTag(t.getName());
                    if (tag != null) {
                        Object value = tag.use(t.getValue());
                        if (value instanceof Assistant) {
                            Main.currentAssistant = (Assistant) value;
                        } else if (value instanceof Player) {
                            Main.currentPlayer = (Player) value;
                        } else {
                            throw new IllegalArgumentException("Invalid tag value for tag: " + t.getName());
                        }
                    } else {
                        throw new NullPointerException("No tag found with name: " + t.getName());
                    }
                });

                sendSessionInfo();
            } else if (Arrays.stream(input.getArgs()).toList().getFirst().equalsIgnoreCase("info")) {
                boolean isValid = sessionTagSet.validate(input.getTags(), Inclusivity.EXCLUSIVE);
                if (!isValid) {
                    throw new IllegalArgumentException("Invalid usage of tags in the " + this.name + " command.");
                }

                if (input.getTags().length == 0) {
                    sendSessionInfo();
                } else {
                    Arrays.stream(input.getTags()).toList().forEach(t -> {
                        Tag<?> tag = Tag.getTag(t.getName());
                        if (tag != null) {
                            Object value = null;
                            if (t.getValue() == null || t.getValue().isEmpty()) {
                                if (tag.getName().equalsIgnoreCase("--player")) {
                                    value = tag.use(Main.currentPlayer.getId());
                                } else if (tag.getName().equalsIgnoreCase("--npc")) {
                                    value = tag.use(Main.currentAssistant.getId());
                                }
                            } else {
                                value = tag.use(t.getValue());
                            }
                            if (value instanceof Assistant) {
                                Assistant a = (Assistant) value;

                                Console.log("Assistant \u001B[35m" + a.getName() + "\u001B[0m session info:\n" + a.getStyledData());
                            } else if (value instanceof Player) {
                                Player p = (Player) value;

                                Console.log("Player \u001B[35m" + p.getName() + "\u001B[0m session info:\n" + p.getStyledData());
                            } else {
                                throw new IllegalArgumentException("Invalid tag value for tag: " + t.getName());
                            }
                        } else {
                            throw new NullPointerException("No tag found with name: " + t.getName());
                        }
                    });
                }
            } else {
                Console.warn("Invalid command argument: " + input.getArgs()[0]);
            }
        } catch (IllegalArgumentException|NullPointerException e) {
            Console.warn(e);
        }
    }

    /**
     * Prints the current session state to the console.
     */
    private void sendSessionInfo() {
        Console.log("Session info:\n" +
                "- Player: " + Main.currentPlayer.getName() + " (" + Main.currentPlayer.getId() + ")\n" +
                "- Assistant: " + Main.currentAssistant.getName() + " (" + Main.currentAssistant.getId() + ")"
        );
    }
}
