package com.example.generaltemplate;

public enum HomeActionTypes {
    STUDY("study", new PlayerStats(-5, 0, 0, 0, 0),
            new PlayerStats(0, 0, 0, 0, 1),
            "Studying takes stamina,\nbut it makes hangman words easier.",
            1, 2, 1, 5),
    HANGOUT("hangOut", new PlayerStats(-6, 0, 0, 0, 0),
            new PlayerStats(0, 0, 0, 20, 0),
            """
                    They say, time flies when you're having fun.
                    The opposite also applies.
                    Costs stamina,
                    but gives more time while campaigning.""",
            5, 2, 20, 3),
    GYM("gym", new PlayerStats(-5, 0, 0, 0 , 0),
            new PlayerStats(0, 5, 0, 0, 0),
            "Working out is tiring,\nbut increases max stamina.",
            5, 2, 20, 3),
    BED("bed", new PlayerStats(0, 0, 0, 0, 0),
            new PlayerStats(0, 0, 0, 0, 0),
            "Restores stamina to full,\nbut a new day is started,\nso start campaigning again.",
            0, 0, 1, 0),
    SHOPPING("shopping", new PlayerStats(-2, 0, 0, 0, 0),
            new PlayerStats(0, 0, 0, 0, 0),
            """
                    Through the power of money,
                    you can upgrade the other actions.
                    Upgrades other home actions to have better stats""",
            0, 0, 1, 0);

    private final String name;
    private final PlayerStats statCost;
    private final PlayerStats statBonus;
    private final int scalingBonus;
    private final String description;
    private final int maxTotalUpgrade;
    private final int maxTotalUsePoints;
    private final int costToUpgrade;

    HomeActionTypes(String name, PlayerStats statCost, PlayerStats statBonus, String description, int scalingBonus, int maxTotalUpgrade, int maxTotalUsePoints, int costToUpgrade) {
        this.name = name;
        this.statCost = statCost;
        this.statBonus = statBonus;
        this.description = description;
        this.scalingBonus = scalingBonus;
        this.maxTotalUpgrade = maxTotalUpgrade;
        this.maxTotalUsePoints = maxTotalUsePoints;
        this.costToUpgrade = costToUpgrade;
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

    public int getMaxTotalUpgrade() {return maxTotalUpgrade;}

    public int getMaxTotalUsePoints() {
        return maxTotalUsePoints;
    }

    public int getCostToUpgrade() {
        return costToUpgrade;
    }
}
