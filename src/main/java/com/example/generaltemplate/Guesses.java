package com.example.generaltemplate;

import java.util.ArrayList;

public class Guesses {
    private ArrayList<Guess> allGuesses;
    private Guess mostRecentGuess;
    private ArrayList<Guess> correctGuesses;
    private ArrayList<Guess> incorrectGuesses;

    public Guesses() {
        allGuesses = new ArrayList<>();
        correctGuesses = new ArrayList<>();
        incorrectGuesses = new ArrayList<>();
    }

    public void clear() {
        allGuesses.clear();
        mostRecentGuess = null;
        correctGuesses.clear();
        incorrectGuesses.clear();
    }

    public void guess(String chosenWord, char guess) {
        mostRecentGuess = new Guess(chosenWord, guess);
        allGuesses.add(mostRecentGuess);
        if (mostRecentGuess.getCorrectText().equals("Correct!")) {
            correctGuesses.add(mostRecentGuess);
        } else {
            incorrectGuesses.add(mostRecentGuess);
        }
    }


    public ArrayList<Guess> getAllGuesses() {
        return allGuesses;
    }

    public Guess getMostRecentGuess() {
        return mostRecentGuess;
    }

    public ArrayList<Guess> getCorrectGuesses() {
        return correctGuesses;
    }

    public ArrayList<Guess> getIncorrectGuesses() {
        return incorrectGuesses;
    }
}
