package com.ethanrobins.ai_npc_concept.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <b>Color</b> - An advanced ANSI-compatible color enum for text formatting.
 * <p>Includes 24-bit RGB, 8-bit color code support, gradient generation, and utility objects for foreground
 * ({@link Color.Foreground}), background ({@link Color.Background}), and mix ({@link Color.Mix}) rendering.</p>
 *
 * <br><b>Constants:</b>
 * <ul>
 *   <li>{@link Color#RESET}</li>
 *   <li>{@link Color#BLACK}, {@link Color#RED}, {@link Color#GREEN}, {@link Color#YELLOW}, {@link Color#BLUE}, {@link Color#PURPLE}, {@link Color#CYAN}, {@link Color#WHITE}</li>
 *   <li>{@link Color#BRIGHT_BLACK}, {@link Color#BRIGHT_RED}, {@link Color#BRIGHT_GREEN}, {@link Color#BRIGHT_YELLOW}, {@link Color#BRIGHT_BLUE}, {@link Color#BRIGHT_PURPLE}, {@link Color#BRIGHT_CYAN}, {@link Color#BRIGHT_WHITE}</li>
 * </ul>
 *
 * <br><b>Methods:</b>
 * <ul>
 *   <li>{@link Color#fg()}</li>
 *   <li>{@link Color#bg()}</li>
 *   <li>{@link Color#fg(Color)}, {@link Color#fg(int)}, {@link Color#fg(int, int, int)}</li>
 *   <li>{@link Color#bg(Color)}, {@link Color#bg(int)}, {@link Color#bg(int, int, int)}</li>
 *   <li>{@link Color#colorize(String, ColorObject)}</li>
 *   <li>{@link Color#gradient(String, ColorObject...)}</li>
 *   <li>{@link Color#resetAll()}</li>
 *   <li>{@link Color#toString()}</li>
 * </ul>
 *
 * <br><b>Usage:</b>
 * <pre>{@code
 * Color.Foreground fg = Color.fg(255, 100, 25); // 24-bit RGB foreground
 * Color.Background bg = Color.bg(0, 100, 255); // Background color
 * Color.Mix mix = Color.mix(fg, bg)
 * String output = Color.colorize("Hello, World!", mix);
 * }</pre>
 *
 * @see Formatting
 * @see Foreground
 * @see Background
 * @see Mix
 * @see ColorObject
 * @see ColorType
 */
public enum Color implements Formatting.Formatter {
    /**
     * Resets foreground/background to default.
     * <br>If left unspecified, it will reset both foreground and background (using {@link #toString()})
     */
    RESET(new Foreground("\u001B[39m"), new Background("\u001B[49m")),
    /**
     * Sets foreground/background to black.
     */
    BLACK(new Foreground("\u001B[30m"), new Background("\u001B[40m")),
    /**
     * Sets foreground/background to red.
     */
    RED(new Foreground("\u001B[31m"), new Background("\u001B[41m")),
    /**
     * Sets foreground/background to green.
     */
    GREEN(new Foreground("\u001B[32m"), new Background("\u001B[42m")),
    /**
     * Sets foreground/background to yellow.
     */
    YELLOW(new Foreground("\u001B[33m"), new Background("\u001B[43m")),
    /**
     * Sets foreground/background to blue.
     */
    BLUE(new Foreground("\u001B[34m"), new Background("\u001B[44m")),
    /**
     * Sets foreground/background to purple.
     */
    PURPLE(new Foreground("\u001B[35m"), new Background("\u001B[45m")),
    /**
     * Sets foreground/background to cyan.
     */
    CYAN(new Foreground("\u001B[36m"), new Background("\u001B[46m")),
    /**
     * Sets foreground/background to white.
     */
    WHITE(new Foreground("\u001B[37m"), new Background("\u001B[47m")),
    /**
     * Sets foreground/background to bright black (gray).
     */
    BRIGHT_BLACK(new Foreground("\u001B[90m"), new Background("\u001B[100m")),
    /**
     * Sets foreground/background to bright red.
     */
    BRIGHT_RED(new Foreground("\u001B[91m"), new Background("\u001B[101m")),
    /**
     * Sets foreground/background to bright green.
     */
    BRIGHT_GREEN(new Foreground("\u001B[92m"), new Background("\u001B[102m")),
    /**
     * Sets foreground/background to bright yellow.
     */
    BRIGHT_YELLOW(new Foreground("\u001B[93m"), new Background("\u001B[103m")),
    /**
     * Sets foreground/background to bright blue.
     */
    BRIGHT_BLUE(new Foreground("\u001B[94m"), new Background("\u001B[104m")),
    /**
     * Sets foreground/background to bright purple.
     */
    BRIGHT_PURPLE(new Foreground("\u001B[95m"), new Background("\u001B[105m")),
    /**
     * Sets foreground/background to bright cyan.
     */
    BRIGHT_CYAN(new Foreground("\u001B[96m"), new Background("\u001B[106m")),
    /**
     * Sets foreground/background to bright white.
     */
    BRIGHT_WHITE(new Foreground("\u001B[97m"), new Background("\u001B[107m"));

    /**
     * The {@link Foreground} object for this color.
     */
    private final Foreground foreground;
    /**
     * The {@link Background} object for this color.
     */
    private final Background background;

    /**
     * Constructs a {@link Color} enum constant with predefined foreground and background ANSI sequences.
     *
     * @param foreground {@link #foreground}
     * @param background {@link #background}
     */
    Color(Foreground foreground, Background background) {
        this.foreground = foreground;
        this.background = background;
    }

    /**
     * Returns the {@link Foreground} object for this color.
     *
     * @return {@link #foreground}
     */
    public Foreground fg() {
        return this.foreground;
    }
    /**
     * Returns the {@link Foreground} of the provided color constant.
     *
     * @param color {@link Color} constant.
     * @return The {@link Color}'s {@link Foreground} object.
     */
    public static Foreground fg(@NotNull Color color) {
        return color.fg();
    }
    /**
     * Returns the {@link Foreground} of the provided 8-bit color code.
     *
     * @param colorCode An integer from {@code 0} to {@code 255}.
     * @return A {@link Foreground} with the ANSI 8-bit color.
     * @throws IllegalArgumentException if the value is out of range.
     */
    public static Foreground fg(int colorCode) {
        return Foreground.fromEightBit(colorCode);
    }
    /**
     * Returns the ANSI 24-bit RGB foreground color code.
     *
     * @param r Red {@code 0-255}
     * @param g Green {@code 0-255}
     * @param b Blue {@code 0-255}
     * @return ANSI escape sequence for 24-bit color.
     * @throws IllegalArgumentException if any value is out of range.
     */
    public static Foreground fg(int r, int g, int b) {
        return Foreground.fromRGB(r, g, b);
    }

    /**
     * Returns the {@link Background} object for this color.
     *
     * @return {@link #background}
     */
    public Background bg() {
        return this.background;
    }
    /**
     * Returns the {@link Background} of the provided color constant.
     *
     * @param color {@link Color} constant.
     * @return The {@link Color}'s {@link Background} object.
     */
    public static Background bg(@NotNull Color color) {
        return color.bg();
    }
    /**
     * Returns the {@link Background} of the provided 8-bit color code.
     *
     * @param colorCode An integer from {@code 0} to {@code 255}.
     * @return A {@link Background} with the ANSI 8-bit color.
     * @throws IllegalArgumentException if the value is out of range.
     */
    public static Background bg(int colorCode) {
        return Background.fromEightBit(colorCode);
    }
    /**
     * Returns the {@link Background} of the provided 8-bit color code.
     *
     * @param r Red {@code 0-255}
     * @param g Green {@code 0-255}
     * @param b Blue {@code 0-255}
     * @return A {@link Background} with the ANSI 24-bit color.
     * @throws IllegalArgumentException if any value is out of range.
     */
    public static Background bg(int r, int g, int b) {
        return Background.fromRGB(r, g, b);
    }

    /**
     * A {@link Mix} of the {@link #RESET} constants that combines the {@link Foreground} and {@link Background} ANSI escape sequences.
     *
     * @return {@link Mix} of {@link #RESET}.
     */
    public static Mix resetAll() {
        return new Mix(RESET.fg(), RESET.bg());
    }

    /**
     * Combines this color into a {@link Mix} using both {@link Foreground} and {@link Background} ANSI codes.
     *
     * @return {@link Mix} representation.
     */
    public static Mix mix(Foreground foreground, Background background) {
        return new Mix(foreground, background);
    }

    /**
     * Wraps a string with {@link ColorObject}s and resets formatting at the end.
     *
     * @param text The text to colorize.
     * @param colorObject The {@link ColorObject} to apply.
     * @return The colorized string.
     */
    public static String colorize(@NotNull String text, @NotNull ColorObject colorObject) {
        StringBuilder sb = new StringBuilder();
        sb.append(resetAll());
        sb.append(colorObject);
        sb.append(text);
        sb.append(resetAll());

        return sb.toString();
    }

    /**
     * Generates a text gradient by applying interpolated {@link ColorObject} values ({@link Foreground}, {@link Background}, or {@link Mix})
     * across the provided string.
     *
     * @param text The text to apply the gradient to. Cannot be {@code null} or empty.
     * @param rightToLeft An ordered array of {@link ColorObject} used to interpolate between colors. Must contain at least 2 colors.
     * @return A string with ANSI escape sequences applied as a gradient over the input text.
     *
     * @throws IllegalArgumentException If {@code text} is {@code null} or empty, fewer than two color objects are provided,
     *                                  or any {@link ColorObject} does not support 24-bit color.
     * @see #interpolateChannel(int, int, float) 
     */
    public static String gradient(@Nullable String text, @NotNull ColorObject... rightToLeft) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Text cannot be null or empty.");
        }
        if (rightToLeft == null || rightToLeft.length < 2) {
            throw new IllegalArgumentException("At least two colors are required to create a gradient.");
        }
        for (ColorObject co : rightToLeft) {
            switch (co) {
                case Background bg -> {
                    if (bg.type() != ColorType.TWENTY_FOUR_BIT) {
                        throw new IllegalArgumentException("24-bit colors are required for gradients.");
                    }
                }
                case Foreground fg -> {
                    if (fg.type() != ColorType.TWENTY_FOUR_BIT) {
                        throw new IllegalArgumentException("24-bit colors are required for gradients.");
                    }
                }
                case Mix m -> {
                    if (m.foreground().type() != ColorType.TWENTY_FOUR_BIT || m.background().type() != ColorType.TWENTY_FOUR_BIT) {
                        throw new IllegalArgumentException("24-bit colors are required for gradients.");
                    }
                }
                default -> throw new IllegalArgumentException("Invalid color object type: " + co.getClass().getSimpleName());
            }
        }

        int totalChars = text.length();
        int numSections = rightToLeft.length - 1;
        int charsPerSection = Math.max(1, totalChars / numSections);

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < totalChars; i++) {
            int section = Math.min(i / charsPerSection, numSections - 1);
            ColorObject start = rightToLeft[section];
            ColorObject end = rightToLeft[section + 1];

            float progress = (i % charsPerSection) / (float) charsPerSection;

            sb.append(Color.colorize(String.valueOf(text.charAt(i)), interpolateColor(start, end, progress)));
        }

        sb.append(resetAll());
        return sb.toString();
    }

    /**
     * Interpolates between two {@link ColorObject} values to produce a mid-point color based on progress.
     *
     * @param start The starting {@link ColorObject}.
     * @param end The ending {@link ColorObject}.
     * @param progress A value between 0.0 and 1.0 representing the interpolation point.
     * @return A new {@link ColorObject} representing the interpolated color.
     *
     * @throws IllegalArgumentException If the types of {@code start} and {@code end} are incompatible,
     *                                  or if either is {@code null}.
     * @see #interpolateChannel(int, int, float) 
     */
    private static ColorObject interpolateColor(ColorObject start, ColorObject end, float progress) {
        switch (start) {
            case Foreground fgStart when end instanceof Foreground fgEnd -> {
                int r = interpolateChannel(fgStart.red(), fgEnd.red(), progress);
                int g = interpolateChannel(fgStart.green(), fgEnd.green(), progress);
                int b = interpolateChannel(fgStart.blue(), fgEnd.blue(), progress);
                return Foreground.fromRGB(r, g, b);
            }
            case Background bgStart when end instanceof Background bgEnd -> {
                int r = interpolateChannel(bgStart.red(), bgEnd.red(), progress);
                int g = interpolateChannel(bgStart.green(), bgEnd.green(), progress);
                int b = interpolateChannel(bgStart.blue(), bgEnd.blue(), progress);
                return Background.fromRGB(r, g, b);
            }
            case Mix mStart when end instanceof Mix mEnd -> {
                int fgr = interpolateChannel(mStart.foreground().red(), mEnd.foreground().red(), progress);
                int fgg = interpolateChannel(mStart.foreground().green(), mEnd.foreground().green(), progress);
                int fgb = interpolateChannel(mStart.foreground().blue(), mEnd.foreground().blue(), progress);
                Foreground fg = Foreground.fromRGB(fgr, fgg, fgb);
                int bgr = interpolateChannel(mStart.background().red(), mEnd.background().red(), progress);
                int bgg = interpolateChannel(mStart.background().green(), mEnd.background().green(), progress);
                int bgb = interpolateChannel(mStart.background().blue(), mEnd.background().blue(), progress);
                Background bg = Background.fromRGB(bgr, bgg, bgb);
                return new Mix(fg, bg);
            }
            case null, default ->
                    throw new IllegalArgumentException("Invalid color object type: " + start.getClass().getSimpleName());
        }
    }

    /**
     * Linearly interpolates a single RGB color channel from a start to an end value.
     *
     * @param startChannel The starting value (0–255).
     * @param endChannel The ending value (0–255).
     * @param progress A float from 0.0 to 1.0 indicating interpolation progress.
     * @return The interpolated channel value as an integer.
     */
    private static int interpolateChannel(int startChannel, int endChannel, float progress) {
        return (int) (startChannel + progress * (endChannel - startChannel));
    }

    /**
     * Returns the ANSI escape code for the foreground color of this constant.
     *
     * @return {@link Foreground#toString()} of this color.
     */
    @Override
    public String toString() {
        return this == RESET ? resetAll().toString() : this.fg().toString();
    }

    /**
     * <b>Color.Foreground</b> - Represents an ANSI-compatible foreground (text) color.
     * <p>This class implements {@link ColorObject} and {@link Formatting.FormatObject} to support formatted output using
     * ANSI escape sequences. It supports standard, 8-bit, and 24-bit (true color) foreground colors.</p>
     *
     * <br><b>Implements:</b>
     * <ul>
     *   <li>{@link ColorObject}</li>
     *   <li>{@link Formatting.FormatObject}</li>
     * </ul>
     *
     * <br><b>Static Methods:</b>
     * <ul>
     *   <li>{@link Foreground#fromEightBit(int)}</li>
     *   <li>{@link Foreground#fromRGB(int, int, int)}</li>
     * </ul>
     *
     * <br><b>Instance Methods:</b>
     * <ul>
     *   <li>{@link Foreground#type()}</li>
     *   <li>{@link Foreground#red()}</li>
     *   <li>{@link Foreground#green()}</li>
     *   <li>{@link Foreground#blue()}</li>
     *   <li>{@link Foreground#toString()}</li>
     *   <li>{@link Foreground#equals(Object)}</li>
     *   <li>{@link Foreground#hashCode()}</li>
     * </ul>
     *
     * <b>Usage:</b>
     * <pre>{@code
     * Foreground fg = Foreground.fromRGB(255, 100, 50);
     * System.out.println(fg + "Hello, World!" + Color.RESET);
     * }</pre>
     *
     * @see Color
     */
    public static class Foreground implements ColorObject, Formatting.FormatObject {
        /**
         * The raw ANSI escape sequence for this foreground color.
         */
        private final String ansi;
        /**
         * Regular expression pattern that validates ANSI 4-bit, 8-bit, and 24-bit foreground color codes.
         * <p>This pattern is used during construction to ensure only valid ANSI color strings are accepted.</p>
         */
        private static final String PATTERN = "\\u001B\\[(39|[39][0-7]|38;[25];(\\d{1,3}|\\d{1,3};\\d{1,3};\\d{1,3}))m";

        /**
         * Constructs a {@link Foreground} color from a raw ANSI sequence.
         *
         * @param ansi The ANSI escape code string representing a valid foreground color.
         * @throws IllegalArgumentException If the ANSI string is {@code null} or does not match a valid foreground pattern.
         */
        public Foreground(String ansi) {
            if (ansi == null || !ansi.matches(PATTERN)) {
                throw new IllegalArgumentException("Invalid ANSI foreground format: " + ansi);
            }
            this.ansi = ansi;
        }

        /**
         * Determines the {@link ColorType} of this foreground color (e.g., standard, 8-bit, or 24-bit).
         *
         * @return A {@link ColorType} enum representing the format of this foreground.
         */
        public ColorType type() {
            return ColorType.evalGet(this.ansi);
        }

        /**
         * Returns the red component of a 24-bit foreground color.
         *
         * @return Red value (0–255).
         * @throws IllegalStateException If this color is not 24-bit.
         */
        public int red() {
            if (this.type() != ColorType.TWENTY_FOUR_BIT) {
                throw new IllegalStateException("This color is not a 24-bit color.");
            }
            return this.extractRGBComponent(1);
        }

        /**
         * Returns the green component of a 24-bit foreground color.
         *
         * @return Green value (0–255).
         * @throws IllegalStateException If this color is not 24-bit.
         */
        public int green() {
            if (this.type() != ColorType.TWENTY_FOUR_BIT) {
                throw new IllegalStateException("This color is not a 24-bit color.");
            }
            return this.extractRGBComponent(2);
        }

        /**
         * Returns the blue component of a 24-bit foreground color.
         *
         * @return Blue value (0–255).
         * @throws IllegalStateException If this color is not 24-bit.
         */
        public int blue() {
            if (this.type() != ColorType.TWENTY_FOUR_BIT) {
                throw new IllegalStateException("This color is not a 24-bit color.");
            }
            return this.extractRGBComponent(3);
        }

        /**
         * Creates a {@link Foreground} from an 8-bit ANSI color code.
         *
         * @param colorCode A value from 0 to 255.
         * @return A {@link Foreground} representing the 8-bit color.
         * @throws IllegalArgumentException If the color code is out of range.
         */
        public static Foreground fromEightBit(int colorCode) {
            if (colorCode < 0 || colorCode > 255) {
                throw new IllegalArgumentException("8-bit color code must be between 0 and 255.");
            }
            return new Foreground("\u001B[38;5;" + colorCode + "m");
        }

        /**
         * Creates a {@link Foreground} from RGB values (24-bit true color).
         *
         * @param r Red value (0–255)
         * @param g Green value (0–255)
         * @param b Blue value (0–255)
         * @return A {@link Foreground} representing the color.
         * @throws IllegalArgumentException If any RGB component is out of range.
         */
        public static Foreground fromRGB(int r, int g, int b) {
            if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
                throw new IllegalArgumentException("RGB values must be between 0 and 255.");
            }
            return new Foreground("\u001B[38;2;" + r + ";" + g + ";" + b + "m");
        }

        /**
         * Internal utility for extracting an RGB component from a 24-bit ANSI foreground sequence.
         *
         * @param groupIndex The regex capture group index (1=R, 2=G, 3=B).
         * @return Integer value of the RGB component.
         * @throws IllegalStateException If the ANSI format is invalid or not 24-bit.
         */
        private int extractRGBComponent(int groupIndex) {
            String pattern = "\\u001B\\[38;2;(\\d{1,3});(\\d{1,3});(\\d{1,3})m";
            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(this.ansi);

            if (!matcher.matches()) {
                throw new IllegalStateException("Invalid ANSI foreground format: " + this.ansi);
            }

            return Integer.parseInt(matcher.group(groupIndex));
        }

        /**
         * Returns the ANSI escape sequence as a string.
         *
         * @return {@link Foreground#ansi}
         */
        @Override
        public String toString() {
            return this.ansi;
        }

        /**
         * Compares this {@link Foreground} to another object for equality.
         * Two foregrounds are equal if their ANSI codes match.
         *
         * @param obj Another object.
         * @return {@code true} if the ANSI codes are equal.
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Foreground that = (Foreground) obj;
            return this.ansi.equals(that.ansi);
        }

        /**
         * Returns the hash code based on the ANSI escape code.
         *
         * @return hash code.
         */
        @Override
        public int hashCode() {
            return this.toString().hashCode();
        }
    }

    /**
     * <b>Color.Background</b> - Represents an ANSI-compatible background color.
     * <p>This class implements {@link ColorObject} and {@link Formatting.FormatObject} to support formatted output using
     * ANSI escape sequences. It supports standard, 8-bit, and 24-bit (true color) background colors.</p>
     *
     * <br><b>Implements:</b>
     * <ul>
     *   <li>{@link ColorObject}</li>
     *   <li>{@link Formatting.FormatObject}</li>
     * </ul>
     *
     * <br><b>Static Methods:</b>
     * <ul>
     *   <li>{@link Background#fromEightBit(int)}</li>
     *   <li>{@link Background#fromRGB(int, int, int)}</li>
     * </ul>
     *
     * <br><b>Instance Methods:</b>
     * <ul>
     *   <li>{@link Background#type()}</li>
     *   <li>{@link Background#red()}</li>
     *   <li>{@link Background#green()}</li>
     *   <li>{@link Background#blue()}</li>
     *   <li>{@link Background#toString()}</li>
     *   <li>{@link Background#equals(Object)}</li>
     *   <li>{@link Background#hashCode()}</li>
     * </ul>
     *
     * <b>Usage:</b>
     * <pre>{@code
     * Background bg = Background.fromRGB(255, 100, 50);
     * System.out.println(Color.colorize("Hello, World!", bg));
     * }</pre>
     *
     * @see Color
     */
    public static class Background implements ColorObject, Formatting.FormatObject {
        /**
         * The raw ANSI escape sequence for this background color.
         */
        private final String ansi;
        /**
         * Regular expression pattern that validates ANSI 4-bit, 8-bit, and 24-bit background color codes.
         * <p>This pattern is used during construction to ensure only valid ANSI color strings are accepted.</p>
         */
        private static final String PATTERN = "\\u001B\\[(49|(4|10)[0-7]|48;[25];(\\d{1,3}|\\d{1,3};\\d{1,3};\\d{1,3}))m";

        /**
         * Constructs a {@link Background} color from a raw ANSI sequence.
         *
         * @param ansi The ANSI escape code string representing a valid background color.
         * @throws IllegalArgumentException If the ANSI string is {@code null} or does not match a valid background pattern.
         */
        public Background(String ansi) {
            if (ansi == null || !ansi.matches(PATTERN)) {
                throw new IllegalArgumentException("Invalid ANSI background format: " + ansi);
            }
            this.ansi = ansi;
        }

        /**
         * Determines the {@link ColorType} of this background color (e.g., standard, 8-bit, or 24-bit).
         *
         * @return A {@link ColorType} enum representing the format of this background.
         */
        public ColorType type() {
            return ColorType.evalGet(this.ansi);
        }

        /**
         * Returns the red component of a 24-bit background color.
         *
         * @return Red value (0–255).
         * @throws IllegalStateException If this color is not 24-bit.
         */
        public int red() {
            if (this.type() != ColorType.TWENTY_FOUR_BIT) {
                throw new IllegalStateException("This color is not a 24-bit color.");
            }
            return this.extractRGBComponent(1);
        }

        /**
         * Returns the green component of a 24-bit background color.
         *
         * @return Green value (0–255).
         * @throws IllegalStateException If this color is not 24-bit.
         */
        public int green() {
            if (this.type() != ColorType.TWENTY_FOUR_BIT) {
                throw new IllegalStateException("This color is not a 24-bit color.");
            }
            return this.extractRGBComponent(2);
        }

        /**
         * Returns the blue component of a 24-bit background color.
         *
         * @return Blue value (0–255).
         * @throws IllegalStateException If this color is not 24-bit.
         */
        public int blue() {
            if (this.type() != ColorType.TWENTY_FOUR_BIT) {
                throw new IllegalStateException("This color is not a 24-bit color.");
            }
            return this.extractRGBComponent(3);
        }

        /**
         * Creates a {@link Background} from an 8-bit ANSI color code.
         *
         * @param colorCode A value from 0 to 255.
         * @return A {@link Background} representing the 8-bit color.
         * @throws IllegalArgumentException If the color code is out of range.
         */
        public static Background fromEightBit(int colorCode) {
            if (colorCode < 0 || colorCode > 255) {
                throw new IllegalArgumentException("8-bit color code must be between 0 and 255.");
            }
            return new Background("\u001B[48;5;" + colorCode + "m");
        }

        /**
         * Creates a {@link Background} from RGB values (24-bit true color).
         *
         * @param r Red value (0–255)
         * @param g Green value (0–255)
         * @param b Blue value (0–255)
         * @return A {@link Background} representing the color.
         * @throws IllegalArgumentException If any RGB component is out of range.
         */
        public static Background fromRGB(int r, int g, int b) {
            if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
                throw new IllegalArgumentException("RGB values must be between 0 and 255.");
            }
            return new Background("\u001B[48;2;" + r + ";" + g + ";" + b + "m");
        }

        /**
         * Internal utility for extracting an RGB component from a 24-bit ANSI background sequence.
         *
         * @param groupIndex The regex capture group index (1=R, 2=G, 3=B).
         * @return Integer value of the RGB component.
         * @throws IllegalStateException If the ANSI format is invalid or not 24-bit.
         */
        private int extractRGBComponent(int groupIndex) {
            String pattern = "\\u001B\\[48;2;(\\d{1,3});(\\d{1,3});(\\d{1,3})m";
            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(this.ansi);

            if (!matcher.matches()) {
                throw new IllegalStateException("Invalid ANSI background format: " + this.ansi);
            }

            return Integer.parseInt(matcher.group(groupIndex));
        }

        /**
         * Returns the ANSI escape sequence as a string.
         *
         * @return {@link Background#ansi}
         */
        @Override
        public String toString() {
            return this.ansi;
        }

        /**
         * Compares this {@link Background} to another object for equality.
         * Two backgrounds are equal if their ANSI codes match.
         *
         * @param obj Another object.
         * @return {@code true} if the ANSI codes are equal.
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Background that = (Background) obj;
            return this.ansi.equals(that.ansi);
        }

        /**
         * Returns the hash code based on the ANSI escape code.
         *
         * @return hash code.
         */
        @Override
        public int hashCode() {
            return this.toString().hashCode();
        }
    }

    /**
     * <b>Color.Mix</b> - Represents a combined ANSI foreground and background color.
     * <p>This class implements both {@link ColorObject} and {@link Formatting.FormatObject} to support styled output using
     * a mix of foreground and background colors. Each instance wraps a {@link Foreground} and {@link Background} object.</p>
     *
     * <br><b>Implements:</b>
     * <ul>
     *   <li>{@link ColorObject}</li>
     *   <li>{@link Formatting.FormatObject}</li>
     * </ul>
     *
     * <br><b>Fields:</b>
     * <ul>
     *   <li>{@link Mix#foreground}</li>
     *   <li>{@link Mix#background}</li>
     * </ul>
     *
     * <br><b>Methods:</b>
     * <ul>
     *   <li>{@link Mix#foreground()}</li>
     *   <li>{@link Mix#background()}</li>
     *   <li>{@link Mix#toString()}</li>
     *   <li>{@link Mix#equals(Object)}</li>
     *   <li>{@link Mix#hashCode()}</li>
     * </ul>
     *
     * <b>Usage:</b>
     * <pre>{@code
     * Foreground fg = Foreground.fromRGB(255, 255, 0);
     * Background bg = Background.fromRGB(30, 30, 30);
     * Mix mix = new Mix(fg, bg);
     * System.out.println(Color.colorize("Warning message", mix));
     * }</pre>
     *
     * @see Color
     * @see Color.Foreground
     * @see Color.Background
     */
    public static class Mix implements ColorObject, Formatting.FormatObject {
        /**
         * The foreground color applied in this mix.
         */
        private final Foreground foreground;
        /**
         * The background color applied in this mix.
         */
        private final Background background;

        /**
         * Constructs a {@link Mix} from the given {@link Foreground} and {@link Background}.
         *
         * @param foreground The foreground color to apply.
         * @param background The background color to apply.
         */
        public Mix(Foreground foreground, Background background) {
            this.foreground = foreground;
            this.background = background;
        }

        /**
         * Retrieves the {@link Foreground} part of this mix.
         *
         * @return {@link #foreground}
         */
        public Foreground foreground() {
            return this.foreground;
        }

        /**
         * Retrieves the {@link Background} part of this mix.
         *
         * @return {@link #background}
         */
        public Background background() {
            return this.background;
        }

        /**
         * Returns the combined ANSI string for both foreground and background colors.
         *
         * @return Concatenated {@link Foreground#toString()} and {@link Background#toString()}.
         */
        @Override
        public String toString() {
            return this.foreground.toString() + this.background.toString();
        }

        /**
         * Compares this {@link Mix} with another object for equality.
         * Two mixes are equal if their foreground and background components are equal.
         *
         * @param obj The object to compare with.
         * @return {@code true} if both mixes have the same foreground and background.
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Mix that = (Mix) obj;
            return this.foreground.equals(that.foreground) && this.background.equals(that.background);
        }

        /**
         * Returns the hash code based on the combined ANSI sequences of foreground and background.
         *
         * @return Combined hash code of both color layers.
         */
        @Override
        public int hashCode() {
            return this.toString().hashCode();
        }
    }

    /**
     * <b>Color.ColorObject</b> - Represents a typed ANSI color formatting object.
     * <p>This interface is implemented by all typed color components including
     * {@link Color.Foreground}, {@link Color.Background}, and {@link Color.Mix}. It defines a contract for
     * ANSI-compatible color objects used in styled terminal output.</p>
     *
     * <br><b>Methods:</b>
     * <ul>
     *   <li>{@link ColorObject#toString()}</li>
     *   <li>{@link ColorObject#equals(Object)}</li>
     *   <li>{@link ColorObject#hashCode()}</li>
     * </ul>
     *
     * <b>Usage:</b>
     * <p>Use {@code ColorObject} as a common type to handle foreground, background, or mixed ANSI color formats.
     * This allows gradient rendering and color utilities to generically process color layers.</p>
     *
     * @see Color
     */
    public interface ColorObject {
        /**
         * Returns the ANSI escape sequence that represents this color.
         *
         * @return A valid ANSI color sequence.
         */
        @Override
        String toString();

        /**
         * Checks whether this color object is equal to another.
         *
         * @param obj Another {@link Object} to compare.
         * @return {@code true} if both objects represent the same ANSI color sequence.
         */
        @Override
        boolean equals(Object obj);

        /**
         * Returns the hash code of this color object.
         *
         * @return Hash code derived from the ANSI sequence.
         */
        @Override
        int hashCode();
    }

    /**
     * <b>Color.ColorType</b> - Represents different ANSI color encoding types.
     * <p>Used to classify a {@link ColorObject} based on its ANSI escape sequence format. This includes support for
     * standard (normal), bright, 8-bit, and 24-bit color modes.</p>
     *
     * <br><b>Enum Constants:</b>
     * <ul>
     *   <li>{@link ColorType#RESET}</li>
     *   <li>{@link ColorType#NORMAL}</li>
     *   <li>{@link ColorType#BRIGHT}</li>
     *   <li>{@link ColorType#EIGHT_BIT}</li>
     *   <li>{@link ColorType#TWENTY_FOUR_BIT}</li>
     * </ul>
     *
     * <br><b>Methods:</b>
     * <ul>
     *   <li>{@link ColorType#pattern()}</li>
     *   <li>{@link ColorType#eval(String)}</li>
     *   <li>{@link ColorType#evalGet(String)}</li>
     *   <li>{@link ColorType#toString()}</li>
     * </ul>
     *
     * <b>Usage:</b>
     * <p>Used internally by {@link Color.Foreground}, {@link Color.Background}, and {@link Color.Mix}
     * to determine whether a color sequence represents 24-bit, 8-bit, or another ANSI color type.</p>
     *
     * @see Color
     */
    public enum ColorType {
        /**
         * Matches ANSI reset sequences for color and formatting.
         * Example: {@code \u001B[0m}, {@code \u001B[39m}, {@code \u001B[49m}
         */
        RESET("\\u001B\\[(0|[34]9)m"),
        /**
         * Matches standard 4-bit ANSI color codes.
         * Example: {@code \u001B[31m}, {@code \u001B[44m}
         */
        NORMAL("\\u001B\\[(3|4)[0-7]m"),
        /**
         * Matches bright 4-bit ANSI color codes.
         * Example: {@code \u001B[91m}, {@code \u001B[107m}
         */
        BRIGHT("\\u001B\\[(9|10)[0-7]m"),
        /**
         * Matches 8-bit indexed ANSI color codes.
         * Example: {@code \u001B[38;5;123m}, {@code \u001B[48;5;200m}
         */
        EIGHT_BIT("\\u001B\\[(3|4)8;5;\\d{1,3}m"),
        /**
         * Matches 24-bit true color ANSI sequences.
         * Example: {@code \u001B[38;2;255;100;0m}, {@code \u001B[48;2;20;20;20m}
         */
        TWENTY_FOUR_BIT("\\u001B\\[(3|4)8;2;\\d{1,3};\\d{1,3};\\d{1,3}m");

        /**
         * The regex pattern used to match ANSI escape sequences of this {@link ColorType}.
         */
        private final String pattern;

        /**
         * Constructs a {@link ColorType} with a given regex pattern.
         *
         * @param pattern The regex pattern to match against ANSI color strings.
         */
        ColorType(String pattern) {
            this.pattern = pattern;
        }

        /**
         * Returns the regex pattern used to identify this {@link ColorType}.
         *
         * @return {@link #pattern}
         */
        public String pattern() {
            return this.pattern;
        }

        /**
         * Checks whether the given ANSI escape sequence matches this {@link ColorType}.
         *
         * @param ansi The ANSI string to evaluate.
         * @return {@code true} if the ANSI string matches this type’s pattern.
         */
        public boolean eval(String ansi) {
            return ansi != null && ansi.matches(this.pattern());
        }

        /**
         * Determines the appropriate {@link ColorType} for the given ANSI string.
         *
         * @param ansi The ANSI escape sequence to evaluate.
         * @return The matching {@link ColorType}.
         * @throws IllegalArgumentException If no match is found.
         */
        public static ColorType evalGet(String ansi) {
            for (ColorType t : ColorType.values()) {
                if (ansi != null && ansi.matches(t.pattern())) {
                    return t;
                }
            }
            throw new IllegalArgumentException("Invalid ANSI color format: " + ansi);
        }

        /**
         * Returns the enum constant name for this {@link ColorType}.
         *
         * @return This enum’s name as a string.
         */
        @Override
        public String toString() {
            return this.name();
        }
    }
}
