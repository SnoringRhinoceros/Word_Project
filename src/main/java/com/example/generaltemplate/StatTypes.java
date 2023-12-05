package com.example.generaltemplate;

public enum StatTypes {
    STAMINA(StatModifTypes.BASE, 15),
    MAX_STAMINA(StatModifTypes.BASE, 15),
    MONEY(StatModifTypes.BASE, 0),
    TIME_BONUS(StatModifTypes.JOB_GAME, 0),
    LETTER_BONUS(StatModifTypes.JOB_GAME, 0);

    private final int startingVal;
    private final StatModifTypes statModifType;
    StatTypes(StatModifTypes statModifType, int startingVal) {
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
}
