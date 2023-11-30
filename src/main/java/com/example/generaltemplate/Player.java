package com.example.generaltemplate;

import java.util.Arrays;
import java.util.List;

public class Player {
    private final Stats stats;
    private int addMoneyAmount;
    private final List<HomeAction> allHomeActions;

    public Player() {
        stats = new Stats();
        addMoneyAmount = 1;
        allHomeActions = Arrays.asList(
                new Study(new Stats(5, 0, 0)),
                new HangOut(new Stats(5, 0, 0)),
                new Gym(new Stats(5, 0, 0)),
                new Upgrade(new Stats(1, 0, 2)),
                new Bed(new Stats(0, 0, 0))
        );
    }

    public void addMoney() {
        getStats().set(StatTypes.MONEY, getStats().get(StatTypes.MONEY) + addMoneyAmount);
    }

    public void addToAddMoneyAmount(int amt) {
        addMoneyAmount += amt;
    }

    public Stats getStats() {
        return stats;
    }

    public int getAddMoneyAmount() {
        return addMoneyAmount;
    }

    public Stats getHomeActionStatCost(String homeActionName) {
        for (HomeAction homeAction: allHomeActions) {
            if (homeAction.getName().equals(homeActionName)) {
                return homeAction.getStatCost();
            }
        }
        throw new RuntimeException("Can't' find that HomeAction");
    }

    private abstract class HomeAction {
        private final String name;
        private int upgradePoints;
        private int usePoints;
        private Stats statCost;

        public HomeAction(String name, Stats statCost) {
            this.name = name;
            this.statCost = statCost;
        }

        public abstract void run();

        public int getUpgradePoints() {
            return upgradePoints;
        }

        public void addUpgradePoints(int amt) {
            upgradePoints += amt;
        }

        public String getName() {
            return name;
        }

        public int getUsePoints() {return usePoints;}

        public Stats getStatCost() {
            return statCost;
        }

        public void setStatCost(Stats statCost) {
            this.statCost = statCost;
        }
    }

    public void doHomeAction(String action) {
        for (HomeAction homeAction: allHomeActions) {
            if (homeAction.getName().equals(action)) {
                stats.subtract(homeAction.getStatCost());
                homeAction.run();
                return;
            }
        }
        throw new RuntimeException("Can't find action");
    }

    private final class Study extends HomeAction {

        public Study(Stats statCost) {
            super("study", statCost);
        }

        @Override
        public void run() {
            addUpgradePoints(1);
        }
    }

    private final class HangOut extends HomeAction {

        public HangOut(Stats statCost) {
            super("hangOut", statCost);
        }

        @Override
        public void run() {
            addUpgradePoints(1);
        }
    }

    private final class Gym extends HomeAction {

        public Gym(Stats statCost) {
            super("gym", statCost);
        }

        @Override
        public void run() {
            addUpgradePoints(1);
        }
    }

    private final class Upgrade extends HomeAction {

        public Upgrade(Stats statCost) {
            super("upgrade", statCost);
        }

        @Override
        public void run() {
            addUpgradePoints(1);
        }
    }

    private final class Bed extends HomeAction {

        public Bed(Stats statCost) {
            super("bed", statCost);
        }

        @Override
        public void run() {
            stats.set(StatTypes.STAMINA, stats.get(StatTypes.MAX_STAMINA));
        }
    }
}
