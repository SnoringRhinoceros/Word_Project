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
    private final HashMap<String, HomeAction> homeActions;

    public Player() {
        homeActions = new HashMap<>();
        homeActions.put("study", () -> study());
        homeActions.put("hangOut", () -> hangOut());
        homeActions.put("gym", () -> gym());
        homeActions.put("upgrade", () -> upgrade());
        homeActions.put("bed", () -> bed());

        addMoneyAmount = 1;
    }

    public void doHomeAction(String action) {
        homeActions.get(action).run();
    }

    private interface HomeAction {
        void run();
    }

    public void study() {

    }

    public void hangOut() {

    }

    public void gym() {

    }

    public void upgrade() {

    }

    public void bed() {

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
