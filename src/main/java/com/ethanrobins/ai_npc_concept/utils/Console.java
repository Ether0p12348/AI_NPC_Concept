package com.ethanrobins.ai_npc_concept.utils;

import com.ethanrobins.ai_npc_concept.commands.Command;
import com.ethanrobins.ai_npc_concept.commands.CommandString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Pattern;

/**
 * <b>Console</b> - A utility class for handling logging, command-line interactions, and background processing.
 * <p>The {@code Console} class provides methods for logging messages, handling warnings and errors, managing
 * progress updates, and running a command-line interface (CLI). It uses background threads for efficient
 * log processing and interactive CLI handling.</p>
 *
 * <br><b>Features:</b>
 * <ul>
 *   <li>Supports multiple log levels ({@code INFO}, {@code DEBUG}, etc.) via {@link LogLevel}.</li>
 *   <li>Processes logs asynchronously using a {@link BlockingQueue} for queued log entries.</li>
 *   <li>Allows users to start a CLI loop for handling commands with {@link #startCli()}.</li>
 *   <li>Provides methods to log messages with specific types, including {@code warn}, {@code error},
 *       {@code debug}, and {@code progress} tracking.</li>
 * </ul>
 *
 * <br><b>Static Fields:</b>
 * <ul>
 *   <li>{@link #logLevel}</li>
 *   <li>{@link #FORMAT}</li>
 *   <li>{@link #logQueue}</li>
 * </ul>
 *
 * <br><b>Static Methods:</b>
 * <ul>
 *   <li>{@link #startCli()}</li>
 *   <li>{@link #log(String)}</li>
 *   <li>{@link #warn(String)}</li>
 *   <li>{@link #warn(Exception)}</li>
 *   <li>{@link #error(Exception)}</li>
 *   <li>{@link #debug(String)}</li>
 *   <li>{@link #progress(String, int)}</li>
 * </ul>
 *
 * <b>Usage:</b>
 * <p>This class provides a centralized console utility for logging and progress tracking. Use different logging
 * methods to categorize log entries, or invoke {@code startCli()} to activate an interactive command-line interface.</p>
 *
 * @see LogEntryObject
 * @see Progress
 */
public class Console {
    /**
     * Controls the current logging verbosity level (e.g., INFO, DEBUG).
     */
    public static volatile LogLevel logLevel = LogLevel.INFO;
    /**
     * Format template used for rendering log messages.
     * Placeholders: %timestamp, %type, %message
     */
    public static final String FORMAT = "[%timestamp] [%type] -> %message";

    /**
     * Flag indicating whether the CLI is currently running.
     */
    private static volatile boolean cliRunning = false;
    /**
     * Thread responsible for handling the CLI input loop.
     */
    private static volatile Thread cliThread = null;
    /**
     * Scanner used to read user input from standard input during CLI runtime.
     */
    private static volatile Scanner scanner = null;
    /**
     * Prompt string displayed during CLI input.
     */
    private static final String PROMPT = "> ";

    /**
     * Internal queue for log entries to be processed asynchronously.
     */
    private static final BlockingQueue<LogEntry> logQueue = new LinkedBlockingQueue<>();
    /**
     * Indicates whether the background logging thread should continue running.
     */
    private static volatile boolean queueRunning = true;
    /**
     * Indicates whether the logging queue is temporarily paused.
     */
    private static volatile boolean queuePaused = false;
    /**
     * Background thread responsible for processing log entries from the queue.
     */
    private static final Thread logThread;

