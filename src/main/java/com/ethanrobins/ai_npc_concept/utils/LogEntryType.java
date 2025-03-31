package com.ethanrobins.ai_npc_concept.utils;

/**
 * <b>LogEntryType Enum</b> - Represents different types of log entries with customizable display formats.
 * <p>This enum is used to categorize log messages and optionally apply color-coded formatting for terminal output,
 * making it easier to distinguish between log types such as progress, information, warnings, and errors.</p>
 *
 * <br><b>Enum Constants:</b>
 * <ul>
 *   <li>{@link LogEntryType#PROGRESS}</li>
 *   <li>{@link LogEntryType#INFO}</li>
 *   <li>{@link LogEntryType#DEBUG}</li>
 *   <li>{@link LogEntryType#WARNING}</li>
 *   <li>{@link LogEntryType#ERROR}</li>
 * </ul>
 *
 * <br><b>Fields:</b>
 * <ul>
 *   <li>{@link LogEntryType#display}</li>
 * </ul>
 *
 * <br><b>Methods:</b>
 * <ul>
 *   <li>{@link LogEntryType#getDisplay()}</li>
 * </ul>
 *
 * <b>Usage:</b>
 * <p>Use {@code LogEntryType} to semantically classify messages in the log system and render them with appropriate
 * styling for improved readability in development or operational environments.</p>
 */
public enum LogEntryType {
    /**
     * Used to indicate ongoing or completed progress-related information.
     */
    PROGRESS(Color.colorize("PROGRESS", Color.BRIGHT_PURPLE.fg())),
    /**
     * Standard informational message.
     */
    INFO(Color.colorize("INFO", Color.BRIGHT_WHITE.fg())),
    /**
     * Detailed diagnostic output, primarily for developers.
     */
    DEBUG(Color.colorize("DEBUG", Color.YELLOW.fg())),
    /**
     * Represents a non-critical warning or caution.
     */
    WARNING(Color.colorize("WARNING", Color.BRIGHT_YELLOW.fg())),
    /**
     * Critical error or failure state message.
     */
    ERROR(Color.colorize("ERROR", Color.BRIGHT_RED.fg()));

    /**
     * The display string used when rendering this log type.
     * May include ANSI color codes for console styling.
     */
    private final String display;

    /**
     * Constructs an {@link LogEntryType} enum with a display string.
     *
     * @param display {@link LogEntryType#display}
     * @see LogEntryType
     */
    LogEntryType(String display) {
        this.display = display;
    }

    /**
     * Retrieves the formatted display name for this log entry type.
     *
     * @return {@link LogEntryType#display}
     */
    public String getDisplay() {
        return display;
    }
}
