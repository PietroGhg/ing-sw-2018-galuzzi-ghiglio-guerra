package it.polimi.se2018.model;

public enum Colour {
    RED("\u001B[31m"),
    YELLOW("\u001B[33m"),
    GREEN("\u001B[32m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m");

    public static final String RESET = "\u001B[0m";

    private String escape;

    Colour(String escape) {
        this.escape = escape;
    }

    public String escape() {
        return escape;
    }

    public String letter(){
        if(this == Colour.GREEN) return "G";
        if(this == Colour.RED) return "R";
        if(this == Colour.BLUE) return "B";
        if(this == Colour.PURPLE) return "P";
        if(this == Colour.YELLOW) return "Y";
        return "X";
    }
}
