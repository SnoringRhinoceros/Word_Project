package com.example.generaltemplate;

import javafx.geometry.Pos;

import java.util.ArrayList;

public class Game {
    private JobGame currentJobGame;
    private final Player player;
    private int daysPassed;
    private final int finalDay = 2;
    private final int votersNeeded = 1;
    private final ArrayList<PossibleEndings> endingsReached;

    public Game() {
        player = new Player();
        endingsReached = new ArrayList<>();
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

    public PossibleEndings getCurEnding() {
        if (player.getVotersGained() > votersNeeded) {
            return PossibleEndings.GOOD;
        } else if (player.getVotersGained() < votersNeeded) {
            return PossibleEndings.BAD;
        } else if (player.getVotersGained() == votersNeeded) {
            return PossibleEndings.SECRET;
        }
        throw new RuntimeException("Ending not possible");
    }

    public ArrayList<PossibleEndings> getEndingsReached() {
        return endingsReached;
    }

    public void addEndingReached(PossibleEndings endingReached) {
        if (!endingsReached.contains(endingReached)) {
            endingsReached.add(endingReached);
        }
    }

    public int getVotersNeeded() {
        return votersNeeded;
    }
}
