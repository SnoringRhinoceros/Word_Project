package com.example.generaltemplate;

import java.util.Arrays;
import java.util.List;

public class Player {
    private PlayerStats playerStats;
    private int addMoneyAmount;
    private final List<HomeAction> allHomeActions;
    private int votersGained;

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
        votersGained = 0;
    }

    public void addMoney() {
        playerStats.set(StatTypes.MONEY, playerStats.get(StatTypes.MONEY) + addMoneyAmount);
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

    public int getVotersGained() {
        return votersGained;
    }

    public void addVoters(int amt) {
        votersGained += amt;
    }

    public PlayerStats getHomeActionStatCost(String homeActionName) {
        return findHomeAction(homeActionName).getStatCost();
    }

    public PlayerStats getNextUpgradeStats(String homeActionName) {
        return findHomeAction(homeActionName).getNextUpgradeStatBonus();
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

    public void incrementUpgradePoints(String shoppingUpgradeBtnAction) {
        findHomeAction(shoppingUpgradeBtnAction).incrementUpgradePoints();
    }

    public int getHomeActionUpgradePoints(String action) {return findHomeAction(action).getUpgradePoints();}

    public int getHomeActionMaxTotalUsePoints(String action) {return findHomeAction(action).getHomeActionType().getMaxTotalUsePoints();}

    public int getHomeActionUsePoints(String action) {return findHomeAction(action).getUsePoints();}

    public boolean getHomeActionUpgradeBuyable(String action) {return playerStats.get(StatTypes.MONEY) >= findHomeAction(action).getHomeActionType().getCostToUpgrade()
            && getHomeActionUpgradePoints(action) < getHomeActionMaxTotalUsePoints(action);}

    public int getHomeActionCostToUpgrade(String action) {return findHomeAction(action).getHomeActionType().getCostToUpgrade();}

    private abstract class HomeAction {
        private final HomeActionTypes homeActionType;
        private int upgradePoints;
        private int usePoints;
        private final PlayerStats statCost;
        private final PlayerStats statBonus;

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
            PlayerStats result = new PlayerStats();
            for (StatTypes statType: statBonus.getAll().keySet()) {
                result.set(statType, statBonus.get(statType));
                if (statBonus.get(statType) > 0) {
                    result.set(statType, statBonus.get(statType)+(upgradePoints*homeActionType.getScalingBonus()));
                }
            }
            return result;
        }

        public PlayerStats getNextUpgradeStatBonus() {
            PlayerStats result = new PlayerStats();
            for (StatTypes statType: statBonus.getAll().keySet()) {
                result.set(statType, statBonus.get(statType));
                if (statBonus.get(statType) > 0) {
                    result.set(statType, statBonus.get(statType)+((upgradePoints+1)* homeActionType.getScalingBonus()));
                }
            }
            return result;
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

        public void incrementUpgradePoints() {
            upgradePoints++;
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
