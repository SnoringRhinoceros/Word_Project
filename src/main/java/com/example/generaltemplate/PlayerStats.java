package com.example.generaltemplate;

public class PlayerStats {
    private final Stats stats;
    
    public PlayerStats() {
        stats = new Stats();
        for (StatTypes statType: StatTypes.getAll()) {
            stats.set(statType, statType.getStartingVal());
        }
    }

    public Stats getStats() {
        return stats;
    }
}
