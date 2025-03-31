package com.ethanrobins.ai_npc_concept.utils;

import org.jetbrains.annotations.Nullable;

/**
 * <b>Formatting</b> - Utility class for constructing ANSI text formatting codes.
 * <p>Provides static helpers for combining foreground, background, and style sequences into a single
 * ANSI formatting string. Also includes a global reset function and a marker interface for color/style enums.</p>
 *
 * <br><b>Methods:</b>
 * <ul>
 *   <li>{@link Formatting#mix(String, String, String...)}</li>
 *   <li>{@link Formatting#resetAll()}</li>
 * </ul>
 *
 * <br><b>Interfaces:</b>
 * <ul>
 *   <li>{@link Formatting.Formatter}</li>
 * </ul>
 *
 * <b>Usage:</b>
 * <p>Use this class to generate reusable, customizable ANSI format strings from {@link Color}, {@link Style}, or
 * raw ANSI fragments.</p>
 *
 * @see Color
 * @see Style
 */
public class Formatting {
    /**
     * Combines foreground, background, and one or more style codes into a single ANSI sequence.
     *
     * @param fg The foreground color escape sequence, or {@code null}.
     * @param bg The background color escape sequence, or {@code null}.
     * @param style Optional additional style codes (e.g., bold, italic).
     * @return Combined ANSI escape sequence string.
     */
    public static String mix(@Nullable String fg, @Nullable String bg, String... style) {
        StringBuilder sb = new StringBuilder();
        sb.append(fg != null ? fg : "");
        sb.append(bg != null ? bg : "");

        for (String s : style) {
            sb.append(s);
        }

        return sb.toString();
    }

