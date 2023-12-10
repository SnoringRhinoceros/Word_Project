package com.example.generaltemplate;

public class Game {
    private JobGame currentJobGame;
    private final Player player;
    private int daysPassed;
    private final int finalDay = 2;

    public Game() {
        player = new Player();
    }

    public JobGame getCurrentJobGame() {
        return currentJobGame;
    }

    public void setCurrentJobGame(JobGame currentJobGame) {
        this.currentJobGame = currentJobGame;
    }

    public Player getPlayer() {
        return player;
    }

    public void incrementDaysPassed() {daysPassed++;
        System.out.println(daysPassed);}

    public boolean over() {return !(daysPassed < finalDay);}

    public int getTimeLeft() {return finalDay-daysPassed;}
}
