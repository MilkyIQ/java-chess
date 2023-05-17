package tools;

public class ConsoleColors {
    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";   // WHITE

    // Bold
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

    // Underline
    public static final String BLACK_UNDERLINED = "\033[4;30m";  // BLACK
    public static final String RED_UNDERLINED = "\033[4;31m";    // RED
    public static final String GREEN_UNDERLINED = "\033[4;32m";  // GREEN
    public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
    public static final String BLUE_UNDERLINED = "\033[4;34m";   // BLUE
    public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
    public static final String CYAN_UNDERLINED = "\033[4;36m";   // CYAN
    public static final String WHITE_UNDERLINED = "\033[4;37m";  // WHITE

    // Background
    public static final String BLACK_BACKGROUND = "\033[40m";  // BLACK
    public static final String RED_BACKGROUND = "\033[41m";    // RED
    public static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
    public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
    public static final String BLUE_BACKGROUND = "\033[44m";   // BLUE
    public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
    public static final String CYAN_BACKGROUND = "\033[46m";   // CYAN
    public static final String WHITE_BACKGROUND = "\033[47m";  // WHITE

    // High Intensity
    public static final String BLACK_BRIGHT = "\033[0;90m";  // BLACK
    public static final String RED_BRIGHT = "\033[0;91m";    // RED
    public static final String GREEN_BRIGHT = "\033[0;92m";  // GREEN
    public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
    public static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE
    public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
    public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN
    public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE

    // Bold High Intensity
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

    // High Intensity backgrounds
    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";  // CYAN
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";   // WHITE

    // is this good practice? dunno!
    public static String getColorCodeOf(String color)
    {
        switch (color.toUpperCase())
        {
            case "BLACK": return BLACK; 
            case "RED": return RED; 
            case "GREEN": return GREEN; 
            case "YELLOW": return YELLOW; 
            case "BLUE": return BLUE; 
            case "PURPLE": return PURPLE; 
            case "CYAN": return CYAN; 
            case "WHITE": return WHITE; 
            case "BLACK_BOLD": return BLACK_BOLD; 
            case "RED_BOLD": return RED_BOLD; 
            case "GREEN_BOLD": return GREEN_BOLD; 
            case "YELLOW_BOLD": return YELLOW_BOLD; 
            case "BLUE_BOLD": return BLUE_BOLD; 
            case "PURPLE_BOLD": return PURPLE_BOLD; 
            case "CYAN_BOLD": return CYAN_BOLD; 
            case "WHITE_BOLD": return WHITE_BOLD; 
            case "BLACK_UNDERLINED": return BLACK_UNDERLINED; 
            case "RED_UNDERLINED": return RED_UNDERLINED; 
            case "GREEN_UNDERLINED": return GREEN_UNDERLINED; 
            case "YELLOW_UNDERLINED": return YELLOW_UNDERLINED; 
            case "BLUE_UNDERLINED": return BLUE_UNDERLINED; 
            case "PURPLE_UNDERLINED": return PURPLE_UNDERLINED; 
            case "CYAN_UNDERLINED": return CYAN_UNDERLINED; 
            case "WHITE_UNDERLINED": return WHITE_UNDERLINED; 
            case "BLACK_BACKGROUND": return BLACK_BACKGROUND; 
            case "RED_BACKGROUND": return RED_BACKGROUND; 
            case "GREEN_BACKGROUND": return GREEN_BACKGROUND; 
            case "YELLOW_BACKGROUND": return YELLOW_BACKGROUND; 
            case "BLUE_BACKGROUND": return BLUE_BACKGROUND; 
            case "PURPLE_BACKGROUND": return PURPLE_BACKGROUND; 
            case "CYAN_BACKGROUND": return CYAN_BACKGROUND; 
            case "WHITE_BACKGROUND": return WHITE_BACKGROUND; 
            case "BLACK_BRIGHT": return BLACK_BRIGHT; 
            case "RED_BRIGHT": return RED_BRIGHT; 
            case "GREEN_BRIGHT": return GREEN_BRIGHT; 
            case "YELLOW_BRIGHT": return YELLOW_BRIGHT; 
            case "BLUE_BRIGHT": return BLUE_BRIGHT; 
            case "PURPLE_BRIGHT": return PURPLE_BRIGHT; 
            case "CYAN_BRIGHT": return CYAN_BRIGHT; 
            case "WHITE_BRIGHT": return WHITE_BRIGHT; 
            case "BLACK_BOLD_BRIGHT": return BLACK_BOLD_BRIGHT; 
            case "RED_BOLD_BRIGHT": return RED_BOLD_BRIGHT; 
            case "GREEN_BOLD_BRIGHT": return GREEN_BOLD_BRIGHT; 
            case "YELLOW_BOLD_BRIGHT": return YELLOW_BOLD_BRIGHT; 
            case "BLUE_BOLD_BRIGHT": return BLUE_BOLD_BRIGHT; 
            case "PURPLE_BOLD_BRIGHT": return PURPLE_BOLD_BRIGHT; 
            case "CYAN_BOLD_BRIGHT": return CYAN_BOLD_BRIGHT; 
            case "WHITE_BOLD_BRIGHT": return WHITE_BOLD_BRIGHT; 
            case "BLACK_BACKGROUND_BRIGHT": return BLACK_BACKGROUND_BRIGHT; 
            case "RED_BACKGROUND_BRIGHT": return RED_BACKGROUND_BRIGHT; 
            case "GREEN_BACKGROUND_BRIGHT": return GREEN_BACKGROUND_BRIGHT; 
            case "YELLOW_BACKGROUND_BRIGHT": return YELLOW_BACKGROUND_BRIGHT; 
            case "BLUE_BACKGROUND_BRIGHT": return BLUE_BACKGROUND_BRIGHT; 
            case "PURPLE_BACKGROUND_BRIGHT": return PURPLE_BACKGROUND_BRIGHT; 
            case "CYAN_BACKGROUND_BRIGHT": return CYAN_BACKGROUND_BRIGHT; 
            case "WHITE_BACKGROUND_BRIGHT": return WHITE_BACKGROUND_BRIGHT;
            case "RESET": return RESET;
            case "BLANK": return "";
            default: return null;
        }
    }
}

