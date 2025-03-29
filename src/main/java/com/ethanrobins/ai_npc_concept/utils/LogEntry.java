package com.ethanrobins.ai_npc_concept.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface LogEntry {
    @NotNull
    LogEntryType getType();
    @NotNull
    String getMessage();
    @Nullable
    Exception getException();
    LogEntry upToQueue();
    boolean isUpToQueue();
    LogEntry queue();
}
