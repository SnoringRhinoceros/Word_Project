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

    public PlayerStats(int stamina, int maxStamina, int money, int timeBonus, int letterBonus) {
        all = new HashMap<>();
        all.put(StatTypes.STAMINA, stamina);
        all.put(StatTypes.MAX_STAMINA, maxStamina);
        all.put(StatTypes.MONEY, money);
        all.put(StatTypes.TIME_BONUS, timeBonus);
        all.put(StatTypes.LETTER_BONUS, letterBonus);
    }

    public PlayerStats(HashMap<StatTypes, Integer> all) {
        this.all = all;
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

    public PlayerStats add(PlayerStats statToAdd) {
        HashMap<StatTypes, Integer> newAll = new HashMap<>();
        for (StatTypes statType : StatTypes.getAll()) {
            newAll.put(statType, all.get(statType) + statToAdd.get(statType));
        }
        return new PlayerStats(newAll);
    }

    public PlayerStats subtract (PlayerStats statToSubtract) {
        HashMap<StatTypes, Integer> newAll = new HashMap<>();
        for (StatTypes statType : StatTypes.getAll()) {
            newAll.put(statType, all.get(statType) - statToSubtract.get(statType));
        }
        return new PlayerStats(newAll);
    }

    public PlayerStats multiply(int multiplyAmt) {
        HashMap<StatTypes, Integer> newAll = new HashMap<>();
        for (StatTypes statType : StatTypes.getAll()) {
            newAll.put(statType, all.get(statType) * multiplyAmt);
        }
        return new PlayerStats(newAll);
    }

    public String getString() {
        StringBuilder result = new StringBuilder();
        for (StatTypes statType: all.keySet()) {
            if (all.get(statType) > 0) {
               result.append(statType.name()).append(": +").append(all.get(statType)).append("\n");
            } else if (all.get(statType) < 0) {
                result.append(statType.name()).append(": +").append(all.get(statType)).append("\n");
            }
        }
        if (!result.isEmpty()) {
            result.insert(0, "Stats:\n");
        }
        return result.toString();
    }

    public void setAll(HashMap<StatTypes, Integer> all) {
        this.all = all;
    }
}
