package com.example.generaltemplate;


public class Game {
    private JobGame currentJobGame;
    private Player player;
    private int daysPassed;
    private final int finalDay = 2;
    private final int votersNeeded = 1;
    private EndingsReached endingsReached;

    public Game() {
        player = new Player();
        endingsReached = new EndingsReached();
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

    public EndingsReached getEndingsReached() {
        return endingsReached;
    }

    public void addEndingReached(PossibleEndings endingReached) {
        if (!endingsReached.getAll().contains(endingReached)) {
            endingsReached.getAll().add(endingReached);
        }
    }

    public int getVotersNeeded() {
        return votersNeeded;
    }

    public void reset() {
        this.player = new Player();
        daysPassed = 0;
    }

    public void setEndingsReached(EndingsReached endingsReached) {
        this.endingsReached = endingsReached;
    }
}
