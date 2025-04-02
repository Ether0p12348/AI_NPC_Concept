package com.ethanrobins.ai_npc_concept.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * <b>Style</b> - ANSI-compatible text styles for terminal formatting.
 * <p>This enum defines a set of commonly supported ANSI text styles such as {@link Style#BOLD}, {@link Style#ITALIC},
 * {@link Style#UNDERLINE}, and more. Each style includes both an application sequence ({@link Style.SetStyle}) and
 * a corresponding reset sequence ({@link Style.Reset}). Styles are used to format text output in terminal-based
 * environments and can be combined with color formatting from the {@link Color} system.</p>
 *
 * <br><b>Implements:</b>
 * <ul>
 *   <li>{@link Formatting.Formatter}</li>
 * </ul>
 *
 * <br><b>Enum Constants:</b>
 * <ul>
 *   <li>{@link Style#BOLD}</li>
 *   <li>{@link Style#DIM} (Deprecated)</li>
 *   <li>{@link Style#ITALIC}</li>
 *   <li>{@link Style#UNDERLINE}</li>
 *   <li>{@link Style#BLINK} (Deprecated)</li>
 *   <li>{@link Style#REVERSE}</li>
 *   <li>{@link Style#HIDDEN} (Deprecated)</li>
 *   <li>{@link Style#STRIKETHROUGH}</li>
 * </ul>
 *
 * <br><b>Fields:</b>
 * <ul>
 *     <li>{@link Style#style}</li>
 *     <li>{@link Style#reset}</li>
 *     <li>{@link Style#RESET_ALL}</li>
 * </ul>
 *
 * <br><b>Methods:</b>
 * <ul>
 *     <li>{@link #style()}</li>
 *     <li>{@link #reset()}</li>
 *     <li>{@link #style(Style...)}</li>
 *     <li>{@link #resetAll()}</li>
 *     <li>{@link #reset(Style...)}</li>
 *     <li>{@link #stylize(String, Styler)}</li>
 *     <li>{@link #toString()}</li>
 * </ul>
 *
 * <b>Usage:</b>
 * <p>Styles can be applied individually or mixed using utility methods such as
 * {@link Style#style(Style...)}, {@link Style#resetAll()}, and {@link Style#stylize(String, Styler)}.</p>
 *
 * @see Formatting
 * @see Style.SetStyle
 * @see Style.Reset
 * @see Style.Mix
 * @see Style.StyleObject
 */
public enum Style implements Formatting.Formatter {
    /**
     * Bold text style.
     */
    BOLD(new SetStyle("\u001B[1m"), new Reset("\u001B[22m")),
    /**
     * Dimmed (faint) text style.
     */
    @Deprecated
    DIM(new SetStyle("\u001B[2m"), new Reset("\u001B[22m")),
    /**
     * Italicized text style.
     */
    ITALIC(new SetStyle("\u001B[3m"), new Reset("\u001B[23m")),
    /**
     * Underlined text style.
     */
    UNDERLINE(new SetStyle("\u001B[4m"), new Reset("\u001B[24m")),
    /**
     * Blinking text style.
     */
    @Deprecated
    BLINK(new SetStyle("\u001B[5m"), new Reset("\u001B[25m")),
    /**
     * Reverse/inverted color style.
     */
    REVERSE(new SetStyle("\u001B[7m"), new Reset("\u001B[27m")),
    /**
     * Hidden/invisible text style.
     */
    @Deprecated
    HIDDEN(new SetStyle("\u001B[8m"), new Reset("\u001B[28m")),
    /**
     * Strikethrough text style.
     */
    STRIKETHROUGH(new SetStyle("\u001B[9m"), new Reset("\u001B[29m"));

    /**
     * The {@link SetStyle} with the ANSI escape sequence representing the style.
     */
    private final SetStyle style;
    /**
     * The {@link Reset} with the ANSI escape sequence to reset the style.
     */
    private final Reset reset;

    /**
     * {@link Reset}{@code []} of combined ANSI sequence to reset all defined styles.
     */
    public static final Reset[] RESET_ALL;

    // Loads the class with the RESET_ALL constant set to keep things efficient.
    static {
        List<Reset> resets = new ArrayList<>();
        for (Style s : Style.values()) {
            resets.add(s.reset);
        }
        RESET_ALL = resets.toArray(new Reset[0]);
    }

    /**
     * Constructs a {@link Style} constant with ANSI style and reset codes.
     *
     * @param style {@link #style}
     * @param reset {@link #reset}
     */
    Style(SetStyle style, Reset reset) {
        this.style = style;
        this.reset = reset;
    }

    /**
     * Returns the {@link SetStyle} applies this style.
     *
     * @return {@link #style}
     */
    public SetStyle style() {
        return this.style;
    }

    /**
     * Returns the {@link Reset} that resets this style.
     *
     * @return {@link #reset}
     */
    public Reset reset() {
        return this.reset;
    }

    /**
     * Returns a {@link Mix} of {@link SetStyle} for the given styles only.
     *
     * @param style Styles to mix.
     * @return Combined style object.
     */
    public static Mix<SetStyle> style(Style... style) {
        List<SetStyle> styles = new ArrayList<>();
        for (Style s : style) {
            styles.add(s.style);
        }
        return new Mix<>(styles.toArray(new SetStyle[0]));
    }

    /**
     * Returns a {@link Mix} of {@link Reset} for all styles.
     *
     * @return {@link #RESET_ALL}
     */
    public static Mix<Reset> resetAll() {
        return new Mix<>(RESET_ALL);
    }

    /**
     * Returns a {@link Mix} of {@link Reset} for the given styles only.
     *
     * @param style Styles to reset.
     * @return Combined reset object.
     */
    public static Mix<Reset> reset(Style... style) {
        List<Reset> resets = new ArrayList<>();
        for (Style s : style) {
            resets.add(s.reset);
        }
        return new Mix<>(resets.toArray(new Reset[0]));
    }

    /**
     * Applies the given styles to a string, wrapping it in style codes.
     *
     * @param text The text to stylize.
     * @param style One or more styles to apply.
     * @return Styled string with ANSI codes.
     */
    public static String stylize(String text, Styler style) {
        StringBuilder sb = new StringBuilder();
        sb.append(resetAll());
        if (style instanceof SetStyle s) {
            sb.append(s);
        } else if (style instanceof Mix<?> m) {
            if (m.ansi() instanceof SetStyle[] a) {
                for (SetStyle s : a) {
                    sb.append(s);
                }
            } else {
                throw new IllegalArgumentException("Invalid style object: " + m.ansi().getClass().getName());
            }
        } else {
            throw new IllegalArgumentException("Invalid style object: " + style.getClass().getName());
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
        return this.style.toString();
    }

    /**
     * <b>Style.SetStyle</b> - Represents an ANSI escape sequence used to apply a text style.
     * <p>This class implements {@link StyleObject}, {@link Styler}, and {@link Formatting.FormatObject}, and is used to apply
     * specific ANSI formatting like bold, italic, underline, etc., within terminal output. It wraps the raw ANSI string for the
     * style activation and validates it against a known pattern at construction.</p>
     *
     * <br><b>Implements:</b>
     * <ul>
     *   <li>{@link StyleObject}</li>
     *   <li>{@link Styler}</li>
     *   <li>{@link Formatting.FormatObject}</li>
     * </ul>
     *
     * <br><b>Fields:</b>
     * <ul>
     *   <li>{@link SetStyle#ansi}</li>
     *   <li>{@link SetStyle#PATTERN}</li>
     * </ul>
     *
     * <br><b>Methods:</b>
     * <ul>
     *   <li>{@link SetStyle#toString()}</li>
     *   <li>{@link SetStyle#equals(Object)}</li>
     *   <li>{@link SetStyle#hashCode()}</li>
     * </ul>
     *
     * <b>Usage:</b>
     * <pre>{@code
     * SetStyle bold = new SetStyle("\u001B[1m");
     * System.out.println(bold + "Bold text" + Style.resetAll());
     * }</pre>
     *
     * @see Style
     */
    public static class SetStyle implements StyleObject, Styler, Formatting.FormatObject {
        /**
         * The raw ANSI escape sequence used to apply this text style.
         */
        private final String ansi;
        /**
         * Regular expression pattern for validating allowed ANSI style codes (e.g., bold, italic, underline, etc.).
         * Supported codes: {@code 1, 2, 3, 4, 5, 7, 8, 9}.
         */
        private static final String PATTERN = "\\u001B\\[(1|2|3|4|5|7|8|9)m";

        /**
         * Constructs a {@link SetStyle} with a given ANSI sequence.
         *
         * @param ansi The ANSI escape code that activates a text style (e.g., {@code \u001B[1m} for bold).
         * @throws IllegalArgumentException If the provided string is null or does not match the expected ANSI style format.
         */
        public SetStyle(String ansi) {
            if (ansi == null || !ansi.matches(PATTERN)) {
                throw new IllegalArgumentException("Invalid ANSI style format: " + ansi);
            }
            this.ansi = ansi;
        }

        /**
         * Returns the ANSI escape sequence for this style.
         *
         * @return {@link #ansi}
         */
        @Override
        public String toString() {
            return this.ansi;
        }

        /**
         * Compares this {@link SetStyle} with another object.
         * Two {@link SetStyle} instances are equal if their ANSI sequences are the same.
         *
         * @param obj The object to compare.
         * @return {@code true} if both objects are {@code SetStyle} and their ANSI codes match.
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            SetStyle that = (SetStyle) obj;
            return this.ansi.equals(that.ansi);
        }

        /**
         * Computes the hash code for this {@link SetStyle}, based on its ANSI code.
         *
         * @return Hash code of {@link #ansi}.
         */
        @Override
        public int hashCode() {
            return this.ansi.hashCode();
        }
    }

    /**
     * <b>Style.Reset</b> - Represents an ANSI escape sequence that resets a previously applied text style.
     * <p>This class implements {@link StyleObject}, {@link Styler}, and {@link Formatting.FormatObject} to support
     * formatting operations that restore text to a normal state after a style (e.g., bold, italic) has been applied.</p>
     *
     * <br><b>Implements:</b>
     * <ul>
     *   <li>{@link StyleObject}</li>
     *   <li>{@link Styler}</li>
     *   <li>{@link Formatting.FormatObject}</li>
     * </ul>
     *
     * <br><b>Fields:</b>
     * <ul>
     *   <li>{@link Reset#ansi}</li>
     *   <li>{@link Reset#PATTERN}</li>
     * </ul>
     *
     * <br><b>Methods:</b>
     * <ul>
     *   <li>{@link Reset#toString()}</li>
     *   <li>{@link Reset#equals(Object)}</li>
     *   <li>{@link Reset#hashCode()}</li>
     * </ul>
     *
     * <b>Usage:</b>
     * <pre>{@code
     * Reset resetBold = new Reset("\u001B[22m");
     * System.out.println(Style.BOLD.toString() + "Bold text" + resetBold);
     * }</pre>
     *
     * @see Style
     */
    public static class Reset implements StyleObject, Styler, Formatting.FormatObject {
        /**
         * The ANSI escape sequence that resets a specific text style.
         */
        private final String ansi;
        /**
         * Regex pattern for validating ANSI style reset sequences.
         * <p>Matches known reset codes: {@code 22} (bold/dim), {@code 23} (italic), {@code 24} (underline),
         * {@code 25} (blink), {@code 27} (reverse), {@code 28} (hidden), and {@code 29} (strikethrough).</p>
         */
        private static final String PATTERN = "\\u001B\\[(22|23|24|25|27|28|29)m";

        /**
         * Constructs a {@link Reset} object using a valid ANSI reset sequence.
         *
         * @param ansi The ANSI escape sequence that resets a specific style.
         * @throws IllegalArgumentException If the given ANSI string is null or does not match a valid reset pattern.
         */
        public Reset(String ansi) {
            if (ansi == null || !ansi.matches(PATTERN)) {
                throw new IllegalArgumentException("Invalid ANSI style reset format: " + ansi);
            }
            this.ansi = ansi;
        }

        /**
         * Returns the ANSI escape sequence as a string.
         *
         * @return {@link #ansi}
         */
        @Override
        public String toString() {
            return this.ansi;
        }

        /**
         * Compares this {@link Reset} with another object for equality.
         *
         * @param obj The object to compare.
         * @return {@code true} if both are {@link Reset} instances and their ANSI sequences are equal.
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Reset that = (Reset) obj;
            return this.ansi.equals(that.ansi);
        }

        /**
         * Computes the hash code for this reset sequence.
         *
         * @return Hash code based on the ANSI string.
         */
        @Override
        public int hashCode() {
            return this.toString().hashCode();
        }
    }

    /**
     * <b>Style.Mix</b> - Represents a combination of multiple ANSI style sequences.
     * <p>This generic formatting utility implements {@link StyleObject} and {@link Formatting.FormatObject},
     * and allows multiple {@link Styler} types (such as {@link Style.SetStyle} and {@link Style.Reset}) to be applied
     * together in a single unit. The resulting string is a concatenation of all ANSI style codes in the array.</p>
     *
     * <br><b>Implements:</b>
     * <ul>
     *   <li>{@link StyleObject}</li>
     *   <li>{@link Formatting.FormatObject}</li>
     * </ul>
     *
     * <br><b>Fields:</b>
     * <ul>
     *   <li>{@link Mix#ansiArray}</li>
     * </ul>
     *
     * <br><b>Methods:</b>
     * <ul>
     *   <li>{@link Mix#ansi()}</li>
     *   <li>{@link Mix#toString()}</li>
     *   <li>{@link Mix#equals(Object)}</li>
     *   <li>{@link Mix#hashCode()}</li>
     * </ul>
     *
     * <b>Usage:</b>
     * <pre>{@code
     * Style.Mix<Styler> combined = new Style.Mix<>(
     *     new Style.SetStyle("\u001B[1m"),  // Bold
     *     new Style.SetStyle("\u001B[4m")   // Underline
     * );
     * System.out.println(combined + "Bold + Underlined Text" + Style.resetAll());
     * }</pre>
     *
     * @param <T> A class that implements {@link Styler}, typically {@link Style.SetStyle} or {@link Style.Reset}
     * @see Style
     * @see Styler
     * @see StyleObject
     * @see Formatting.FormatObject
     */
    @SuppressWarnings("unchecked")
    public static class Mix<T extends Styler> implements StyleObject, Formatting.FormatObject {
        /**
         * The array of ANSI style objects that make up this mix.
         */
        private final T[] ansiArray;

        /**
         * Constructs a {@link Mix} from one or more {@link Styler} values.
         *
         * @param ansi One or more style objects to combine.
         */
        public Mix(T... ansi) {
            List<T> ansiList = List.of(ansi);
            this.ansiArray = (T[]) ansiList.toArray(new Styler[0]);
        }

        /**
         * Returns the array of ANSI style elements used in this mix.
         *
         * @return {@link #ansiArray}
         */
        public T[] ansi() {
            return this.ansiArray;
        }

        /**
         * Concatenates all ANSI sequences in the {@link #ansiArray} to form the final ANSI string.
         *
         * @return ANSI string representing all styles in this mix.
         */
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (T s : this.ansiArray) {
                sb.append(s.toString());
            }
            return sb.toString();
        }

        /**
         * Compares this {@link Mix} with another object for equality.
         * Two {@link Mix} objects are equal if they contain the same set of {@link Styler} objects, regardless of order.
         *
         * @param obj The object to compare.
         * @return {@code true} if the {@link #ansiArray} contents match.
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Mix<T> that = (Mix<T>) obj;
            return this.ansiArray.length == that.ansiArray.length &&
                    new HashSet<>(List.of(this.ansiArray)).containsAll(List.of(that.ansiArray));
        }

        /**
         * Computes the hash code based on the combined ANSI string.
         *
         * @return Hash code of {@link #toString()}.
         */
        @Override
        public int hashCode() {
            return this.toString().hashCode();
        }
    }

    /**
     * <b>Style.StyleObject</b> - Marker interface for any object that represents a style element.
     * <p>This interface is implemented by types that can be applied as part of an ANSI style sequence, such as
     * {@link Style.SetStyle}, {@link Style.Reset}, or {@link Style.Mix}. It defines a formatting contract
     * that allows consistent usage in terminal rendering logic.</p>
     *
     * <br><b>Methods:</b>
     * <ul>
     *   <li>{@link #toString()}</li>
     *   <li>{@link #equals(Object)}</li>
     *   <li>{@link #hashCode()}</li>
     * </ul>
     *
     * <b>Usage:</b>
     * <p>Used throughout the {@link Style} system to refer generically to any object that provides ANSI style behavior.</p>
     *
     * @see Style
     */
    public interface StyleObject {
        /**
         * Returns the ANSI escape sequence string represented by this style object.
         *
         * @return ANSI escape sequence.
         */
        @Override
        String toString();

        /**
         * Compares this style object to another for equality.
         *
         * @param obj The object to compare.
         * @return {@code true} if both represent the same ANSI sequence.
         */
        @Override
        boolean equals(Object obj);

        /**
         * Computes a hash code for this style object.
         *
         * @return Hash code derived from the ANSI sequence.
         */
        @Override
        int hashCode();
    }

    /**
     * <b>Style.Styler</b> - Base interface for atomic ANSI style components.
     * <p>Implemented by low-level formatting types such as {@link Style.SetStyle} and {@link Style.Reset}.
     * Unlike {@link Style.StyleObject}, this interface is intended only for single-style units and is used internally
     * by {@link Style.Mix} to construct combined style sequences.</p>
     *
     * <br><b>Methods:</b>
     * <ul>
     *   <li>{@link #toString()}</li>
     *   <li>{@link #equals(Object)}</li>
     *   <li>{@link #hashCode()}</li>
     * </ul>
     *
     * <b>Usage:</b>
     * <p>{@link Styler} is not typically referenced directly outside of {@link Style.Mix}, but is useful for building
     * reusable and type-safe ANSI sequences.</p>
     *
     * @see Style
     */
    public interface Styler {
        /**
         * Returns the ANSI escape sequence represented by this styler.
         *
         * @return ANSI string used to apply or reset a style.
         */
        @Override
        String toString();

        /**
         * Checks whether this styler is equal to another object.
         *
         * @param obj The object to compare with.
         * @return {@code true} if both stylers represent the same ANSI escape code.
         */
        @Override
        boolean equals(Object obj);

        /**
         * Returns the hash code for this styler.
         *
         * @return Hash code based on the ANSI string.
         */
        @Override
        int hashCode();
    }
}
