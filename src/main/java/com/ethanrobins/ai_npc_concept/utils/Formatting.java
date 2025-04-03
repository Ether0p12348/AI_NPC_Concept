package com.ethanrobins.ai_npc_concept.utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * <b>Formatting</b> - Utility class for constructing ANSI text formatting codes.
 * <p>Provides static helpers for combining foreground, background, and style sequences into a single
 * ANSI formatting string. Also includes a global reset function and a marker interface for color/style enums.</p>
 *
 * <br><b>Methods:</b>
 * <ul>
 *   <li>{@link Formatting#mix(Color.ColorObject, Style.StyleObject)}</li>
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
 * @see Mix
 * @see Formatter
 * @see FormatObject
 */
public class Formatting {
    /**
     * The global RegEx pattern for this system.
     * <br><a href="https://regexr.com/8dprj">Test it!</a>
     */
    public static final String PATTERN = "\\u001B\\[(0|(3|4|9|10)[0-79]|[34]8;(5;\\d{1,3}|2;\\d{1,3};\\d{1,3};\\d{1,3})|[1-57-9]|2[2-57-9])m";

    /**
     * Combines foreground, background, and one or more style codes into a single ANSI sequence.
     *
     * @param colorObject The color object.
     * @param styleObject The style object.
     * @return Combined ANSI escape sequence Mix object.
     */
    public static Mix<Color.ColorObject, Style.StyleObject> mix(@NotNull Color.ColorObject colorObject, @NotNull Style.StyleObject styleObject) {
        Color.ColorObject color;
        Class<? extends Color.ColorObject> colorClass;
        switch (colorObject) {
            case Color.Foreground fg -> {
                color = new Color.Foreground(fg.toString());
                colorClass = Color.Foreground.class;
            }
            case Color.Background bg -> {
                color = new Color.Background(bg.toString());
                colorClass = Color.Background.class;
            }
            case Color.Mix m -> {
                color = new Color.Mix(new Color.Foreground(m.foreground().toString()), new Color.Background(m.background().toString()));
                colorClass = Color.Mix.class;
            }
            default ->
                    throw new IllegalArgumentException("Invalid color object type: " + colorObject.getClass().getName());
        }

        Style.StyleObject style;
        Class<? extends Style.StyleObject> styleClass;
        if (styleObject instanceof Style.SetStyle s) {
            style = new Style.SetStyle(s.toString());
            styleClass = Style.SetStyle.class;
        } else if (styleObject instanceof Style.Mix<?> m) {
            if (m.ansi() instanceof Style.SetStyle[] s) {
                List<Style.SetStyle> styles = List.of(s);
                style = new Style.Mix<>(styles.toArray(new Style.SetStyle[0]));
                styleClass = Style.Mix.class;
            } else {
                throw new IllegalArgumentException("Invalid style object type: " + m.ansi().getClass().getName());
            }
        } else {
            throw new IllegalArgumentException("Invalid style object type: " + styleObject.getClass().getName());
        }

        return new Mix<>(colorClass.cast(color), styleClass.cast(style));
    }

    /**
     * Resets all formatting by combining {@link Style#resetAll()} and {@link Color#resetAll()}.
     *
     * @return The complete ANSI sequence to reset all text formatting.
     */
    public static FormatObject resetAll() {
        return new Mix<>(Color.resetAll(), Style.resetAll());
    }

    /**
     * Prints all features offered by the Formatting system.
     */
    public static void test() {
        Thread thread = new Thread(() -> {
            StringBuilder sb = new StringBuilder();
            sb.append(Color.colorize("RESET (default)", Color.RESET.fg())).append("\n");
            sb.append(Color.colorize("BLACK", Color.BLACK.fg())).append("\n");
            sb.append(Color.colorize("RED", Color.RED.fg())).append("\n");
            sb.append(Color.colorize("GREEN", Color.GREEN.fg())).append("\n");
            sb.append(Color.colorize("YELLOW", Color.YELLOW.fg())).append("\n");
            sb.append(Color.colorize("BLUE", Color.BLUE.fg())).append("\n");
            sb.append(Color.colorize("PURPLE", Color.PURPLE.fg())).append("\n");
            sb.append(Color.colorize("CYAN", Color.CYAN.fg())).append("\n");
            sb.append(Color.colorize("WHITE", Color.WHITE.fg())).append("\n");
            sb.append("\n");
            sb.append(Color.colorize("BRIGHT_BLACK", Color.BRIGHT_BLACK.fg())).append("\n");
            sb.append(Color.colorize("BRIGHT_RED", Color.BRIGHT_RED.fg())).append("\n");
            sb.append(Color.colorize("BRIGHT_GREEN", Color.BRIGHT_GREEN.fg())).append("\n");
            sb.append(Color.colorize("BRIGHT_YELLOW", Color.BRIGHT_YELLOW.fg())).append("\n");
            sb.append(Color.colorize("BRIGHT_BLUE", Color.BRIGHT_BLUE.fg())).append("\n");
            sb.append(Color.colorize("BRIGHT_PURPLE", Color.BRIGHT_PURPLE.fg())).append("\n");
            sb.append(Color.colorize("BRIGHT_CYAN", Color.BRIGHT_CYAN.fg())).append("\n");
            sb.append(Color.colorize("BRIGHT_WHITE", Color.BRIGHT_WHITE.fg())).append("\n");
            sb.append("\n");
            sb.append(Color.colorize("BLACK_BACKGROUND", Color.BLACK.bg())).append("\n");
            sb.append(Color.colorize("RED_BACKGROUND", Color.RED.bg())).append("\n");
            sb.append(Color.colorize("GREEN_BACKGROUND", Color.GREEN.bg())).append("\n");
            sb.append(Color.colorize("YELLOW_BACKGROUND", Color.YELLOW.bg())).append("\n");
            sb.append(Color.colorize("BLUE_BACKGROUND", Color.BLUE.bg())).append("\n");
            sb.append(Color.colorize("PURPLE_BACKGROUND", Color.PURPLE.bg())).append("\n");
            sb.append(Color.colorize("CYAN_BACKGROUND", Color.CYAN.bg())).append("\n");
            sb.append(Color.colorize("WHITE_BACKGROUND", Color.WHITE.bg())).append("\n");
            sb.append("\n");
            sb.append(Color.colorize("BRIGHT_BLACK_BACKGROUND", Color.BRIGHT_BLACK.bg())).append("\n");
            sb.append(Color.colorize("BRIGHT_RED_BACKGROUND", Color.BRIGHT_RED.bg())).append("\n");
            sb.append(Color.colorize("BRIGHT_GREEN_BACKGROUND", Color.BRIGHT_GREEN.bg())).append("\n");
            sb.append(Color.colorize("BRIGHT_YELLOW_BACKGROUND", Color.BRIGHT_YELLOW.bg())).append("\n");
            sb.append(Color.colorize("BRIGHT_BLUE_BACKGROUND", Color.BRIGHT_BLUE.bg())).append("\n");
            sb.append(Color.colorize("BRIGHT_PURPLE_BACKGROUND", Color.BRIGHT_PURPLE.bg())).append("\n");
            sb.append(Color.colorize("BRIGHT_CYAN_BACKGROUND", Color.BRIGHT_CYAN.bg())).append("\n");
            sb.append(Color.colorize("BRIGHT_WHITE_BACKGROUND", Color.BRIGHT_WHITE.bg())).append("\n");
            sb.append("\n");
            sb.append(Color.gradient("This is a foreground GRADIENT!", Color.Foreground.fromRGB(255, 200, 200), Color.Foreground.fromRGB(200, 255, 200), Color.Foreground.fromRGB(200, 200, 255))).append("\n");
            sb.append(Color.gradient("This is a background GRADIENT!", Color.mix(Color.Foreground.fromRGB(0, 0, 0), Color.Background.fromRGB(255, 200, 200)), Color.mix(Color.Foreground.fromRGB(0, 0, 0), Color.Background.fromRGB(200, 255, 200)), Color.mix(Color.Foreground.fromRGB(0, 0, 0), Color.Background.fromRGB(200, 200, 255)))).append("\n");
            sb.append("\n");
            sb.append(Style.stylize("BOLD", Style.BOLD.style())).append("\n");
            sb.append(Style.stylize("ITALIC", Style.ITALIC.style())).append("\n");
            sb.append(Style.stylize("UNDERLINE", Style.UNDERLINE.style())).append("\n");
            sb.append(Color.colorize("BRIGHT_WHITE with BLACK_BACKGROUND " + Style.stylize("REVERSE", Style.REVERSE.style()), Color.mix(Color.BRIGHT_WHITE.fg(), Color.BLACK.bg()))).append("\n");
            sb.append(Style.stylize("STRIKETHROUGH", Style.STRIKETHROUGH.style())).append("\n");

            System.out.println(sb);

            System.out.println(Color.colorize("All 8-bit colors:", Color.mix(Color.BRIGHT_WHITE.fg(), Color.BLACK.bg())));
            int width = 16;
            int height = 16;

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int colorCode = y * width + x;
                    System.out.print(Color.colorize(String.format("%3d ", colorCode), Color.mix(Color.fg(26,48,112), Color.bg(colorCode))));
                }
                System.out.println();
            }
            System.out.println();


            System.out.println(Color.colorize("24-bit colors (not limited to):", Color.mix(Color.BRIGHT_WHITE.fg(), Color.BLACK.bg())));
            width = 80;
            height = 20;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int red = (x * 255) / (width - 1);
                    int green = (y * 255) / (height - 1);
                    int blue = 255 - ((x * 255) / (width - 1));

                    System.out.print(Color.colorize("█", Color.mix(Color.fg(red, green, blue), Color.bg(red, green, blue))));
                }
                System.out.println();
            }
        });
        thread.start();
    }

    /**
     * <b>Formatting.Mix</b> - Combines a {@link Color.ColorObject} and a {@link Style.StyleObject} into a single formatted unit.
     * <p>This generic formatting object can be used to apply both color and style simultaneously in ANSI terminal output.
     * The formatting sequence is generated by concatenating the ANSI strings of the color and style.</p>
     *
     * <br><b>Implements:</b>
     * <ul>
     *   <li>{@link Formatting.FormatObject}</li>
     * </ul>
     *
     * <br><b>Fields:</b>
     * <ul>
     *   <li>{@link Formatting.Mix#color}</li>
     *   <li>{@link Formatting.Mix#style}</li>
     * </ul>
     *
     * <br><b>Methods:</b>
     * <ul>
     *   <li>{@link Formatting.Mix#color()}</li>
     *   <li>{@link Formatting.Mix#style()}</li>
     *   <li>{@link Formatting.Mix#toString()}</li>
     *   <li>{@link Formatting.Mix#equals(Object)}</li>
     * </ul>
     *
     * <b>Usage:</b>
     * <pre>{@code
     * Formatting.Mix<Color.Foreground, Style.Bold> format =
     *     new Formatting.Mix<>(Color.Foreground.fromRGB(255, 0, 0), Style.BOLD);
     * System.out.println(format + "Important Text" + Color.RESET);
     * }</pre>
     *
     * @param <C> A type that extends {@link Color.ColorObject}, representing a foreground, background, or mix color.
     * @param <S> A type that extends {@link Style.StyleObject}, representing text style attributes such as bold or italic.
     * @see Color.ColorObject
     * @see Style.StyleObject
     * @see Formatting.FormatObject
     */
    public static class Mix<C extends Color.ColorObject, S extends Style.StyleObject> implements FormatObject {
        /**
         * The {@link Color.ColorObject} layer of this mix. Can be {@link Color.Foreground}, {@link Color.Background}, or a combined {@link Color.Mix}.
         */
        private final C color;
        /**
         * The text {@link Style.Styler} layer of this mix. Expected to be a {@link Style.SetStyle}, {@link Style.Reset}, or {@link Style.Mix}.
         */
        private final S style;

        /**
         * Constructs a {@link Formatting.Mix} from a color and a style object.
         *
         * @param color The {@link Color.ColorObject}, typically a {@link Color.Foreground}, {@link Color.Background}, or {@link Color.Mix}.
         * @param style The {@link Style.StyleObject}, such as {@link Style#BOLD} or {@link Style#ITALIC}.
         */
        public Mix(C color, S style) {
            this.color = color;
            this.style = style;
        }

        /**
         * Retrieves the color portion of this formatting mix.
         *
         * @return {@link #color}
         */
        public C color() {
            return color;
        }

        /**
         * Retrieves the style portion of this formatting mix.
         *
         * @return {@link #style}
         */
        public S style() {
            return style;
        }

        /**
         * Concatenates the ANSI string of both the color and style to produce the complete ANSI sequence.
         *
         * @return ANSI string representing the color and style together.
         */
        @Override
        public String toString() {
            return Color.resetAll().toString() + Style.resetAll().toString() + color.toString() + style.toString() + Color.resetAll().toString() + Style.resetAll().toString();
        }

        /**
         * Compares this {@link Formatting.Mix} to another object for equality.
         * Two mixes are equal if their {@link #color} and {@link #style} are both equal.
         *
         * @param obj The object to compare.
         * @return {@code true} if both the color and style match.
         */
        @SuppressWarnings("unchecked")
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            if (!color.equals(((Mix<?, ?>) obj).color()) || !style.equals(((Mix<?, ?>) obj).style())) return false;
            Mix<C, S> mix = (Mix<C, S>) obj;
            return equals(mix);
        }
    }

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

    /**
     * <b>Formatting.FormatObject</b> - Marker interface for ANSI formatting objects used in styled terminal output.
     * <p>This interface standardizes the contract for formatting-related types, such as color, style, or their combinations.
     * Implementations must override {@link #toString()}, {@link #equals(Object)}, and {@link #hashCode()} to ensure
     * compatibility with formatting utilities and comparison operations.</p>
     *
     * <br><b>Methods:</b>
     * <ul>
     *   <li>{@link #toString()}</li>
     *   <li>{@link #equals(Object)}</li>
     *   <li>{@link #hashCode()}</li>
     * </ul>
     *
     * <b>Usage:</b>
     * <p>All objects that are used for constructing formatting sequences must implement this interface to ensure they are
     * recognized by {@link Formatting} tools and validated during formatting operations.</p>
     */
    public interface FormatObject {
        /**
         * Returns the ANSI string representation of this formatting object.
         *
         * @return ANSI escape sequence string.
         */
        @Override
        String toString();

        /**
         * Compares this formatting object to another for equality.
         *
         * @param obj The object to compare.
         * @return {@code true} if the two formatting objects are equivalent.
         */
        @Override
        boolean equals(Object obj);

        /**
         * Computes a hash code for this formatting object.
         *
         * @return The hash code derived from the underlying ANSI string or state.
         */
        @Override
        int hashCode();
    }
}
