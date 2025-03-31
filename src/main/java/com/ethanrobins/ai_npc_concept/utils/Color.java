package com.ethanrobins.ai_npc_concept.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <b>Color</b> - ANSI-compatible color enumeration for terminal text formatting.
 * <p>This enum implements {@link Formatting.Formatter} and provides foreground and background color codes using
 * ANSI escape sequences, including support for basic, bright, 8-bit, and 24-bit RGB color formatting.</p>
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
 * <b>Usage:</b>
 * <p>Use this enum to colorize terminal output by combining foreground and background codes with strings.
 * Includes convenience methods for 8-bit and 24-bit color customization.</p>
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
     * The ANSI escape code for the foreground color.
     */
    private final Foreground foreground;
    /**
     * The ANSI escape code for the background color.
     */
    private final Background background;

    /**
     * Constructs a {@link Color} constant with foreground and background escape codes.
     *
     * @param foreground The ANSI foreground code.
     * @param background The ANSI background code.
     */
    Color(Foreground foreground, Background background) {
        this.foreground = foreground;
        this.background = background;
    }

    /**
     * Returns the ANSI foreground color code for this color.
     *
     * @return {@link #foreground}
     */
    public ColorObject fg() {
        return this.foreground;
    }
    /**
     * Returns the ANSI foreground color code of the given {@link Color}.
     *
     * @param color The {@link Color} to use.
     * @return {@link #foreground}
     */
    public static ColorObject fg(@NotNull Color color) {
        return color.fg();
    }
    /**
     * Returns the ANSI 8-bit foreground color code for a given color code.
     *
     * @param colorCode An integer from {@code 0} to {@code 255}.
     * @return ANSI escape sequence for 8-bit color.
     * @throws IllegalArgumentException if the value is out of range.
     */
    public static ColorObject fg(int colorCode) {
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
    public static ColorObject fg(int r, int g, int b) {
        return Foreground.fromRGB(r, g, b);
    }

    /**
     * Returns the ANSI background color code for this color.
     *
     * @return {@link #background}
     */
    public ColorObject bg() {
        return this.background;
    }
    /**
     * Returns the ANSI background color code of the given {@link Color}.
     *
     * @param color The {@link Color} to use.
     * @return {@link #background}
     */
    public static ColorObject bg(@NotNull Color color) {
        return color.bg();
    }
    /**
     * Returns the ANSI 8-bit background color code for a given color code.
     *
     * @param colorCode An integer from {@code 0} to {@code 255}.
     * @return ANSI escape sequence for 8-bit color.
     * @throws IllegalArgumentException if the value is out of range.
     */
    public static ColorObject bg(int colorCode) {
        return Background.fromEightBit(colorCode);
    }
    /**
     * Returns the ANSI 24-bit RGB background color code.
     *
     * @param r Red {@code 0-255}
     * @param g Green {@code 0-255}
     * @param b Blue {@code 0-255}
     * @return ANSI escape sequence for 24-bit color.
     * @throws IllegalArgumentException if any value is out of range.
     */
    public static ColorObject bg(int r, int g, int b) {
        return Background.fromRGB(r, g, b);
    }

    /**
     * Returns the ANSI escape code that resets all formatting and colors.
     *
     * @return ANSI reset sequence.
     */
    public static ColorObject resetAll() {
        return new Mix((Foreground) RESET.fg(), (Background) RESET.bg());
    }

    public static ColorObject mix(Foreground foreground, Background background) {
        return new Mix(foreground, background);
    }

    /**
     * Wraps a string with ANSI color codes and resets formatting at both ends.
     *
     * @param text The text to colorize.
     * @param ansi One or more ANSI formatting codes to apply.
     * @return The colorized string.
     */
    public static String colorize(@NotNull String text, @NotNull ColorObject ansi) {
        StringBuilder sb = new StringBuilder();
        sb.append(resetAll());
        sb.append(ansi);
        sb.append(text);
        sb.append(resetAll());

        return sb.toString();
    }

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

    private static int interpolateChannel(int startChannel, int endChannel, float progress) {
        return (int) (startChannel + progress * (endChannel - startChannel));
    }

    /**
     * Returns the default ANSI color representation of this enum.
     * <p>Returns {@link #resetAll()} if this is {@link Color#RESET}, otherwise returns {@link #fg()}.</p>
     *
     * @return ANSI color code
     */
    @Override
    public String toString() {
        return this == RESET ? resetAll().toString() : this.fg().toString();
    }

    public static class Foreground implements ColorObject {
        private final String ansi;
        private static final String PATTERN = "\\u001B\\[39m|\\u001B\\[3[0-7]m|\\u001B\\[9[0-7]m|\\u001B\\[38;5;\\d{1,3}m|\\u001B\\[38;2;\\d{1,3};\\d{1,3};\\d{1,3}m";

        public Foreground(String ansi) {
            if (ansi == null || !ansi.matches(PATTERN)) {
                throw new IllegalArgumentException("Invalid ANSI foreground format: " + ansi);
            }
            this.ansi = ansi;
        }

        public ColorType type() {
            return ColorType.evalGet(this.ansi);
        }

        public int red() {
            if (this.type() != ColorType.TWENTY_FOUR_BIT) {
                throw new IllegalStateException("This color is not a 24-bit color.");
            }
            return this.extractRGBComponent(1);
        }

        public int green() {
            if (this.type() != ColorType.TWENTY_FOUR_BIT) {
                throw new IllegalStateException("This color is not a 24-bit color.");
            }
            return this.extractRGBComponent(2);
        }

        public int blue() {
            if (this.type() != ColorType.TWENTY_FOUR_BIT) {
                throw new IllegalStateException("This color is not a 24-bit color.");
            }
            return this.extractRGBComponent(3);
        }

        public static Foreground fromEightBit(int colorCode) {
            if (colorCode < 0 || colorCode > 255) {
                throw new IllegalArgumentException("8-bit color code must be between 0 and 255.");
            }
            return new Foreground("\u001B[38;5;" + colorCode + "m");
        }

        public static Foreground fromRGB(int r, int g, int b) {
            if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
                throw new IllegalArgumentException("RGB values must be between 0 and 255.");
            }
            return new Foreground("\u001B[38;2;" + r + ";" + g + ";" + b + "m");
        }

        private int extractRGBComponent(int groupIndex) {
            String pattern = "\\u001B\\[38;2;(\\d{1,3});(\\d{1,3});(\\d{1,3})m";
            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(this.ansi);

            if (!matcher.matches()) {
                throw new IllegalStateException("Invalid ANSI foreground format: " + this.ansi);
            }

            return Integer.parseInt(matcher.group(groupIndex));

        }

        @Override
        public String toString() {
            return this.ansi;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Foreground that = (Foreground) obj;
            return this.ansi.equals(that.ansi);
        }

        @Override
        public int hashCode() {
            return this.toString().hashCode();
        }
    }

    public static class Background implements ColorObject {
        private final String ansi;
        private static final String PATTERN = "\\u001B\\[49m|\\u001B\\[4[0-7]m|\\u001B\\[10[0-7]m|\\u001B\\[48;5;\\d{1,3}m|\\u001B\\[48;2;\\d{1,3};\\d{1,3};\\d{1,3}m";

        public Background(String ansi) {
            if (ansi == null || !ansi.matches(PATTERN)) {
                throw new IllegalArgumentException("Invalid ANSI background format: " + ansi);
            }
            this.ansi = ansi;
        }

        public ColorType type() {
            return ColorType.evalGet(this.ansi);
        }

        public int red() {
            if (this.type() != ColorType.TWENTY_FOUR_BIT) {
                throw new IllegalStateException("This color is not a 24-bit color.");
            }
            return this.extractRGBComponent(1);
        }

        public int green() {
            if (this.type() != ColorType.TWENTY_FOUR_BIT) {
                throw new IllegalStateException("This color is not a 24-bit color.");
            }
            return this.extractRGBComponent(2);
        }

        public int blue() {
            if (this.type() != ColorType.TWENTY_FOUR_BIT) {
                throw new IllegalStateException("This color is not a 24-bit color.");
            }
            return this.extractRGBComponent(3);
        }

        public static Background fromEightBit(int colorCode) {
            if (colorCode < 0 || colorCode > 255) {
                throw new IllegalArgumentException("8-bit color code must be between 0 and 255.");
            }
            return new Background("\u001B[48;5;" + colorCode + "m");
        }

        public static Background fromRGB(int r, int g, int b) {
            if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
                throw new IllegalArgumentException("RGB values must be between 0 and 255.");
            }
            return new Background("\u001B[48;2;" + r + ";" + g + ";" + b + "m");
        }

        private int extractRGBComponent(int groupIndex) {
            String pattern = "\\u001B\\[38;2;\\d{1,3};\\d{1,3};\\d{1,3}m";
            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(this.ansi);
            if (!matcher.matches()) {
                throw new IllegalStateException("Invalid ANSI foreground format: " + this.ansi);
            }
            return Integer.parseInt(matcher.group(groupIndex));
        }

        @Override
        public String toString() {
            return this.ansi;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Background that = (Background) obj;
            return this.ansi.equals(that.ansi);
        }

        @Override
        public int hashCode() {
            return this.toString().hashCode();
        }
    }

    public static class Mix implements ColorObject {
        private final Foreground foreground;
        private final Background background;

        public Mix(Foreground foreground, Background background) {
            this.foreground = foreground;
            this.background = background;
        }

        public Foreground foreground() {
            return this.foreground;
        }

        public Background background() {
            return this.background;
        }

        @Override
        public String toString() {
            return this.foreground.toString() + this.background.toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Mix that = (Mix) obj;
            return this.foreground.equals(that.foreground) && this.background.equals(that.background);
        }

        @Override
        public int hashCode() {
            return this.toString().hashCode();
        }
    }

    public interface ColorObject {
        @Override
        String toString();
        @Override
        boolean equals(Object obj);
        @Override
        int hashCode();
    }

    public enum ColorType {
        RESET("\\u001B\\[0m|\\u001B\\[39m|\\u001B\\[49m"),
        NORMAL("\\u001B\\[3[0-7]m|\\u001B\\[4[0-7]m"),
        BRIGHT("\\u001B\\[9[0-7]m|\\u001B\\[10[0-7]m"),
        EIGHT_BIT("\\u001B\\[38;5;\\d{1,3}m|\\u001B\\[48;5;\\d{1,3}m"),
        TWENTY_FOUR_BIT("\\u001B\\[38;2;\\d{1,3};\\d{1,3};\\d{1,3}m|\\u001B\\[48;2;\\d{1,3};\\d{1,3};\\d{1,3}m");

        private final String pattern;

        ColorType(String pattern) {
            this.pattern = pattern;
        }

        public String pattern() {
            return this.pattern;
        }

        public boolean eval(String ansi) {
            return ansi != null && ansi.matches(this.pattern());
        }

        public static ColorType evalGet(String ansi) {
            for (ColorType t : ColorType.values()) {
                if (ansi != null && ansi.matches(t.pattern())) {
                    return t;
                }
            }
            throw new IllegalArgumentException("Invalid ANSI color format: " + ansi);
        }

        @Override
        public String toString() {
            return this.name();
        }
    }
}
