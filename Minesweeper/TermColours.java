package Minesweeper;

public class TermColours {
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[1;30m";
    public static final String RED = "\u001B[31m";
    public static final String REDBOLD = "\u001B[1;91m";
    public static final String REDBG = "\u001B[38;5;88m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[1;33m";
    public static final String ORANGE = "\u001B[38;5;202m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREY = "\u001B[37m";
    public static final String WHITEBOLD = "\u001B[1;97m";

    public static void main(String[] args) {
        System.out.println(ORANGE + "Hi" + RESET + " white");
    }
}
