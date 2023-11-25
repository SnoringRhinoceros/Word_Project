package com.example.generaltemplate;

public class Guess {
    private char letter;
    private String correctText;
    public Guess(String chosenWord, char letter) {
        this.letter = Character.toLowerCase(letter);
        correctText = getCorrectText(chosenWord.toLowerCase().indexOf(letter) != -1);
    }


    private String getCorrectText(boolean wordGuessedRight) {
        if (wordGuessedRight) {
            return "Correct!";
        } else {
            return "Wrong :(";
        }
    }

    public char getLetter() {
        return letter;
    }

    public String getCorrectText() {
        return correctText;
    }
}
