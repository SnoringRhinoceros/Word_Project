package com.example.generaltemplate;

import java.util.HashMap;

public class Stats {
    private HashMap<StatTypes, Integer> all;

    public Stats() {
        all = new HashMap<>();
        for (StatTypes statType: StatTypes.getAll()) {
            all.put(statType, 0);
        }
    }

    public HashMap<StatTypes, Integer> getAll() {
        return all;
    }

    public void set(StatTypes statTypeToFind, Integer num) {
        for (StatTypes statType: StatTypes.getAll()) {
            if (statType.equals(statTypeToFind)) {
                // set int to num
            }
        }
    }
}
