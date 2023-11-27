package com.example.generaltemplate;

import java.util.HashMap;

public class PlayerStats {
    private Stats stats;
    
    public PlayerStats() {
        for (StatTypes statType: StatTypes.getAll()) {
            stats.add(StatT);
        }
    }
}
