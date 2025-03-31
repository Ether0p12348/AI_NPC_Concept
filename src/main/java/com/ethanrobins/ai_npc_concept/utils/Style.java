package com.ethanrobins.ai_npc_concept.utils;

/**
 * <b>Style</b> - ANSI-compatible text style enumeration for terminal formatting.
 * <p>This enum implements {@link Formatting.Formatter} and provides ANSI escape sequences for
 * applying styles like bold, italic, underline, blink, and more.</p>
 *
 * <br><b>Constants:</b>
 * <ul>
 *   <li>{@link Style#BOLD}</li>
 *   <li>{@link Style#DIM}</li>
 *   <li>{@link Style#ITALIC}</li>
 *   <li>{@link Style#UNDERLINE}</li>
 *   <li>{@link Style#BLINK}</li>
 *   <li>{@link Style#REVERSE}</li>
 *   <li>{@link Style#HIDDEN}</li>
 *   <li>{@link Style#STRIKETHROUGH}</li>
 * </ul>
 *
 * <br><b>Methods:</b>
 * <ul>
 *   <li>{@link Style#style()}</li>
 *   <li>{@link Style#reset()}</li>
 *   <li>{@link Style#style(Style...)}</li>
 *   <li>{@link Style#resetAll()}</li>
 *   <li>{@link Style#reset(Style...)}</li>
 *   <li>{@link Style#stylize(String, Style...)}</li>
 *   <li>{@link Style#toString()}</li>
 * </ul>
 *
 * <b>Usage:</b>
 * <p>Use {@code Style} to enhance CLI output with formatted styling.
 * Can be used alongside {@link Color} for complete terminal formatting.</p>
 */
public enum Style implements Formatting.Formatter {
    /**
     * Bold text style.
     */
    BOLD("\u001B[1m", "\u001B[22m"),
    /**
     * Dimmed (faint) text style.
     */
    @Deprecated
    DIM("\u001B[2m", "\u001B[22m"),
    /**
     * Italicized text style.
     */
    ITALIC("\u001B[3m", "\u001B[23m"),
    /**
     * Underlined text style.
     */
    UNDERLINE("\u001B[4m", "\u001B[24m"),
    /**
     * Blinking text style.
     */
    @Deprecated
    BLINK("\u001B[5m", "\u001B[25m"),
    /**
     * Reverse/inverted color style.
     */
    REVERSE("\u001B[7m", "\u001B[27m"),
    /**
     * Hidden/invisible text style.
     */
    @Deprecated
    HIDDEN("\u001B[8m", "\u001B[28m"),
    /**
     * Strikethrough text style.
     */
    STRIKETHROUGH("\u001B[9m", "\u001B[29m");

    /**
     * The ANSI escape code to apply the style.
     */
    private final String style;
    /**
     * The ANSI escape code to reset the style.
     */
    private final String reset;

    /**
     * Combined ANSI sequence to reset all defined styles.
     */
    public static final String RESET_ALL;

    // Loads the class with the RESET_ALL constant set to keep things efficient.
    static {
        StringBuilder sb = new StringBuilder();
        for (Style s : Style.values()) {
            sb.append(s.reset());
        }
        RESET_ALL = sb.toString();
    }

    /**
     * Constructs a {@link Style} constant with ANSI style and reset codes.
     *
     * @param style The style ANSI sequence.
     * @param reset The corresponding reset ANSI sequence.
     */
    Style(String style, String reset) {
        this.style = style;
        this.reset = reset;
    }

    /**
     * Returns the ANSI code that applies this style.
     *
     * @return {@link #style}
     */
    public String style() {
        return this.style;
    }

    /**
     * Returns the ANSI code that resets this style.
     *
     * @return {@link #reset}
     */
    public String reset() {
        return this.reset;
    }

    /**
     * Returns a combined ANSI style code from multiple {@link Style} values.
     *
     * @param style One or more styles.
     * @return Combined ANSI sequence.
     */
    public static String style(Style... style) {
        StringBuilder sb = new StringBuilder();
        for (Style s : style) {
            sb.append(s.style);
        }
        return sb.toString();
    }

    /**
     * Returns a combined ANSI reset sequence for all styles.
     *
     * @return {@link #RESET_ALL}
     */
    public static String resetAll() {
        return RESET_ALL;
    }

    /**
     * Returns a combined ANSI reset sequence for the given styles only.
     *
     * @param style Styles to reset.
     * @return Combined reset string.
     */
    public static String reset(Style... style) {
        StringBuilder sb = new StringBuilder();
        for (Style s : style) {
            sb.append(s.reset());
        }
        return sb.toString();
    }

    /**
     * Applies the given styles to a string, wrapping it with both reset and style codes.
     *
     * @param text The text to stylize.
     * @param style One or more styles to apply.
     * @return Styled string with ANSI codes.
     */
    public static String stylize(String text, Style... style) {
        StringBuilder sb = new StringBuilder();
        sb.append(resetAll());
        for (Style s : style) {
            sb.append(s.style());
        }
        sb.append(text);
        sb.append(resetAll());

        return sb.toString();
    }

    /**
     * Returns this style’s ANSI escape sequence.
     *
     * @return {@link #style}
     */
    @Override
    public String toString() {
        return this.style;
    }
}
