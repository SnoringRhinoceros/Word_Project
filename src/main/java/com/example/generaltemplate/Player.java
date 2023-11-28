package com.example.generaltemplate;

import java.util.Arrays;
import java.util.List;

public class Player {
    private int money;
    private final PlayerStats stats;
    private int addMoneyAmount;
    private final List<HomeAction> allHomeActions;

    public Player() {
        stats = new PlayerStats();
        addMoneyAmount = 1;
        allHomeActions = Arrays.asList(new Study(), new HangOut(), new Gym(), new Upgrade(), new Bed());
    }

    private abstract class HomeAction {
        private final String name;
        private int upgradePoints = 0;

        public HomeAction(String name) {
            this.name = name;
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
    }

    public void doHomeAction(String action) {
        for (HomeAction homeAction: allHomeActions) {
            if (homeAction.getName().equals(action)) {
                homeAction.run();
                return;
            }
        }
        throw new RuntimeException("Can't find action");
    }

    private final class Study extends HomeAction {

        public Study() {
            super("study");
        }

        @Override
        public void run() {
            addUpgradePoints(1);
            System.out.println(getUpgradePoints());
        }
    }

    private final class HangOut extends HomeAction {

        public HangOut() {
            super("hangOut");
        }

        @Override
        public void run() {
            addUpgradePoints(1);
            System.out.println(getUpgradePoints());
        }
    }

    private final class Gym extends HomeAction {

        public Gym() {
            super("gym");
        }

        @Override
        public void run() {
            addUpgradePoints(1);
            System.out.println(getUpgradePoints());
        }
    }

    private final class Upgrade extends HomeAction {

        public Upgrade() {
            super("upgrade");
        }

        @Override
        public void run() {
            addUpgradePoints(1);
            System.out.println(getUpgradePoints());
        }
    }

    private final class Bed extends HomeAction {

        public Bed() {
            super("bed");
        }

        @Override
        public void run() {
            addUpgradePoints(1);
            System.out.println(getUpgradePoints());
        }
    }



    public void addMoney() {
        money += addMoneyAmount;
    }

    public void addToAddMoneyAmount(int amt) {
        addMoneyAmount += amt;
    }

    public int getMoney() {
        return money;
    }

    public PlayerStats getStats() {
        return stats;
    }

    public int getAddMoneyAmount() {
        return addMoneyAmount;
    }
}
