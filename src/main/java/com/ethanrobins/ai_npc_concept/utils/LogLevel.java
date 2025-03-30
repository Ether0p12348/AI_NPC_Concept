package com.ethanrobins.ai_npc_concept.utils;

/**
 * <b>LogLevel Enum</b> - Represents the logging levels available in the application.
 * <p>This enum defines the granularity of logs produced during runtime, allowing developers to control
 * the verbosity of output based on the context (e.g., production or development).</p>
 *
 * <br><b>Enum Constants:</b>
 * <ul>
 *   <li>{@link LogLevel#INFO}</li>
 *   <li>{@link LogLevel#DEBUG}</li>
 * </ul>
 *
 * <b>Usage:</b>
 * <p>Use {@code LogLevel} to determine the logging behavior throughout the application. For instance,
 * {@link LogLevel#INFO} may be used for cleaner logs in production environments, while {@link LogLevel#DEBUG}
 * can reveal internal states during development or testing.</p>
 */
public enum LogLevel {
    /**
     * Informational log level.
     * <p>Used for general-purpose messages such as lifecycle events, progress updates, and user-level notices.</p>
     */
    INFO,
    /**
     * Debug log level.
     * <p>Used to log detailed diagnostic information, internal state, and developer-centric messages
     * during development or troubleshooting.</p>
     */
    DEBUG
}
