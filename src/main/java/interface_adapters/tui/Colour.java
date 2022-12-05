package interface_adapters.tui;

/**
 * Helper class for colouring terminal output
 * Can be used directly e.g. <code>System.out.println(Colour.RED + "Hello World!" + Colour.RESET)</code>
 * Can be used with the helper function e.g. <code>Colour.println(Color.RED, "Hello World!");</code>
 */
@SuppressWarnings("unused")
public class Colour {
    /**
     * Prints a given string in the given colour.
     *
     * @param colour  the colour to print in
     * @param args    the optional arguments for the string formatting
     * @param message the message to print
     */
    public static void println(String colour, String message, Object... args) {
        System.out.println(colour + String.format(message, args) + RESET);
    }

    /**
     * Returns a coloured message.
     *
     * @param colour  the colour of the message
     * @param message the message to colour
     * @param args    the optional arguments for the string formatting
     * @return the coloured message
     */
    public static String colour(String colour, String message, Object... args) {
        return colour + String.format(message, args) + RESET;
    }

    /**
     * Prints a given string as a header, with a standard colour.
     *
     * @param header the header to print
     */
    public static void header(String header) {
        System.out.printf("\n%s== %s ==%s\n", BLUE_BOLD, header, RESET);
    }

    /**
     * Prints a given string as a section, with a standard colour.
     *
     * @param section the section to print
     * @param args    the optional arguments for string formatting the section
     */
    public static void section(String section, Object... args) {
        println(PURPLE_BOLD, String.format("\n" + section, args));
    }

    /**
     * Prints an informative message from the system to the user.
     * Use this method to tell the user something has happened, e.g. "Added 10 ingredients"
     *
     * @param message the message to show the user
     * @param args    optional arguments for string formatting the message
     */
    public static void info(String message, Object... args) {
        println(GREEN, String.format("\n" + messageMarker + message, args));
    }

    /**
     * Prints an error message from the system to the user.
     * Use this method to tell the user something has gone wrong, e.g. "Invalid input"
     *
     * @param error the error to show the user
     * @param args  optional arguments for string formatting the message
     */
    public static void error(String error, Object... args) {
        println(RED, String.format("\n" + messageMarker + error, args));
    }

    /**
     * Returns a yellow example string for displaying to the user.
     *
     * @param example the example string, e.g. "exit"
     * @return the coloured example string
     */
    public static String example(String example) {
        return colour(YELLOW_BOLD, example);
    }

    /**
     * Marker used for info and error messages shown to the user.
     */
    private static final String messageMarker = "\u2503 ";

    public static final String RESET = "\033[0m";

    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";

    public static final String BLACK_BOLD = "\033[1;30m";
    public static final String RED_BOLD = "\033[1;31m";
    public static final String GREEN_BOLD = "\033[1;32m";
    public static final String YELLOW_BOLD = "\033[1;33m";
    public static final String BLUE_BOLD = "\033[1;34m";
    public static final String PURPLE_BOLD = "\033[1;35m";
    public static final String CYAN_BOLD = "\033[1;36m";
    public static final String WHITE_BOLD = "\033[1;37m";

    public static final String BLACK_UNDERLINED = "\033[4;30m";
    public static final String RED_UNDERLINED = "\033[4;31m";
    public static final String GREEN_UNDERLINED = "\033[4;32m";
    public static final String YELLOW_UNDERLINED = "\033[4;33m";
    public static final String BLUE_UNDERLINED = "\033[4;34m";
    public static final String PURPLE_UNDERLINED = "\033[4;35m";
    public static final String CYAN_UNDERLINED = "\033[4;36m";
    public static final String WHITE_UNDERLINED = "\033[4;37m";

    public static final String BLACK_BACKGROUND = "\033[40m";
    public static final String RED_BACKGROUND = "\033[41m";
    public static final String GREEN_BACKGROUND = "\033[42m";
    public static final String YELLOW_BACKGROUND = "\033[43m";
    public static final String BLUE_BACKGROUND = "\033[44m";
    public static final String PURPLE_BACKGROUND = "\033[45m";
    public static final String CYAN_BACKGROUND = "\033[46m";
    public static final String WHITE_BACKGROUND = "\033[47m";
}
