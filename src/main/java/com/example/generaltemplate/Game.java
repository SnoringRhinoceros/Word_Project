package com.example.generaltemplate;

public class Game {
    private JobGame currentJobGame;
    private final Home home;

    public Game() {
        home = new Home();
    }

    public JobGame getCurrentJobGame() {
        return currentJobGame;
    }

    public void setCurrentJobGame(JobGame currentJobGame) {
        this.currentJobGame = currentJobGame;
    }

    public Home getHome() {
        return home;
    }

    public void givePlayerEndOfDayMoney() {
        for (int i = 0; i < currentJobGame.getWordsCorrect(); i++) {
            getHome().getPlayer().addMoney();
        }
    }
}
