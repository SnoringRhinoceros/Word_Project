package com.example.generaltemplate;

public class Guess {
    private final char letter;
    private final String correctText;
    public Guess(ChosenWord chosenWord, char letter) {
        this.letter = Character.toLowerCase(letter);
        correctText = getCorrectText(chosenWord.getText().toLowerCase().indexOf(this.letter) != -1);
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
