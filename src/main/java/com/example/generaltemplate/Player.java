package com.example.generaltemplate;

import java.util.Arrays;
import java.util.List;

public class Player {
    private PlayerStats playerStats;
    private int addMoneyAmount;
    private final List<HomeAction> allHomeActions;

    public Player() {
        playerStats = new PlayerStats();
        addMoneyAmount = 1;
        allHomeActions = Arrays.asList(
                new Study(),
                new HangOut(),
                new Gym(),
                new Shopping(),
                new Bed()
        );
    }

    public void addMoney() {
        playerStats.set(StatTypes.MONEY, playerStats.get(StatTypes.MONEY) + addMoneyAmount);
    }

    public void addToAddMoneyAmount(int amt) {
        addMoneyAmount += amt;
    }

    public PlayerStats getBaseStats() {
        return playerStats;
    }

    public PlayerStats getModifStats() {
        PlayerStats result = playerStats;
        for (HomeAction homeAction: allHomeActions) {
            result = result.add(homeAction.getStatBonus().multiply(homeAction.getUsePoints()));
        }
        return result;
    }

    public int getAddMoneyAmount() {
        return addMoneyAmount;
    }

    public PlayerStats getHomeActionStatCost(String homeActionName) {
        for (HomeAction homeAction: allHomeActions) {
            if (homeAction.getName().equals(homeActionName)) {
                return homeAction.getStatCost();
            }
        }
        throw new RuntimeException("Can't' find that HomeAction");
    }

    private HomeAction findHomeAction(String name) {
        for (HomeAction homeAction: allHomeActions) {
            if (homeAction.getName().equals(name)) {
                return homeAction;
            }
        }
        throw new RuntimeException("Can't find HomeAction");
    }

    public void doHomeAction(String action) {
        playerStats = playerStats.add(findHomeAction(action).getStatCost());
        HomeAction homeAction = findHomeAction(action);
        homeAction.run();
        for (StatTypes statType: homeAction.getHomeActionType().getStatBonus().getAll().keySet()) {
            if (homeAction.getHomeActionType().getStatBonus().get(statType) != 0) {
                if (statType.getStatModifType().equals(StatModifTypes.BASE)) {
                    playerStats.add(statType, homeAction.getHomeActionType().getStatBonus().get(statType));
                    homeAction.clearUsePoints();
                }
            }
        }
    }

    public PlayerStats getHomeActionStatBonus(String action) {
        return findHomeAction(action).getStatBonus();
    }

    public void loseAllUsePoints() {
        for (HomeAction homeAction: allHomeActions) {
            for (StatTypes statBonus: homeAction.getStatBonus().getAll().keySet()) {
                if (statBonus.getStatModifType().equals(StatModifTypes.JOB_GAME)) {
                    homeAction.clearUsePoints();
                    playerStats.set(statBonus, 0);
                }
            }
        }
    }

    public String getHomeActionDescription(String action) {
        return findHomeAction(action).getHomeActionType().getDescription();
    }

    private abstract class HomeAction {
        private final HomeActionTypes homeActionType;
        private int upgradePoints;
        private int usePoints;
        private PlayerStats statCost;
        private PlayerStats statBonus;

        public HomeAction(HomeActionTypes homeActionType) {
            this.homeActionType = homeActionType;
            statCost = homeActionType.getStatCost();
            this.statBonus = homeActionType.getStatBonus();
        }

        public abstract void run();

        public int getUpgradePoints() {
            return upgradePoints;
        }

        public void addUpgradePoints(int amt) {
            upgradePoints += amt;
        }

        public int getUsePoints() {return usePoints;}
        public void incrementUsePoints() {
            usePoints++;
        }

        public String getName() {
            return homeActionType.getName();
        }

        public PlayerStats getStatBonus() {
            return statBonus;
        }

        public PlayerStats getStatCost() {
            return statCost;
        }

        public HomeActionTypes getHomeActionType() {
            return homeActionType;
        }
        
        public void clearUsePoints() {
            usePoints = 0;
        }
    }

    private final class Study extends HomeAction {

        public Study() {
            super(HomeActionTypes.STUDY);
        }

        @Override
        public void run() {
            incrementUsePoints();
        }
    }

    private final class HangOut extends HomeAction {

        public HangOut() {
            super(HomeActionTypes.HANGOUT);
        }

        @Override
        public void run() {
            incrementUsePoints();
        }
    }

    private final class Gym extends HomeAction {

        public Gym() {
            super(HomeActionTypes.GYM);
        }

        @Override
        public void run() {
            incrementUsePoints();
        }
    }

    private final class Shopping extends HomeAction {

        public Shopping() {
            super(HomeActionTypes.SHOPPING);
        }

        @Override
        public void run() {
            addUpgradePoints(1);
        }
    }

    private final class Bed extends HomeAction {

        public Bed() {
            super(HomeActionTypes.BED);
        }

        @Override
        public void run() {
            playerStats.set(StatTypes.STAMINA, playerStats.get(StatTypes.MAX_STAMINA));
        }
    }
}
