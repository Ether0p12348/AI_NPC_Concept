package com.ethanrobins.ai_npc_concept.utils;

import java.util.List;

public class CommandString {
    private final String command;
    private final String[] args;

    public CommandString(String command, String[] args) {
        this.command = command;
        this.args = args;
    }

    public CommandString(String command, List<String> args) {
        this.command = command;
        this.args = args.toArray(new String[0]);
    }

    public CommandString(String fullCommand) {
        String[] split = fullCommand.split(" ");
        this.command = split[0];
        this.args = new String[split.length - 1];
        System.arraycopy(split, 1, this.args, 0, this.args.length);
    }

    public String getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }
}
