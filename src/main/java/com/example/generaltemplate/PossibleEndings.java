package com.example.generaltemplate;

public enum PossibleEndings {
    GOOD("win_screen"), BAD("lose_screen"), SECRET("secret_screen");

    public final String name;

    PossibleEndings(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
