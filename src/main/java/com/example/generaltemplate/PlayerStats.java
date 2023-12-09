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

    public boolean canAdd (PlayerStats statToSubtract) {
        for (StatTypes statType : StatTypes.getAll()) {
            if (all.get(statType) + statToSubtract.get(statType) < 0) {
                return false;
            }
        }
        return true;
    }
    
    public void add(StatTypes statTypeToChange, int amt) {
        for (StatTypes statType: all.keySet()) {
            if (statType.equals(statTypeToChange)) {
                all.put(statType, all.get(statType) + amt);
                break;
            }
        }
    }
    
    public PlayerStats add(PlayerStats statToAdd) {
        HashMap<StatTypes, Integer> newAll = new HashMap<>();
        for (StatTypes statType : StatTypes.getAll()) {
            newAll.put(statType, all.get(statType) + statToAdd.get(statType));
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

    public String getString(boolean incPlusAndMinusSigns) {
        StringBuilder result = new StringBuilder();
        for (StatTypes statType: all.keySet()) {
            if (incPlusAndMinusSigns) {
                if (all.get(statType) > 0) {
                    result.append(statType.getName()).append(": +").append(all.get(statType)).append("\n");
                } else if (all.get(statType) < 0) {
                    result.append(statType.getName()).append(": ").append(all.get(statType)).append("\n");
                }
            } else {
                if (all.get(statType) > 0) {
                    result.append(statType.getName()).append(": ").append(all.get(statType)).append("\n");
                }
            }
        }
        return result.toString();
    }

    public void setAll(HashMap<StatTypes, Integer> all) {
        this.all = all;
    }
}
