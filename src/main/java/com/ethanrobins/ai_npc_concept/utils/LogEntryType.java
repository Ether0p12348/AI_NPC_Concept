package com.ethanrobins.ai_npc_concept.utils;

public enum LogEntryType {
    PROGRESS("\u001B[35mPROGRESS\u001B[0m"),
    INFO("INFO"),
    DEBUG("\u001B[33mDEBUG\u001B[0m"),
    WARNING("\u001B[33mWARNING\u001B[0m"),
    ERROR("\u001B[31mERROR\u001B[0m");

    private final String display;

    LogEntryType(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }
}