    // Initialize and start the background logging thread.
    // This thread continuously polls from the log queue and processes entries.
    // If the queue is paused, it waits until it is resumed.
    // If the thread is interrupted, the error is logged and the loop exits.
    static {
        logThread = new Thread(() -> {
            while (queueRunning) {
                try {
                    if (!Console.queuePaused) {
                        LogEntry e = logQueue.take();
                        e.upToQueue();
                    } else {
                        synchronized (Thread.currentThread()) {
                            while (Console.queuePaused) {
                                Thread.currentThread().wait();
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    Console.error(e);
                    break;
                }
            }
        });

        logThread.start();
    }

    /**
     * Starts the interactive command-line interface (CLI) on a new thread.
     * <p>Supports built-in commands such as {@code end}, {@code help}, and {@code session}.</p>
     *
     * @throws IllegalStateException If the CLI fails to start or a command cannot be executed.
     */
    public static void startCli() throws IllegalStateException {
        Progress cliStartProgress = Console.progress("Starting CLI", 1);
        cliRunning = true;

        cliThread = new Thread(() -> {
            scanner = new Scanner(System.in);
            while (cliRunning) {
                String input = scanner.nextLine();

                CommandString commandString = new CommandString(input);

                try {
                    switch (commandString.getCommand()) {
                        case "end":
                            cliRunning = false;
                            scanner.close();
                            Objects.requireNonNull(Command.getCommand("end")).execute(commandString);
                            queueRunning = false;
                            break;
                        case "help":
                            Objects.requireNonNull(Command.getCommand("help")).execute(commandString);
                            break;
                        case "session":
                            Objects.requireNonNull(Command.getCommand("session")).execute(commandString);
                            break;
                        default:
                            warn(new Exception("Command not implemented: " + commandString.getCommand()));
                            break;
                    }
                } catch (NullPointerException e) {
                    Console.error(e);
                }
            }
        });

        try {
            cliThread.start();
            cliStartProgress.step();
        } catch (Exception e) {
            cliStartProgress.error(e);
        }
    }

    /**
     * Queues a basic informational log.
     *
     * @param message The message to be logged as {@link LogEntryType#INFO}.
     * @throws NullPointerException If the message is {@code null}.
     */
    public static void log(@NotNull String message) {
        new LogEntryObject(LogEntryType.INFO, message).queue();
    }

    /**
     * Sends a formatted {@link LogEntryObject} with type {@link LogEntryType#INFO} to the console output.
     *
     * @param entry The log entry to display.
     */
    private static void sendLog(LogEntryObject entry) {
        System.out.print("\r\033[2K");
        System.out.println(getFormattedMessage(entry.getMessage(), entry.getType().getDisplay()));

        if (cliRunning) {
            System.out.print(PROMPT);
        }
    }

    /**
     * Queues a warning log.
     *
     * @param message The message to be logged as {@link LogEntryType#WARNING}.
     * @throws NullPointerException If the message is {@code null}.
     */
    public static void warn(@NotNull String message) {
        new LogEntryObject(LogEntryType.WARNING, message).queue();
    }

    /**
     * Queues a warning log from an {@link Exception}.
     *
     * @param exception The exception to be logged as {@link LogEntryType#WARNING}.
     * @throws NullPointerException If the message is {@code null}.
     */
    public static void warn(@NotNull Exception exception) {
        new LogEntryObject(LogEntryType.WARNING, exception).queue();
    }

    /**
     * Sends a warning-formatted {@link LogEntryObject} to the console output.
     *
     * @param entry The warning log entry to display.
     */
    private static void sendWarn(LogEntryObject entry) {
        System.out.print("\r\033[2K");
        System.out.println(getFormattedMessage(Color.colorize(entry.getMessage(), Color.YELLOW.fg()), entry.getType().getDisplay()));

        if (cliRunning) {
            System.out.print(PROMPT);
        }
    }

    /**
     * Queues an error log from an {@link Exception}.
     *
     * @param exception The exception to log with a {@link LogEntryType#ERROR} tag.
     * @throws NullPointerException If the message is {@code null}.
     */
    public static void error(@NotNull Exception exception) {
        new LogEntryObject(LogEntryType.ERROR, exception).queue();
    }

    /**
     * Sends an error-formatted {@link LogEntryObject} to the console output and prints stack trace if available.
     *
     * @param entry The error log entry to display.
     */
    private static void sendError(LogEntryObject entry) {
        System.out.print("\r\033[2K");
        System.out.println(getFormattedMessage(Color.colorize(entry.getMessage(), Color.RED.fg()), entry.getType().getDisplay()));
        if (entry.getException() != null) {
            entry.getException().printStackTrace();
        }

        if (cliRunning) {
            System.out.print(PROMPT);
        }
    }

    /**
     * Queues a debug log.
     *
     * @param message The debug message to be logged.
     * @throws NullPointerException If the message is {@code null}.
     */
    public static void debug(@NotNull String message) {
        new LogEntryObject(LogEntryType.DEBUG, message).queue();
    }

    /**
     * Sends a debug-formatted {@link LogEntryObject} to the console output, if debug mode is enabled.
     *
     * @param entry The debug log entry to display.
     */
    private static void sendDebug(LogEntryObject entry) {
        if (logLevel == LogLevel.DEBUG) {
            System.out.print("\r\033[2K");
            System.out.println(getFormattedMessage(Color.colorize(entry.getMessage(), Color.YELLOW.fg()), entry.getType().getDisplay()));

            if (cliRunning) {
                System.out.print(PROMPT);
            }
        }
    }

    /**
     * Queues a progress log.
     *
     * @param message The progress message to be logged.
     * @param numSteps Number of total steps to track.
     * @return A {@link Console.Progress} instance.
     */
    public static Progress progress(String message, int numSteps) {
        return new Progress(message, numSteps).queue();
    }


    /**
     * Builds a timestamped and type-tagged log message using the standard {@link #FORMAT}.
     *
     * @param input The message content to format.
     * @param type The display label for the log type.
     * @return The fully formatted log string.
     */
    private static String getFormattedMessage(String input, String type) {
        String prefix = FORMAT;
        prefix = prefix.replaceAll(Pattern.quote("%timestamp"), getTimestamp());
        prefix = prefix.replaceAll(Pattern.quote("%type"), type);
        prefix = prefix.replaceAll(Pattern.quote("%message"), "");

        String indentedMessage = indentMultiline(input, prefix);
        return prefix + indentedMessage;
    }

    /**
     * Returns the current timestamp as a formatted string.
     *
     * @return A timestamp string in {@code yyyy-MM-dd HH:mm:ss} format.
     */
    private static String getTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return now.format(formatter);
    }

    /**
     * Indents multi-line messages so that each line aligns with the original prefix.
     *
     * @param message The original multi-line message.
     * @param prefix The prefix used for the first line (for alignment).
     * @return The indented, multi-line string.
     */
    private static String indentMultiline(String message, String prefix) {
        String[] lines = message.split("\n");
        if (lines.length <= 1) return message;

        StringBuilder builder = new StringBuilder(lines[0]);
        String padding = " ".repeat(prefix.length());

        for (int i = 1; i < lines.length; i++) {
            builder.append("\n").append(padding).append(lines[i]);
        }

        return builder.toString();
    }

    /**
     * <b>Console.LogEntryObject</b> - Concrete implementation of the {@link LogEntry} interface.
     * <p>This class represents a single log entry with a type, message, and optional exception.
     * It supports queueing for asynchronous log processing and tracks whether it has been processed.</p>
     *
     * <br><b>Fields:</b>
     * <ul>
     *   <li>{@link LogEntryObject#type}</li>
     *   <li>{@link LogEntryObject#message}</li>
     *   <li>{@link LogEntryObject#exception}</li>
     *   <li>{@link LogEntryObject#isUpToQueue}</li>
     * </ul>
     *
     * <br><b>Methods:</b>
     * <ul>
     *   <li>{@link #getType()}</li>
     *   <li>{@link #getMessage()}</li>
     *   <li>{@link #getException()}</li>
     *   <li>{@link #upToQueue()}</li>
     *   <li>{@link #isUpToQueue()}</li>
     *   <li>{@link #queue()}</li>
     * </ul>
     *
     * @see Console
     * @see LogEntry
     * @see LogEntryType
     */
    public static class LogEntryObject implements LogEntry {
        /**
         * The type of log entry (e.g., INFO, DEBUG, WARNING, ERROR).
         */
        private final LogEntryType type;
        /**
         * The main message content of the log entry.
         */
        private final String message;
        /**
         * The exception associated with this log entry, if any.
         */
        private final Exception exception;
        /**
         * Flag indicating whether this log entry has been queued for output.
         */
        private boolean isUpToQueue = false;

        /**
         * Constructs a log entry with the specified type and message.
         *
         * @param type {@link LogEntryObject#type}
         * @param message {@link LogEntryObject#message}
         * @see LogEntryObject
         */
        public LogEntryObject(@NotNull LogEntryType type, @NotNull String message) {
            this.type = type;
            this.message = message;
            this.exception = null;
        }

        /**
         * Constructs a log entry from the given exception.
         *
         * @param type {@link LogEntryObject#type}
         * @param exception {@link LogEntryObject#exception}
         * @see LogEntryObject
         */
        public LogEntryObject(@NotNull LogEntryType type, @NotNull Exception exception) {
            this.type = type;
            this.message = exception.getMessage();
            this.exception = exception;
        }

        /**
         * Retrieves the type of this log entry.
         *
         * @return {@link LogEntryObject#type}
         */
        @Override @NotNull
        public LogEntryType getType() {
            return this.type;
        }

        /**
         * Retrieves the main log message content.
         *
         * @return {@link LogEntryObject#message}
         */
        @Override @NotNull
        public String getMessage() {
            return this.message;
        }

        /**
         * Retrieves the exception associated with this log entry, if available.
         *
         * @return {@link LogEntryObject#exception} (May be {@code null})
         */
        @Override @Nullable
        public Exception getException() {
            return this.exception;
        }

        /**
         * Flags this log entry as "up to queue" and triggers its immediate output to the console,
         * using the appropriate display format based on its {@link LogEntryType}.
         *
         * @return This {@link LogEntryObject} instance, marked as processed.
         */
        @Override
        public LogEntryObject upToQueue() {
            this.isUpToQueue = true;

            switch (this.type) {
                case INFO:
                    sendLog(this);
                    break;
                case WARNING:
                    sendWarn(this);
                    break;
                case ERROR:
                    sendError(this);
                    break;
                case DEBUG:
                    sendDebug(this);
                    break;
            }

            return this;
        }

        /**
         * Indicates whether this log entry has already been sent to the console.
         *
         * @return {@code true} if this entry has been marked for output, otherwise {@code false}.
         */
        @Override
        public boolean isUpToQueue() {
            return this.isUpToQueue;
        }

        /**
         * Queues this log entry for asynchronous processing by the logging thread.
         *
         * @return This {@link LogEntryObject} instance, now added to the queue.
         */
        @Override
        public LogEntryObject queue() {
            logQueue.add(this);
            return this;
        }
    }

    /**
     * <b>Console.Progress</b> - A utility class for tracking and displaying progress logs.
     * <p>This class implements {@link LogEntry} and is designed for multi-step operations that need progress updates.
     * It includes tracking, formatting, and error-handling capabilities for progress-based logs.</p>
     *
     * <br><b>Fields:</b>
     * <ul>
     *   <li>{@link Progress#type}</li>
     *   <li>{@link Progress#message}</li>
     *   <li>{@link Progress#progressMessage}</li>
     *   <li>{@link Progress#numSteps}</li>
     *   <li>{@link Progress#percentComplete}</li>
     *   <li>{@link Progress#finished}</li>
     *   <li>{@link Progress#exception}</li>
     * </ul>
     *
     * <br><b>Methods:</b>
     * <ul>
     *   <li>{@link #getType()}</li>
     *   <li>{@link #getMessage()}</li>
     *   <li>{@link #getProgressMessage()}</li>
     *   <li>{@link #setProgressMessage(String)}</li>
     *   <li>{@link #getNumSteps()}</li>
     *   <li>{@link #getPercentComplete()}</li>
     *   <li>{@link #setPercentComplete(double)}</li>
     *   <li>{@link #step()}</li>
     *   <li>{@link #complete()}</li>
     *   <li>{@link #error(Exception)}</li>
     * </ul>
     *
     * <b>Usage:</b>
     * <p>Use {@link Console#progress(String, int)} to create a new {@code Progress} log. Call {@code step()} to increment the progress,
     * {@code setProgressMessage()} to update the sub-status, and {@code complete()} to finish it.</p>
     *
     * @see Console
     */
    public static class Progress implements LogEntry {
        /**
         * The log type for this entry, always {@link LogEntryType#PROGRESS}.
         */
        private final LogEntryType type = LogEntryType.PROGRESS;
        /**
         * The message describing what the progress operation is.
         */
        private final String message;
        /**
         * A sub-message that reflects the current status of the progress.
         */
        private String progressMessage = null;
        /**
         * An exception that occurred during the progress operation, if any.
         */
        private Exception exception = null;
        /**
         * Indicates whether this progress entry has been queued.
         */
        private boolean isUpToQueue = false;
        /**
         * Total number of steps required to complete the progress.
         */
        private final int numSteps;
        /**
         * The current percent complete value, represented as a double.
         */
        private double percentComplete = 0.0;
        /**
         * Flag indicating whether the progress has been marked complete.
         */
        private boolean finished = false;

        /**
         * Constructs a {@link Progress} tracker for a multi-step operation.
         *
         * @param message {@link Progress#message}
         * @param numSteps {@link Progress#numSteps}
         * @see Progress
         */
        public Progress(String message, int numSteps) {
            this.message = message;
            this.numSteps = numSteps;
        }

        /**
         * Returns the log type associated with this entry.
         * Always returns {@link LogEntryType#PROGRESS}.
         *
         * @return {@link Progress#type}
         */
        @Override @NotNull
        public LogEntryType getType() {
            return this.type;
        }

        /**
         * Retrieves the base progress message or task label.
         *
         * @return {@link Progress#message}
         */
        @Override @NotNull
        public String getMessage() {
            return this.message;
        }

        /**
         * Retrieves the progress sub-message (e.g., current step label).
         *
         * @return {@link Progress#progressMessage} (May be {@code null})
         */
        @Nullable
        public String getProgressMessage() {
            return this.progressMessage;
        }

        /**
         * Sets a new sub-message for this progress entry and updates the console output if applicable.
         *
         * @param progressMessage A short message describing the current phase or step of the operation.
         * @return this {@link Progress}
         */
        public Progress setProgressMessage(@Nullable String progressMessage) {
            this.progressMessage = progressMessage;
            if (this.isUpToQueue) {
                System.out.print("\r\033[2K");
                System.out.print(getFormattedMessage(message + " - " + getPercentComplete() + "%" + (this.progressMessage != null ? ": " + this.progressMessage : ""), this.type.getDisplay()));
            }
            return this;
        }

        /**
         * Retrieves the progress exception if applicable.
         *
         * @return {@link Progress#exception} (May be {@code null})
         */
        @Override @Nullable
        public Exception getException() {
            return this.exception;
        }

        /**
         * Flags this progress entry as "up to queue" and triggers its immediate output to the console.
         *
         * @return This {@link Progress} instance, marked as processed.
         */
        @Override
        public Progress upToQueue() {
            this.isUpToQueue = true;
            Console.queuePaused = true;

            System.out.print("\r\033[2K");
            if (this.exception == null) {
                System.out.print(getFormattedMessage(message + " - " + getPercentComplete() + "%: " + (this.progressMessage != null ? ": " + this.progressMessage : ""), this.type.getDisplay()));
            } else {
                System.out.println(getFormattedMessage(message + " - \u001B[34mERR\u001B[0m", this.type.getDisplay()));
            }

            if (this.percentComplete >= 100.0 || this.finished) {
                System.out.print("\r\033[2K");
                System.out.print(getFormattedMessage(message + " - \u001B[32mDONE\u001B[0m", this.type.getDisplay()));
                System.out.println();

                if (Console.cliRunning) {
                    System.out.print(PROMPT);
                }
            }

            return this;
        }

        /**
         * Indicates whether this progress entry has already been sent to the console.
         *
         * @return {@code true} if this entry has been marked for output, otherwise {@code false}.
         */
        @Override
        public boolean isUpToQueue() {
            return this.isUpToQueue;
        }

        /**
         * Returns the total number of steps defined for the progress operation.
         *
         * @return {@link Progress#numSteps}
         */
        public int getNumSteps() {
            return this.numSteps;
        }

        /**
         * Returns the current percent completion of this operation, rounded to one decimal place.
         *
         * @return {@link Progress#percentComplete}
         */
        public double getPercentComplete() {
            return Math.ceil((this.percentComplete * 10.0) / 10.0);
        }

        /**
         * Updates the progress percent directly.
         *
         * @param percentComplete The new percent completion value.
         * @return this {@link Progress}
         */
        public Progress setPercentComplete(double percentComplete) {
            this.percentComplete = percentComplete;
            return this;
        }

        /**
         * Checks whether the progress entry has been marked as complete.
         *
         * @return {@code true} if completed; otherwise, {@code false}.
         */
        public boolean isFinished() {
            return this.finished;
        }

        /**
         * Queues this progress entry for asynchronous logging.
         *
         * @return this {@link Progress}
         */
        @Override
        public Progress queue() {
            logQueue.add(this);
            return this;
        }

        /**
         * Associates an error with this progress entry, prints it to the console,
         * and completes the operation immediately.
         *
         * @param exception The {@link Exception} to associate with this progress.
         * @return this {@link Progress}
         */
        public Progress error(Exception exception) {
            this.exception = exception;
            if (this.isUpToQueue) {
                System.out.print("\r\033[2K");
                System.out.print(getFormattedMessage(message + " - \u001B[34mERR\u001B[0m", this.type.getDisplay()));
            }
            Console.error(exception);
            complete();
            return this;
        }

        /**
         * Increments progress by one step and updates the percent complete.
         * If the operation is finished, finalizes and prints completion.
         *
         * @return this {@link Progress}
         */
        public Progress step() {
            if (!this.finished) {
                this.percentComplete += 100.0 / this.numSteps;
                if (this.isUpToQueue) {
                    System.out.print("\r\033[2K");
                    System.out.print(getFormattedMessage(message + " - " + getPercentComplete() + "%: " + (this.progressMessage != null ? ": " + this.progressMessage : ""), this.type.getDisplay()));
                }
                //if (this.percentComplete > ((100.0 / this.numSteps) * (this.numSteps - 1))) {
                if (this.percentComplete >= 100.0) {
                    System.out.print("\r\033[2K");
                    System.out.print(getFormattedMessage(message + " - \u001B[32mDONE\u001B[0m", this.type.getDisplay()));
                    complete();
                }
                return this;
            }
            return this;
        }

        /**
         * Marks the progress operation as 100% complete and resumes the log queue if paused.
         * Prints final output to the console.
         */
        public void complete() {
            this.percentComplete = 100.0;
            if (this.isUpToQueue) {
                System.out.println();

                if (Console.cliRunning) {
                    System.out.print(PROMPT);
                }
            }
            this.finished = true;
            Console.queuePaused = false;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Console.error(e);
            }
            synchronized (Console.logThread) {
                Console.logThread.notifyAll();
            }
        }
    }
}
