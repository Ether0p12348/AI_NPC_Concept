package com.ethanrobins.ai_npc_concept.utils;

import com.ethanrobins.ai_npc_concept.Main;
import com.ethanrobins.ai_npc_concept.commands.Command;
import com.ethanrobins.ai_npc_concept.commands.End;
import com.ethanrobins.ai_npc_concept.commands.Help;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Console {
    private static volatile boolean cliRunning = true;
    private static volatile Scanner scanner = null;
    private static final String PROMPT = "> ";
    public static LogLevel logLevel = LogLevel.INFO;
    public static final String FORMAT = "[%timestamp] [%type] -> %message";

    public static void startCli() throws IllegalStateException {
        Console.log("Starting CLI...");

        Thread cliThread = new Thread(() -> {
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
                            break;
                        case "help":
                            Objects.requireNonNull(Command.getCommand("help")).execute(commandString);
                            break;
                        default:
                            warn(new Exception("Command not implemented: " + commandString.getCommand()));
                    }
                } catch (NullPointerException e) {
                    error(e);
                }
            }
        });
        cliThread.start();
    }

    public static void log(String message) {
        System.out.print("\r\033[2K");
        System.out.println(getFormattedMessage(message, "INFO"));

        if (cliRunning) {
            System.out.print(PROMPT);
        }
    }

    public static void warn(String message) {
        System.out.print("\r\033[2K");
        System.out.println("\u001B[33m" + getFormattedMessage(message, "WARNING") + "\u001B[0m");

        if (cliRunning) {
            System.out.print(PROMPT);
        }
    }

    public static void warn(Exception exception) {
        System.out.print("\r\033[2K");
        System.err.println("\u001B[33m" + getFormattedMessage(exception.getMessage(), "WARNING") + "\u001B[0m");

        if (cliRunning) {
            System.out.print(PROMPT);
        }
    }

    public static void error(Exception exception) {
        System.out.print("\r\033[2K");
        System.err.println(getFormattedMessage(exception.getMessage(), "ERROR"));
        exception.printStackTrace();

        if (cliRunning) {
            System.out.print(PROMPT);
        }
    }

    public static void debug(String message) {
        if (logLevel == LogLevel.DEBUG) {
            System.out.print("\r\033[2K");
            System.out.println("\u001B[33m" + getFormattedMessage(message, "DEBUG") + "\u001B[0m");

            if (cliRunning) {
                System.out.print(PROMPT);
            }
        }
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
}
