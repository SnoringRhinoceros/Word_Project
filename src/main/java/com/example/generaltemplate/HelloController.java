package com.example.generaltemplate;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Timer;

public class HelloController {
    @FXML
    public TextField txtInput;
    @FXML
    public Button startPlayBtn, submitBtn;
    @FXML
    public AnchorPane startViewAnchorPane,  playViewAnchorPane, endPlayViewAnchorPane;
    @FXML
    public Label timeLbl, wordToGuessLbl;
    @FXML
    public Label statusTxtLbl;
    private final FakeScreenController fakeScreenController = new FakeScreenController();
    public final static ArrayList<Timer> allTimers = new ArrayList<>();
    @FXML
    public TextArea guessedLettersTextArea;
    private JobGame currentJobGame;
    // in seconds
    private int TIMER_END_TIME = 10000;

    @FXML
    public void initialize() {
        guessedLettersTextArea.setEditable(false);

        FakeScreen startView = new FakeScreen("startView");
        startView.addFXMLElement(startViewAnchorPane);
        fakeScreenController.add(startView);

        FakeScreen playingView = new FakeScreen("playView");
        playingView.addFXMLElement(playViewAnchorPane);
        fakeScreenController.add(playingView);

        FakeScreen playEndView = new FakeScreen("playEndView");
        playEndView.addFXMLElement(endPlayViewAnchorPane);
        fakeScreenController.add(playEndView);

        fakeScreenController.activate("startView");
    }

    public ArrayList<Timer> getAllTimers() {
        return allTimers;
    }

    @FXML
    public void startBtnClick(ActionEvent actionEvent) {
        currentJobGame = new JobGame();
        fakeScreenController.activate("playView");
        currentJobGame.start(new onTimerUpdateTask());
        updatePlayView();
    }

    public class onTimerUpdateTask implements Runnable {
        @Override
        public void run() {
            if (currentJobGame.getTimeElapsed() >= TIMER_END_TIME) {
                fakeScreenController.activate("playEndView");
                currentJobGame.getTimer().cancel();
                txtInput.clear();
            }
            Platform.runLater(this::updateFXMLElementsOnTimerUpdate);
        }

        private void updateFXMLElementsOnTimerUpdate() {
            timeLbl.setText("Time: " + currentJobGame.getTimeElapsed());
            wordToGuessLbl.setText(currentJobGame.getHiddenChosenWord());
        }
    }

    public void updatePlayView() {
        timeLbl.setText("Time: " + currentJobGame.getTimeElapsed());
        wordToGuessLbl.setText(currentJobGame.getHiddenChosenWord());
        if (currentJobGame.getGuesses().getMostRecentGuess() != null) {
            statusTxtLbl.setText(currentJobGame.getGuesses().getMostRecentGuess().getCorrectText());
        }
        if (currentJobGame.wordFullyGuessed()) {
            submitBtn.setText("Next Word");
        } else {
            submitBtn.setText("Submit");
        }
        updateGuessedLettersTextArea();
    }

    private void updateGuessedLettersTextArea() {
        if (!currentJobGame.getGuesses().getAllGuesses().isEmpty()) {
            guessedLettersTextArea.setText("Guesses\n\n");
            guessedLettersTextArea.appendText("Correct Guesses:\n");
            for (Guess guess: currentJobGame.getGuesses().getCorrectGuesses()) {
                guessedLettersTextArea.appendText(guess.getLetter() + "\n");
            }
            guessedLettersTextArea.appendText("\n\nIncorrect Guesses:\n");
            for (Guess guess: currentJobGame.getGuesses().getIncorrectGuesses()) {
                guessedLettersTextArea.appendText(guess.getLetter() + "\n");
            }
        } else {
            guessedLettersTextArea.clear();
        }
    }

    @FXML
    public void submitBtnClick(ActionEvent actionEvent) {
        if (currentJobGame.wordFullyGuessed()) {
            currentJobGame.makeNewChosenWord();
            // handle logic for word is guessed
        } else {
            currentJobGame.guess(txtInput.getText().charAt(0));
        }
        txtInput.clear();
        updatePlayView();
    }

    @FXML
    public void playAgainBtnClick(ActionEvent actionEvent) {
        currentJobGame = new JobGame();
        fakeScreenController.activate("playView");
        currentJobGame.start(new onTimerUpdateTask());
        updatePlayView();
    }

}