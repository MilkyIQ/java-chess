package tools;

public final class Color {

    private Color()
    {
        throw new IllegalAccessError("Color.class is unable to instantiate objects.");
    }

    // is this good practice? dunno!
    public static final String get(String color)
    {
        switch (color.toUpperCase())
        {
            case "RESET": return "\033[0m";

            case "BLACK":  return "\033[0;30m";   
            case "RED":    return "\033[0;31m";     
            case "GREEN":  return "\033[0;32m";   
            case "YELLOW": return "\033[0;33m";  
            case "BLUE":   return "\033[0;34m";    
            case "PURPLE": return "\033[0;35m";  
            case "CYAN":   return "\033[0;36m";    
            case "WHITE":  return "\033[0;37m";   
            
            case "BLACK_BOLD":  return "\033[1;30m";  
            case "RED_BOLD":    return "\033[1;31m";    
            case "GREEN_BOLD":  return "\033[1;32m";  
            case "YELLOW_BOLD": return "\033[1;33m"; 
            case "BLUE_BOLD":   return "\033[1;34m";   
            case "PURPLE_BOLD": return "\033[1;35m"; 
            case "CYAN_BOLD":   return "\033[1;36m";   
            case "WHITE_BOLD":  return "\033[1;37m";  
            
            case "BLACK_UNDERLINED":  return "\033[4;30m";  
            case "RED_UNDERLINED":    return "\033[4;31m";    
            case "GREEN_UNDERLINED":  return "\033[4;32m";  
            case "YELLOW_UNDERLINED": return "\033[4;33m"; 
            case "BLUE_UNDERLINED":   return "\033[4;34m";   
            case "PURPLE_UNDERLINED": return "\033[4;35m"; 
            case "CYAN_UNDERLINED":   return "\033[4;36m";   
            case "WHITE_UNDERLINED":  return "\033[4;37m";   
            
            case "BLACK_BACKGROUND":  return "\033[40m";  
            case "RED_BACKGROUND":    return "\033[41m";    
            case "GREEN_BACKGROUND":  return "\033[42m";  
            case "YELLOW_BACKGROUND": return "\033[43m"; 
            case "BLUE_BACKGROUND":   return "\033[44m";   
            case "PURPLE_BACKGROUND": return "\033[45m"; 
            case "CYAN_BACKGROUND":   return "\033[46m";   
            case "WHITE_BACKGROUND":  return "\033[47m";  
            
            case "BLACK_BRIGHT":  return "\033[0;90m";  
            case "RED_BRIGHT":    return "\033[0;91m";    
            case "GREEN_BRIGHT":  return "\033[0;92m";  
            case "YELLOW_BRIGHT": return "\033[0;93m"; 
            case "BLUE_BRIGHT":   return "\033[0;94m";   
            case "PURPLE_BRIGHT": return "\033[0;95m"; 
            case "CYAN_BRIGHT":   return "\033[0;96m";   
            case "WHITE_BRIGHT":  return "\033[0;97m";  
            
            case "BLACK_BOLD_BRIGHT":  return "\033[1;90m"; 
            case "RED_BOLD_BRIGHT":    return "\033[1;91m";   
            case "GREEN_BOLD_BRIGHT":  return "\033[1;92m"; 
            case "YELLOW_BOLD_BRIGHT": return "\033[1;93m";
            case "BLUE_BOLD_BRIGHT":   return "\033[1;94m";  
            case "PURPLE_BOLD_BRIGHT": return "\033[1;95m";
            case "CYAN_BOLD_BRIGHT":   return "\033[1;96m";  
            case "WHITE_BOLD_BRIGHT":  return "\033[1;97m"; 
            
            case "BLACK_BACKGROUND_BRIGHT":  return "\033[0;100m";
            case "RED_BACKGROUND_BRIGHT":    return "\033[0;101m";
            case "GREEN_BACKGROUND_BRIGHT":  return "\033[0;102m";
            case "YELLOW_BACKGROUND_BRIGHT": return "\033[0;103m";
            case "BLUE_BACKGROUND_BRIGHT":   return "\033[0;104m";
            case "PURPLE_BACKGROUND_BRIGHT": return "\033[0;105m"; 
            case "CYAN_BACKGROUND_BRIGHT":   return "\033[0;106m";  
            case "WHITE_BACKGROUND_BRIGHT":  return"\033[0;107m";

            default: return "";
        }
    }
}

