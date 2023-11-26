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
    public Button startPlayBtn, submitBtn, studyBtn, hangOutBtn, gymBtn, upgradeBtn, bedBtn;
    @FXML
    public AnchorPane startViewAnchorPane,  playViewAnchorPane, playEndViewAnchorPane, atHomeViewAnchorPane, nextDayAnchorPane;
    @FXML
    public Label timeLbl, wordToGuessLbl, statusTxtLbl;
    @FXML
    public TextArea guessedLettersTextArea, playEndStatsTextArea;
    private final FakeScreenController fakeScreenController = new FakeScreenController();
    public final static ArrayList<Timer> allTimers = new ArrayList<>();
    // in seconds
    private int TIMER_END_TIME = 20;
    private Game game;

    @FXML
    public void initialize() {
        guessedLettersTextArea.setEditable(false);
        playEndStatsTextArea.setEditable(false);

        game = new Game();

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
        game.setCurrentJobGame(new JobGame());
        fakeScreenController.activate("playView");
        game.getCurrentJobGame().start(new onTimerUpdateTask());
        updatePlayView();
    }

    public class onTimerUpdateTask implements Runnable {
        @Override
        public void run() {
            if (game.getCurrentJobGame().getTimeElapsed() >= TIMER_END_TIME) {
                fakeScreenController.activate("playEndView");
                game.getCurrentJobGame().getTimer().cancel();
                txtInput.clear();
                game.givePlayerEndOfDayMoney();
                System.out.println(game.getHome().getPlayer().getMoney());
            }
            Platform.runLater(this::updateFXMLElementsOnTimerUpdate);
        }

        private void updateFXMLElementsOnTimerUpdate() {
            timeLbl.setText("Time: " + game.getCurrentJobGame().getTimeElapsed());
            wordToGuessLbl.setText(game.getCurrentJobGame().getChosenWord().getHiddenChosenWord());
        }
    }

    public void updatePlayView() {
        timeLbl.setText("Time: " + game.getCurrentJobGame().getTimeElapsed());
        wordToGuessLbl.setText(game.getCurrentJobGame().getChosenWord().getHiddenChosenWord());
        if (game.getCurrentJobGame().getGuesses().getMostRecentGuess() != null) {
            statusTxtLbl.setText(game.getCurrentJobGame().getGuesses().getMostRecentGuess().getCorrectText());
        }
        if (game.getCurrentJobGame().getChosenWord().wordFullyGuessed()) {
            submitBtn.setText("Next Word");
            submitBtn.setDisable(false);
        } else {
            submitBtn.setText("Submit");
            submitBtn.setDisable(false);
            if (!txtInput.getText().isEmpty() && txtInput.getText().length() == 1) {
                for (Guess guess: game.getCurrentJobGame().getGuesses().getAllGuesses()) {
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
        guessedLettersTextArea.clear();
        guessedLettersTextArea.setText("Money Earned: " + game.getCurrentJobGame().getWordsCorrect()*game.getHome().getPlayer().getAddMoneyAmount());
        guessedLettersTextArea.appendText("\nMoney per correct word: " + game.getHome().getPlayer().getAddMoneyAmount());
        if (!game.getCurrentJobGame().getGuesses().getAllGuesses().isEmpty()) {
            guessedLettersTextArea.appendText("\n\nCorrect Guesses:\n");
            for (Guess guess: game.getCurrentJobGame().getGuesses().getCorrectGuesses()) {
                guessedLettersTextArea.appendText(guess.getLetter() + "\n");
            }
            guessedLettersTextArea.appendText("\n\nIncorrect Guesses:\n");
            for (Guess guess: game.getCurrentJobGame().getGuesses().getIncorrectGuesses()) {
                guessedLettersTextArea.appendText(guess.getLetter() + "\n");
            }
        }
    }

    @FXML
    public void submitBtnClick(ActionEvent actionEvent) {
        if (game.getCurrentJobGame().getChosenWord().wordFullyGuessed()) {
            game.getCurrentJobGame().makeNewChosenWord();
            // handle logic for word is guessed
        } else {
            game.getCurrentJobGame().guess(txtInput.getText().charAt(0));
        }
        txtInput.clear();
        updatePlayView();
    }

    @FXML
    public void playAgainBtnClick(ActionEvent actionEvent) {
        game.setCurrentJobGame(new JobGame());
        fakeScreenController.activate("playView");
        game.getCurrentJobGame().start(new onTimerUpdateTask());
        updatePlayView();
    }

    @FXML
    public void goHomeBtnClick(ActionEvent actionEvent) {
        fakeScreenController.activate("atHomeView");
    }

    @FXML
    public void atHomeActionBtnClick(ActionEvent actionEvent) {
        String btnId = ((Button) actionEvent.getTarget()).getId();
        game.getHome().givePlayerAction(btnId);
    }
}