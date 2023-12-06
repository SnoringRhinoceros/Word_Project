package com.example.generaltemplate;

public enum HomeActionTypes {
    STUDY("study", new PlayerStats(-5, 0, 0, 0, 0),
            new PlayerStats(0, 0, 0, 0, 1),
            "Studying takes stamina,\nbut it makes hangman words easier."),
    HANGOUT("hangOut", new PlayerStats(-10, 0, 0, 0, 0),
            new PlayerStats(0, 0, 0, 30, 0),
            """
                    They say, time flies when you're having fun.
                    The opposite also applies.
                    Costs stamina,
                    but gives more time while campaigning."""),
    GYM("gym", new PlayerStats(-5, 0, 0, 0 , 0),
            new PlayerStats(0, 5, 0, 0, 0),
            "Working out is tiring,\nbut benefits overall."),
    BED("bed", new PlayerStats(0, 0, 0, 0, 0),
            new PlayerStats(0, 0, 0, 0, 0),
            "Restores stamina to full,\nbut a new day is started,\nso start campaigning again."),
    UPGRADE("upgrade", new PlayerStats(0, 0, 0, 0, 0),
            new PlayerStats(0, 0, 0, 0, 0),
            """
                    Through the power of money,
                    you can upgrade the other actions.
                    Upgrades other home actions to have better stats""");

    private final String name;
    private PlayerStats statCost;
    private PlayerStats statBonus;
    private String description;

    HomeActionTypes(String name, PlayerStats statCost, PlayerStats statBonus, String description) {
        this.name = name;
        this.statCost = statCost;
        this.statBonus = statBonus;
        this.description = description;
    }

    HomeActionTypes(String name, PlayerStats statCost) {
        this.name = name;
        this.statCost = statCost;
    }

    public String getName() {
        return name;
    }

    public PlayerStats getStatCost() {
        return statCost;
    }

    public PlayerStats getStatBonus() {
        return statBonus;
    }

    public String getDescription() {
        return description;
    }
}
