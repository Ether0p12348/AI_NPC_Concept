package com.ethanrobins.ai_npc_concept.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <b>LogEntry Interface</b> - Represents a structured log entry in the application.
 * <p>This interface defines a contract for handling log messages that include typed categorization,
 * optional exceptions, and internal queue state management for deferred or asynchronous processing.</p>
 *
 * <br><b>Methods:</b>
 * <ul>
 *   <li>{@link LogEntry#getType()}</li>
 *   <li>{@link LogEntry#getMessage()}</li>
 *   <li>{@link LogEntry#getException()}</li>
 *   <li>{@link LogEntry#upToQueue()}</li>
 *   <li>{@link LogEntry#isUpToQueue()}</li>
 *   <li>{@link LogEntry#queue()}</li>
 * </ul>
 *
 * <b>Usage:</b>
 * <p>Implement {@code LogEntry} to represent individual log events in the system. Each entry may include
 * its type (e.g., INFO, ERROR), a descriptive message, and an optional exception object. Queue-related methods
 * are provided to flag entries for processing and prevent duplicate queueing.</p>
 *
 * @see LogEntryType
 */
public interface LogEntry {
    /**
     * Retrieves the {@link LogEntryType} associated with this log entry.
     *
     * @return The log entry's type.
     */
    @NotNull
    LogEntryType getType();

    /**
     * Retrieves the message content of this log entry.
     *
     * @return A human-readable string describing the log entry.
     */
    @NotNull
    String getMessage();

    /**
     * Retrieves the exception (if any) associated with this log entry.
     *
     * @return The attached {@link Exception}, or {@code null} if none was recorded.
     */
    @Nullable
    Exception getException();

    /**
     * Flags this log entry as ready to be sent to the queue but has not yet been queued.
     *
     * @return This {@link LogEntry} with updated queue state.
     */
    LogEntry upToQueue();

    /**
     * Checks whether this log entry is currently marked for queueing.
     *
     * @return {@code true} if the entry is up to queue; {@code false} otherwise.
     */
    boolean isUpToQueue();

    /**
     * Queues this log entry for processing.
     *
     * @return This {@link LogEntry}, now actively queued.
     */
    LogEntry queue();
}
