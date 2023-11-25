package com.example.generaltemplate;

import java.util.ArrayList;

public class Guesses {
    private final ArrayList<Guess> allGuesses;
    private Guess mostRecentGuess;
    private final ArrayList<Guess> correctGuesses;
    private final ArrayList<Guess> incorrectGuesses;
    private int wordsCorrect;

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

    public void guess(ChosenWord chosenWord, char guess) {
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

    public int getWordsCorrect() {
        return wordsCorrect;
    }
}
