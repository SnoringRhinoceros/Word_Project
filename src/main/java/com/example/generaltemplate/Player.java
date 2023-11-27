package com.example.generaltemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Player {
    private int money;
    private int stamina;
    private int maxStamina;
    private int addMoneyAmount;
    private final List<HomeAction> allHomeActions;

    public Player() {
        addMoneyAmount = 1;
        allHomeActions = Arrays.asList(new Study(), new HangOut(), new Gym(), new Upgrade(), new Bed());
    }

    private abstract class HomeAction {
        private String name;
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
    }

    private final class Study extends HomeAction {

        public Study() {
            super("study");
        }

        @Override
        public void run() {

        }
    }

    private final class HangOut extends HomeAction {

        public HangOut() {
            super("hangOut");
        }

        @Override
        public void run() {

        }
    }

    private final class Gym extends HomeAction {

        public Gym() {
            super("gym");
        }

        @Override
        public void run() {

        }
    }

    private final class Upgrade extends HomeAction {

        public Upgrade() {
            super("upgrade");
        }

        @Override
        public void run() {

        }
    }

    private final class Bed extends HomeAction {

        public Bed() {
            super("bed");
        }

        @Override
        public void run() {

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

    public int getStamina() {
        return stamina;
    }

    public int getAddMoneyAmount() {
        return addMoneyAmount;
    }
}