    /**
     * Resets all formatting by combining {@link Style#resetAll()} and {@link Color#resetAll()}.
     *
     * @return The complete ANSI sequence to reset all text formatting.
     */
    public static String resetAll() {
        return Style.resetAll() + Color.resetAll();
    }

//    public static void test() {
//        Thread thread = new Thread(() -> {
//            StringBuilder sb = new StringBuilder();
//            sb.append(Color.colorize("RESET (default)", Color.RESET.fg())).append("\n");
//            sb.append(Color.colorize("BLACK", Color.BLACK.fg())).append("\n");
//            sb.append(Color.colorize("RED", Color.RED.fg())).append("\n");
//            sb.append(Color.colorize("GREEN", Color.GREEN.fg())).append("\n");
//            sb.append(Color.colorize("YELLOW", Color.YELLOW.fg())).append("\n");
//            sb.append(Color.colorize("BLUE", Color.BLUE.fg())).append("\n");
//            sb.append(Color.colorize("PURPLE", Color.PURPLE.fg())).append("\n");
//            sb.append(Color.colorize("CYAN", Color.CYAN.fg())).append("\n");
//            sb.append(Color.colorize("WHITE", Color.WHITE.fg())).append("\n");
//            sb.append("\n");
//            sb.append(Color.colorize("BRIGHT_BLACK", Color.BRIGHT_BLACK.fg())).append("\n");
//            sb.append(Color.colorize("BRIGHT_RED", Color.BRIGHT_RED.fg())).append("\n");
//            sb.append(Color.colorize("BRIGHT_GREEN", Color.BRIGHT_GREEN.fg())).append("\n");
//            sb.append(Color.colorize("BRIGHT_YELLOW", Color.BRIGHT_YELLOW.fg())).append("\n");
//            sb.append(Color.colorize("BRIGHT_BLUE", Color.BRIGHT_BLUE.fg())).append("\n");
//            sb.append(Color.colorize("BRIGHT_PURPLE", Color.BRIGHT_PURPLE.fg())).append("\n");
//            sb.append(Color.colorize("BRIGHT_CYAN", Color.BRIGHT_CYAN.fg())).append("\n");
//            sb.append(Color.colorize("BRIGHT_WHITE", Color.BRIGHT_WHITE.fg())).append("\n");
//            sb.append("\n");
//            sb.append(Color.colorize("BLACK_BACKGROUND", Color.BLACK.bg())).append("\n");
//            sb.append(Color.colorize("RED_BACKGROUND", Color.RED.bg())).append("\n");
//            sb.append(Color.colorize("GREEN_BACKGROUND", Color.GREEN.bg())).append("\n");
//            sb.append(Color.colorize("YELLOW_BACKGROUND", Color.YELLOW.bg())).append("\n");
//            sb.append(Color.colorize("BLUE_BACKGROUND", Color.BLUE.bg())).append("\n");
//            sb.append(Color.colorize("PURPLE_BACKGROUND", Color.PURPLE.bg())).append("\n");
//            sb.append(Color.colorize("CYAN_BACKGROUND", Color.CYAN.bg())).append("\n");
//            sb.append(Color.colorize("WHITE_BACKGROUND", Color.WHITE.bg())).append("\n");
//            sb.append("\n");
//            sb.append(Color.colorize("BRIGHT_BLACK_BACKGROUND", Color.BRIGHT_BLACK.bg())).append("\n");
//            sb.append(Color.colorize("BRIGHT_RED_BACKGROUND", Color.BRIGHT_RED.bg())).append("\n");
//            sb.append(Color.colorize("BRIGHT_GREEN_BACKGROUND", Color.BRIGHT_GREEN.bg())).append("\n");
//            sb.append(Color.colorize("BRIGHT_YELLOW_BACKGROUND", Color.BRIGHT_YELLOW.bg())).append("\n");
//            sb.append(Color.colorize("BRIGHT_BLUE_BACKGROUND", Color.BRIGHT_BLUE.bg())).append("\n");
//            sb.append(Color.colorize("BRIGHT_PURPLE_BACKGROUND", Color.BRIGHT_PURPLE.bg())).append("\n");
//            sb.append(Color.colorize("BRIGHT_CYAN_BACKGROUND", Color.BRIGHT_CYAN.bg())).append("\n");
//            sb.append(Color.colorize("BRIGHT_WHITE_BACKGROUND", Color.BRIGHT_WHITE.bg())).append("\n");
//            sb.append("\n");
//            sb.append(Style.stylize("BOLD", Style.BOLD)).append("\n");
//            sb.append(Style.stylize("ITALIC", Style.ITALIC)).append("\n");
//            sb.append(Style.stylize("UNDERLINE", Style.UNDERLINE)).append("\n");
//            sb.append(Color.colorize("BRIGHT_WHITE with BLACK_BACKGROUND " + Style.REVERSE + "REVERSE", Formatting.mix(Color.BRIGHT_WHITE.fg(), Color.BLACK.bg()))).append("\n");
//            sb.append(Style.stylize("STRIKETHROUGH", Style.STRIKETHROUGH)).append("\n");
//
//            System.out.println(sb);
//
//            System.out.println(Color.colorize("All 8-bit colors:", Formatting.mix(Color.BRIGHT_WHITE.fg(), Color.BLACK.bg())));
//            int width = 16; // Number of colors in each row (to maintain a compact grid)
//            int height = 16; // Since there are 256 colors, 16x16 grid works perfectly
//
//            for (int y = 0; y < height; y++) {
//                for (int x = 0; x < width; x++) {
//                    int colorCode = y * width + x; // Compute the color code from row and column
//                    // Append the block character along with the color code as a demo
//                    System.out.print(Color.colorize(String.format("%3d ", colorCode), Formatting.mix(Color.fg(26,48,112), Color.bg(colorCode))));
//                }
//                System.out.println(); // Move to the next line after printing one row
//            }
//            System.out.println();
//
//
//            System.out.println(Color.colorize("24-bit colors (not limited to):", Formatting.mix(Color.BRIGHT_WHITE.fg(), Color.BLACK.bg())));
//            width = 80;  // Width of the console output
//            height = 20; // Number of gradient rows
//            for (int y = 0; y < height; y++) {
//                for (int x = 0; x < width; x++) {
//                    // Generate RGB based on position
//                    int red = (x * 255) / (width - 1);  // Red intensity gradient left to right
//                    int green = (y * 255) / (height - 1); // Green intensity gradient top to bottom
//                    int blue = 255 - ((x * 255) / (width - 1)); // Blue intensity decreases left to right
//
//                    // Build ANSI 24-bit color code
//                    System.out.print(Color.colorize("█", Formatting.mix(Color.fg(red, green, blue), Color.bg(red, green, blue))));
//                }
//                System.out.println();
//            }
//        });
//        thread.start();
//    }

    /**
     * <b>Formatting.Formatter</b> - Marker interface for formatting enums such as {@link Color} or {@link Style}.
     * <p>This interface is used to represent objects that return ANSI codes through {@link Object#toString()}.</p>
     *
     * <b>Usage:</b>
     * <p>Used internally to ensure type compatibility and semantic clarity across formatting utilities.</p>
     */
    public interface Formatter {
        /**
         * Returns the ANSI escape sequence for the implementing formatter.
         *
         * @return ANSI code as a string.
         */
        @Override
        String toString();
    }
}
