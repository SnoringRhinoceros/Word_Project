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
    public Button startPlayBtn, submitBtn, studyingBtn, hangingOutBtn, gymBtn, upgradeBtn, bedBtn;
    @FXML
    public AnchorPane startViewAnchorPane,  playViewAnchorPane, playEndViewAnchorPane, atHomeViewAnchorPane, nextDayAnchorPane;
    @FXML
    public Label timeLbl, wordToGuessLbl, statusTxtLbl;
    @FXML
    public TextArea guessedLettersTextArea, playEndStatsTextArea;
    private JobGame currentJobGame;
    private final FakeScreenController fakeScreenController = new FakeScreenController();
    public final static ArrayList<Timer> allTimers = new ArrayList<>();
    // in seconds
    private int TIMER_END_TIME = 5;

    @FXML
    public void initialize() {
        guessedLettersTextArea.setEditable(false);
        playEndStatsTextArea.setEditable(false);

        FakeScreen startView = new FakeScreen("startView");
        startView.addFXMLElement(startViewAnchorPane);
        fakeScreenController.add(startView);

        FakeScreen playingView = new FakeScreen("playView");
        playingView.addFXMLElement(playViewAnchorPane);
        fakeScreenController.add(playingView);

        FakeScreen playEndView = new FakeScreen("playEndView");
        playEndView.addFXMLElement(playEndViewAnchorPane);
        fakeScreenController.add(playEndView);

        FakeScreen atHomeView = new FakeScreen("atHomeView");
        atHomeView.addFXMLElement(atHomeViewAnchorPane);
        fakeScreenController.add(atHomeView);

        FakeScreen nextDayView = new FakeScreen("nextDayView");
        nextDayView.addFXMLElement(nextDayAnchorPane);
        fakeScreenController.add(nextDayView);

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
            wordToGuessLbl.setText(currentJobGame.getChosenWord().getHiddenChosenWord());
        }
    }

    public void updatePlayView() {
        timeLbl.setText("Time: " + currentJobGame.getTimeElapsed());
        wordToGuessLbl.setText(currentJobGame.getChosenWord().getHiddenChosenWord());
        if (currentJobGame.getGuesses().getMostRecentGuess() != null) {
            statusTxtLbl.setText(currentJobGame.getGuesses().getMostRecentGuess().getCorrectText());
        }
        if (currentJobGame.getChosenWord().wordFullyGuessed()) {
            submitBtn.setText("Next Word");
            submitBtn.setDisable(false);
        } else {
            submitBtn.setText("Submit");
            submitBtn.setDisable(false);
            if (!txtInput.getText().isEmpty() && txtInput.getText().length() == 1) {
                for (Guess guess: currentJobGame.getGuesses().getAllGuesses()) {
                    if (guess.getLetter() == txtInput.getText().charAt(0)) {
                        submitBtn.setDisable(true);
                        break;
                    }
                }
            } else {
                submitBtn.setDisable(true);
            }
        }
        updateGuessedLettersTextArea();
    }

    private void updateGuessedLettersTextArea() {
        guessedLettersTextArea.setText("# Questions Correct: " + currentJobGame.getWordsCorrect()+"\n\n");
        if (!currentJobGame.getGuesses().getAllGuesses().isEmpty()) {
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
            guessedLettersTextArea.setText("# Questions Correct: " + currentJobGame.getWordsCorrect()+"\n\n");
        }
    }

    @FXML
    public void submitBtnClick(ActionEvent actionEvent) {
        if (currentJobGame.getChosenWord().wordFullyGuessed()) {
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

    @FXML
    public void goHomeBtnClick(ActionEvent actionEvent) {
        fakeScreenController.activate("atHomeView");
    }
}