package com.ethanrobins.ai_npc_concept.commands;

import com.ethanrobins.ai_npc_concept.Main;

/**
 * <b>EndCommand</b> - A command that gracefully shuts down the program.
 * <p>This command delegates to {@link Main#onExit()} and is typically used for user-initiated or scripted shutdown
 * of the system.</p>
 *
 * <br><b>Methods:</b>
 * <ul>
 *     <li>{@link #execute(CommandString)}</li>
 * </ul>
 *
 * <br><b>Usage:</b>
 * <pre>{@code > end}</pre>
 *
 * <br><b>Command Info:</b>
 * <ul>
 *   <li>Name: {@code end}</li>
 *   <li>Description: {@code Gracefully ends the program.}</li>
 *   <li>Usage: {@code end}</li>
 * </ul>
 */
public class EndCommand extends Command{

    /**
     * Constructs a new {@link EndCommand} with predefined name, description, and usage.
     */
    public EndCommand() {
        super("end", "Gracefully ends the program.", "end");
    }

    /**
     * Executes the shutdown procedure by calling {@link Main#onExit()}.
     *
     * @param input The full parsed {@link CommandString} input (unused in this implementation).
     */
    @Override
    public void execute(CommandString input) {
        Main.onExit();
    }
}
