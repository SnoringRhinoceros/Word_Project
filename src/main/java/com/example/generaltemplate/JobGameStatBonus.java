package com.example.generaltemplate;

public class JobGameStatBonus {
    private PlayerStats all;
    private int level = 1;
    private int scalingBonus = 1;

    public JobGameStatBonus(PlayerStats basePlayerStats) {
        all = new PlayerStats(basePlayerStats);
    }
}
