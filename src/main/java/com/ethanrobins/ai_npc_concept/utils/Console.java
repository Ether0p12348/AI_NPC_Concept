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

public class Console {
    public static volatile LogLevel logLevel = LogLevel.INFO;
    public static final String FORMAT = "[%timestamp] [%type] -> %message";

    private static volatile boolean cliRunning = false;
    private static volatile Thread cliThread = null;
    private static volatile Scanner scanner = null;
    private static final String PROMPT = "> ";

    private static final BlockingQueue<LogEntry> logQueue = new LinkedBlockingQueue<>();
    private static volatile boolean queueRunning = true;
    private static volatile boolean queuePaused = false;
    private static final Thread logThread;

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

    public static void log(String message) {
        new LogEntryObject(LogEntryType.INFO, message).queue();
    }

    private static void sendLog(LogEntryObject entry) {
        System.out.print("\r\033[2K");
        System.out.println(getFormattedMessage(entry.getMessage(), entry.getType().getDisplay()));

        if (cliRunning) {
            System.out.print(PROMPT);
        }
    }

    public static void warn(String message) {
        new LogEntryObject(LogEntryType.WARNING, message).queue();
    }

    public static void warn(Exception exception) {
        new LogEntryObject(LogEntryType.WARNING, exception).queue();
    }

    private static void sendWarn(LogEntryObject entry) {
        System.out.print("\r\033[2K");
        System.out.println(getFormattedMessage("\u001B[33m" + entry.getMessage() + "\u001B[0m", entry.getType().getDisplay()));

        if (cliRunning) {
            System.out.print(PROMPT);
        }
    }

    public static void error(Exception exception) {
        new LogEntryObject(LogEntryType.ERROR, exception).queue();
    }

    private static void sendError(LogEntryObject entry) {
        System.out.print("\r\033[2K");
        System.out.println(getFormattedMessage("\u001B[31m" + entry.getMessage() + "\u001B[0m", entry.getType().getDisplay()));
        if (entry.getException() != null) {
            entry.getException().printStackTrace();
        }

        if (cliRunning) {
            System.out.print(PROMPT);
        }
    }

    public static void debug(String message) {
        new LogEntryObject(LogEntryType.DEBUG, message).queue();
    }

    private static void sendDebug(LogEntryObject entry) {
        if (logLevel == LogLevel.DEBUG) {
            System.out.print("\r\033[2K");
            System.out.println(getFormattedMessage("\u001B[33m" + entry.getMessage() + "\u001B[0m", entry.getType().getDisplay()));

            if (cliRunning) {
                System.out.print(PROMPT);
            }
        }
    }

    public static Progress progress(String message, int numSteps) {
        return new Progress(message, numSteps).queue();
    }

    private static String getFormattedMessage(String input, String type) {
        String prefix = FORMAT;
        prefix = prefix.replaceAll(Pattern.quote("%timestamp"), getTimestamp());
        prefix = prefix.replaceAll(Pattern.quote("%type"), type);
        prefix = prefix.replaceAll(Pattern.quote("%message"), "");

        String indentedMessage = indentMultiline(input, prefix);
        return prefix + indentedMessage;
    }

    private static String getTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return now.format(formatter);
    }

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

    public static class LogEntryObject implements LogEntry {
        private final LogEntryType type;
        private final String message;
        private final Exception exception;
        private boolean isUpToQueue = false;

        public LogEntryObject(@NotNull LogEntryType type, @NotNull String message) {
            this.type = type;
            this.message = message;
            this.exception = null;
        }

        public LogEntryObject(@NotNull LogEntryType type, @NotNull Exception exception) {
            this.type = type;
            this.message = exception.getMessage();
            this.exception = exception;
        }

        @Override @NotNull
        public LogEntryType getType() {
            return this.type;
        }

        @Override @NotNull
        public String getMessage() {
            return this.message;
        }

        @Override @Nullable
        public Exception getException() {
            return this.exception;
        }

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

        @Override
        public boolean isUpToQueue() {
            return this.isUpToQueue;
        }

        @Override
        public LogEntryObject queue() {
            logQueue.add(this);
            return this;
        }
    }

    public static class Progress implements LogEntry {
        private final LogEntryType type = LogEntryType.PROGRESS;
        private final String message;
        private String progressMessage = null;
        private Exception exception = null;
        private boolean isUpToQueue = false;
        private final int numSteps;
        private double percentComplete = 0.0;
        private boolean finished = false;

        public Progress(String message, int numSteps) {
            this.message = message;
            this.numSteps = numSteps;
        }

        @Override @NotNull
        public LogEntryType getType() {
            return this.type;
        }

        @Override @NotNull
        public String getMessage() {
            return this.message;
        }

        public String getProgressMessage() {
            return this.progressMessage;
        }

        public Progress setProgressMessage(@Nullable String progressMessage) {
            this.progressMessage = progressMessage;
            if (this.isUpToQueue) {
                System.out.print("\r\033[2K");
                System.out.print(getFormattedMessage(message + " - " + getPercentComplete() + "%" + (this.progressMessage != null ? ": " + this.progressMessage : ""), this.type.getDisplay()));
            }
            return this;
        }

        @Override @Nullable
        public Exception getException() {
            return this.exception;
        }

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

        @Override
        public boolean isUpToQueue() {
            return this.isUpToQueue;
        }

        public int getNumSteps() {
            return this.numSteps;
        }

        public double getPercentComplete() {
            return Math.ceil((this.percentComplete * 10.0) / 10.0);
        }

        public Progress setPercentComplete(double percentComplete) {
            this.percentComplete = percentComplete;
            return this;
        }

        public boolean isFinished() {
            return this.finished;
        }

        @Override
        public Progress queue() {
            logQueue.add(this);
            return this;
        }

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
