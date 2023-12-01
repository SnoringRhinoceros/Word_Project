package com.example.generaltemplate;

public enum JobGameStatBonusTypes {
    STUDY("study", new PlayerStats(5, 0, 0), new JobGameStatBonus()),
    HANGOUT("hangOut", new PlayerStats(5, 0, 0)),
    GYM("gym", new PlayerStats(5, 0, 0)),
    BED("bed", new PlayerStats(0, 0, 0)),
    UPGRADE("upgrade", new PlayerStats(1, 0, 2));

    private final String name;
    private PlayerStats statCost;
    private JobGameStatBonus jobGameStatBonus;

    JobGameStatBonusTypes(String name, PlayerStats statCost, JobGameStatBonus jobGameStatBonus) {
        this.name = name;
        this.statCost = statCost;
        this.jobGameStatBonus = jobGameStatBonus;
    }

    public String getName() {
        return name;
    }
}
