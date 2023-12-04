package com.example.generaltemplate;

import java.util.Arrays;
import java.util.List;

public class Player {
    private final PlayerStats playerStats;
    private int addMoneyAmount;
    private final List<HomeAction> allHomeActions;

    public Player() {
        playerStats = new PlayerStats();
        addMoneyAmount = 1;
        allHomeActions = Arrays.asList(
                new Study(),
                new HangOut(),
                new Gym(),
                new Upgrade(),
                new Bed()
        );
    }

    public void addMoney() {
        getStats().set(StatTypes.MONEY, getStats().get(StatTypes.MONEY) + addMoneyAmount);
    }

    public void addToAddMoneyAmount(int amt) {
        addMoneyAmount += amt;
    }

    public PlayerStats getStats() {
        return playerStats;
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
        playerStats.subtract(findHomeAction(action).getStatCost());
        findHomeAction(action).run();
    }

    public PlayerStats getHomeActionStatBonus(String action) {
        return findHomeAction(action).getStatBonus();
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
        public void incrementUsePoints() {usePoints++;}

        public String getName() {
            return homeActionType.getName();
        }

        public PlayerStats getStatBonus() {
            return statBonus;
        }

        public PlayerStats getStatCost() {
            return statCost;
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

    private final class Upgrade extends HomeAction {

        public Upgrade() {
            super(HomeActionTypes.UPGRADE);
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
            incrementUsePoints();
            playerStats.set(StatTypes.STAMINA, playerStats.get(StatTypes.MAX_STAMINA));
        }
    }
}
