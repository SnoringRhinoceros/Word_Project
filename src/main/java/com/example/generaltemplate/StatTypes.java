package com.example.generaltemplate;

public enum StatTypes {
    STAMINA(15), MAX_STAMINA(15), MONEY(0), TIME_BONUS(0), LETTER_BONUS(0);

    private final int startingVal;
    StatTypes(int startingVal) {
        this.startingVal = startingVal;
    }

    public static StatTypes[] getAll() {
        return values();
    }

    public int getStartingVal() {
        return startingVal;
    }
}
