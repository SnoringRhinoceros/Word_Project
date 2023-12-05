package com.example.generaltemplate;

public enum HomeActionTypes {
    STUDY("study", new PlayerStats(5, 0, 0, 0, 0),
            new PlayerStats(0, 0, 0, 0, 1)),
    HANGOUT("hangOut", new PlayerStats(5, 0, 0, 0, 0),
            new PlayerStats(0, 0, 0, 30, 0)),
    GYM("gym", new PlayerStats(5, 0, 0, 0 , 0),
            new PlayerStats(0, 5, 0, 0, 0)),
    BED("bed", new PlayerStats(0, 0, 0, 0, 0),
            new PlayerStats(0, 0, 0, 0, 0)),
    UPGRADE("upgrade", new PlayerStats(1, 0, 2, 0, 0),
            new PlayerStats(0, 0, 0, 0, 0));

    private final String name;
    private PlayerStats statCost;
    private PlayerStats statBonus;

    HomeActionTypes(String name, PlayerStats statCost, PlayerStats statBonus) {
        this.name = name;
        this.statCost = statCost;
        this.statBonus = statBonus;
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
}
