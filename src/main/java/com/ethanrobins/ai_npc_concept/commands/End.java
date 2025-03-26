package com.ethanrobins.ai_npc_concept.commands;

import com.ethanrobins.ai_npc_concept.Main;
import com.ethanrobins.ai_npc_concept.utils.CommandString;

public class End extends Command{
    public End() {
        super("end", "Gracefully ends the program.", "end");
    }

    @Override
    public void execute(CommandString input) {
        Main.onExit();
    }
}
