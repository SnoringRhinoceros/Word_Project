package com.example.generaltemplate;

import java.util.HashMap;

public class PlayerStats {
    private HashMap<StatTypes, Integer> all;

    public PlayerStats() {
        all = new HashMap<>();
        for (StatTypes statType: StatTypes.getAll()) {
            all.put(statType, statType.getStartingVal());
        }
    }

    public PlayerStats(int stamina, int maxStamina, int money) {
        all = new HashMap<>();
        all.put(StatTypes.STAMINA, stamina);
        all.put(StatTypes.MAX_STAMINA, maxStamina);
        all.put(StatTypes.MONEY, money);
    }

    public PlayerStats(PlayerStats statToCopy) {
        all = new HashMap<>();
        all.put(StatTypes.STAMINA, statToCopy.get(StatTypes.STAMINA));
        all.put(StatTypes.MAX_STAMINA, statToCopy.get(StatTypes.MAX_STAMINA));
        all.put(StatTypes.MONEY, statToCopy.get(StatTypes.MONEY));
    }

    public HashMap<StatTypes, Integer> getAll() {
        return all;
    }

    public void set(StatTypes statTypeToFind, Integer num) {
        for (StatTypes statType: StatTypes.getAll()) {
            if (statType.equals(statTypeToFind)) {
                all.put(statType, num);
            }
        }
    }

    public int get(StatTypes statTypes) {
        return all.get(statTypes);
    }

    public boolean canSubtract (PlayerStats statToSubtract) {
        for (StatTypes statType : StatTypes.getAll()) {
            if (all.get(statType) - statToSubtract.get(statType) < 0) {
                return false;
            }
        }
        return true;
    }

    public void subtract (PlayerStats statToSubtract) {
        HashMap<StatTypes, Integer> newAll = new HashMap<>();
        for (StatTypes statType : StatTypes.getAll()) {
            newAll.put(statType, all.get(statType) - statToSubtract.get(statType));
        }
        all = newAll;
    }
}
