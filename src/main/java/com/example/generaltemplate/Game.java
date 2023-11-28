package com.example.generaltemplate;

public class Game {
    private JobGame currentJobGame;
    private final Player player;

    public Game() {
        player = new Player();
    }

    public JobGame getCurrentJobGame() {
        return currentJobGame;
    }

    public void setCurrentJobGame(JobGame currentJobGame) {
        this.currentJobGame = currentJobGame;
    }

    public void givePlayerEndOfDayMoney() {
        for (int i = 0; i < currentJobGame.getWordsCorrect(); i++) {
            player.addMoney();
        }
    }

    public Player getPlayer() {
        return player;
    }
}
