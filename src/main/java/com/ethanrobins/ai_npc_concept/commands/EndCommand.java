package com.ethanrobins.ai_npc_concept.commands;

import com.ethanrobins.ai_npc_concept.Main;

public class EndCommand extends Command{
    public EndCommand() {
        super("end", "Gracefully ends the program.", "end");
    }

    @Override
    public void execute(CommandString input) {
        Main.onExit();
    }
}
