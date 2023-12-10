package com.example.generaltemplate;

import javafx.geometry.Pos;

public class Game {
    private JobGame currentJobGame;
    private final Player player;
    private int daysPassed;
    private final int finalDay = 2;
    private final int votersNeeded = 1;

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

    public void incrementDaysPassed() {daysPassed++;}

    public boolean over() {return !(daysPassed < finalDay);}

    public int getTimeLeft() {return finalDay-daysPassed;}

    public PossibleEndings getGameEnding() {
        if (player.getVotersGained() > votersNeeded) {
            return PossibleEndings.GOOD;
        } else if (player.getVotersGained() < votersNeeded) {
            return PossibleEndings.BAD;
        } else if (player.getVotersGained() == votersNeeded) {
            return PossibleEndings.SECRET;
        }
        throw new RuntimeException("Ending not possible");
    }
}
