package com.example.generaltemplate;

public enum StatTypes {
    STAMINA("Stamina", StatModifTypes.BASE, 10),
    MAX_STAMINA("Max Stamina", StatModifTypes.BASE, 10),
    MONEY("Money", StatModifTypes.BASE, 0),
    TIME_BONUS("Time Bonus", StatModifTypes.JOB_GAME, 0),
    LETTER_BONUS("Letter Bonus", StatModifTypes.JOB_GAME, 0);

    private final String name;
    private final int startingVal;
    private final StatModifTypes statModifType;

    StatTypes(String name, StatModifTypes statModifType, int startingVal) {
        this.name = name;
        this.statModifType = statModifType;
        this.startingVal = startingVal;
    }

    public static StatTypes[] getAll() {
        return values();
    }

    public int getStartingVal() {
        return startingVal;
    }

    public StatModifTypes getStatModifType() {
        return statModifType;
    }

    public String getName() {
        return name;
    }
}
