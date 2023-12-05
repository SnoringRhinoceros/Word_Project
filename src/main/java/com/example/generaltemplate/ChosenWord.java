package com.example.generaltemplate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ChosenWord {
    private final String text;
    private String hiddenChosenWord;
    
    public ChosenWord() {
        text = getRandWord();
        System.out.println(text);
    }

    public ChosenWord(int difficulty) {
        text = getRandWord(difficulty);
        System.out.println(text);
    }

    private String getRandWord() {
        try {
            String path = "src/main/java/com/example/generaltemplate/words.txt";
            BufferedReader lineNumReader = new BufferedReader(new FileReader(path));
            int numLines = 0;
            while (lineNumReader.readLine() != null) {
                numLines++;
            }
            lineNumReader.close();

            BufferedReader reader = new BufferedReader(new FileReader(path));
            int randNum = generateRandNum(0, numLines-1);
            for (int i = 0; i < randNum; i++) {
                reader.readLine();
            }
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getRandWord(int difficulty) {
        try {
            String path = "src/main/java/com/example/generaltemplate/words.txt";
            BufferedReader lineNumReader = new BufferedReader(new FileReader(path));
            int numLines = 0;
            while (lineNumReader.readLine() != null) {
                numLines++;
            }
            lineNumReader.close();

            BufferedReader reader = new BufferedReader(new FileReader(path));
            int randNum = generateRandNum(0, numLines-1);
            for (int i = 0; i < randNum; i++) {
                reader.readLine();
            }
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int generateRandNum(int minInc, int maxInc) {
        return minInc + (int) (Math.random()*((maxInc - minInc) + 1));
    }

    public void setHiddenChosenWord(Guesses guesses) {
        hiddenChosenWord = "";
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            boolean wordFound = false;
            for (Guess guess: guesses.getAllGuesses()) {
                if (String.valueOf(guess.getLetter()).equals(String.valueOf(Character.toLowerCase(c)))) {
                    wordFound = true;
                    break;
                }
            }
            if (wordFound) {
                hiddenChosenWord += c;
                wordFound = false;
            } else {
                hiddenChosenWord += "_";
            }
        }
    }

    public boolean wordFullyGuessed() {
        return !hiddenChosenWord.contains("_");
    }

    public String getText() {
        return text;
    }

    public String getHiddenChosenWord() {
        return hiddenChosenWord;
    }
}
