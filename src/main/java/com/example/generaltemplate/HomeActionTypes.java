package com.example.generaltemplate;

public enum HomeActionTypes {
    STUDY("study", new PlayerStats(-5, 0, 0, 0, 0),
            new PlayerStats(0, 0, 0, 0, 1),
            "Studying takes stamina,\nbut it makes hangman words easier.", 1),
    HANGOUT("hangOut", new PlayerStats(-10, 0, 0, 0, 0),
            new PlayerStats(0, 0, 0, 30, 0),
            """
                    They say, time flies when you're having fun.
                    The opposite also applies.
                    Costs stamina,
                    but gives more time while campaigning.""", 5),
    GYM("gym", new PlayerStats(-5, 0, 0, 0 , 0),
            new PlayerStats(0, 5, 0, 0, 0),
            "Working out is tiring,\nbut benefits overall.", 5),
    BED("bed", new PlayerStats(0, 0, 0, 0, 0),
            new PlayerStats(0, 0, 0, 0, 0),
            "Restores stamina to full,\nbut a new day is started,\nso start campaigning again.", 0),
    SHOPPING("shopping", new PlayerStats(0, 0, 0, 0, 0),
            new PlayerStats(0, 0, 0, 0, 0),
            """
                    Through the power of money,
                    you can upgrade the other actions.
                    Upgrades other home actions to have better stats""", 0);

    private final String name;
    private PlayerStats statCost;
    private PlayerStats statBonus;
    private String description;

    private final int scalingBonus;

    HomeActionTypes(String name, PlayerStats statCost, PlayerStats statBonus, String description, int scalingBonus) {
        this.name = name;
        this.statCost = statCost;
        this.statBonus = statBonus;
        this.description = description;
        this.scalingBonus = scalingBonus;
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

    public int getScalingBonus() {
        return scalingBonus;
    }
}
