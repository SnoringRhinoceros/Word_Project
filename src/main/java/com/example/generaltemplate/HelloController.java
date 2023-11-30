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
    public Button startPlayBtn, submitBtn, studyBtn, hangOutBtn, gymBtn, upgradeBtn, bedBtn, goBackBtn, confirmBtn;
    @FXML
    public AnchorPane startViewAnchorPane,  playViewAnchorPane, playEndViewAnchorPane, atHomeViewAnchorPane,
            nextDayAnchorPane, studyViewAnchorPane, hangOutViewAnchorPane, gymViewAnchorPane, upgradeViewAnchorPane,
            bedViewAnchorPane, playerHomeStatsAnchorPane;
    @FXML
    public Label timeLbl, wordToGuessLbl, statusTxtLbl, moneyLbl, staminaLbl;
    @FXML
    public TextArea guessedLettersTextArea, playEndStatsTextArea;
    private final FakeScreenController fakeScreenController = new FakeScreenController();
    public final static ArrayList<Timer> allTimers = new ArrayList<>();
    // in seconds
    public final static int BASE_END_TIME = 1;
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

        FakeScreen nextDayView = new FakeScreen("nextDayView");
        nextDayView.addFXMLElement(nextDayAnchorPane);
        fakeScreenController.add(nextDayView);


        // home stuff ↓

        FakeScreen atHomeView = new FakeScreen("atHomeView");
        atHomeView.addFXMLElement(atHomeViewAnchorPane);
        atHomeView.addFXMLElement(playerHomeStatsAnchorPane);
        fakeScreenController.add(atHomeView);

        FakeScreen studyView = new FakeScreen("studyView");
        studyView.addFXMLElement(studyViewAnchorPane);
        studyView.addFXMLElement(playerHomeStatsAnchorPane);
        studyView.addFXMLElement(goBackBtn);
        studyView.addFXMLElement(confirmBtn);
        fakeScreenController.add(studyView);

        FakeScreen hangOutView = new FakeScreen("hangOutView");
        hangOutView.addFXMLElement(hangOutViewAnchorPane);
        hangOutView.addFXMLElement(playerHomeStatsAnchorPane);
        hangOutView.addFXMLElement(goBackBtn);
        hangOutView.addFXMLElement(confirmBtn);
        fakeScreenController.add(hangOutView);

        FakeScreen gymView = new FakeScreen("gymView");
        gymView.addFXMLElement(gymViewAnchorPane);
        gymView.addFXMLElement(playerHomeStatsAnchorPane);
        gymView.addFXMLElement(goBackBtn);
        gymView.addFXMLElement(confirmBtn);
        fakeScreenController.add(gymView);

        FakeScreen upgradeView = new FakeScreen("upgradeView");
        upgradeView.addFXMLElement(upgradeViewAnchorPane);
        upgradeView.addFXMLElement(playerHomeStatsAnchorPane);
        upgradeView.addFXMLElement(goBackBtn);
        upgradeView.addFXMLElement(confirmBtn);
        fakeScreenController.add(upgradeView);

        FakeScreen bedView = new FakeScreen("bedView");
        bedView.addFXMLElement(bedViewAnchorPane);
        bedView.addFXMLElement(playerHomeStatsAnchorPane);
        bedView.addFXMLElement(goBackBtn);
        bedView.addFXMLElement(confirmBtn);
        fakeScreenController.add(bedView);

        fakeScreenController.activate("startView");
    }

    public ArrayList<Timer> getAllTimers() {
        return allTimers;
    }

    @FXML
    public void startBtnClick(ActionEvent actionEvent) {
        game.setCurrentJobGame(new JobGame(game.getPlayer()));
        fakeScreenController.activate("playView");
        game.getCurrentJobGame().start(new onTimerUpdateTask());
        updatePlayView();
    }

    public class onTimerUpdateTask implements Runnable {
        @Override
        public void run() {
            if (game.getCurrentJobGame().getTimeElapsed() >= BASE_END_TIME) {
                fakeScreenController.activate("playEndView");
                game.getCurrentJobGame().getTimer().cancel();
                txtInput.clear();
                game.givePlayerEndOfDayMoney();
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
        guessedLettersTextArea.setText("Money Earned: " + game.getCurrentJobGame().getWordsCorrect()*game.getPlayer().getAddMoneyAmount());
        guessedLettersTextArea.appendText("\nMoney per correct word: " + game.getPlayer().getAddMoneyAmount());
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
        } else {
            game.getCurrentJobGame().guess(txtInput.getText().charAt(0));
        }
        txtInput.clear();
        updatePlayView();
    }

    @FXML
    public void playAgainBtnClick(ActionEvent actionEvent) {
        startBtnClick(actionEvent);
    }

    @FXML
    public void goHomeBtnClick(ActionEvent actionEvent) {
        fakeScreenController.activate("atHomeView");
        homeInit();
    }

    private void homeInit() {
        updatePlayerStatsAnchorPane();
    }

    private void updatePlayerStatsAnchorPane() {
        moneyLbl.setText("Money: " + game.getPlayer().getStats().get(StatTypes.MONEY));
        staminaLbl.setText("Stamina: " + game.getPlayer().getStats().get(StatTypes.STAMINA) + "/" + game.getPlayer().getStats().get(StatTypes.MAX_STAMINA));
    }

    private String getActionBtnId(ActionEvent actionEvent) {
        String btnId = ((Button) actionEvent.getTarget()).getId();
        return btnId.substring(0, btnId.length()-3);
    }

    @FXML
    public void atHomeActionBtnClick(ActionEvent actionEvent) {
        String actionId = getActionBtnId(actionEvent);
        fakeScreenController.activate( actionId + "View");
        updateHomeActionViews();
    }

    @FXML
    public void goBackBtnClick(ActionEvent actionEvent) {
        fakeScreenController.activate("atHomeView");
        homeInit();
    }

    @FXML
    public void confirmBtnClick(ActionEvent actionEvent) {
        String action = fakeScreenController.getCurrentScreen().getName();
        action = action.substring(0, action.indexOf("View"));
        game.getPlayer().doHomeAction(action);
        updatePlayerStatsAnchorPane();
        updateHomeActionViews();
    }

    private void updateHomeActionViews() {
        String nameOfCurrentHomeView = fakeScreenController.getCurrentScreen().getName();
        nameOfCurrentHomeView = nameOfCurrentHomeView.substring(0, nameOfCurrentHomeView.indexOf("View"));
        confirmBtn.setDisable(false);
        if (!game.getPlayer().getStats().canSubtract(game.getPlayer().getHomeActionStatCost(nameOfCurrentHomeView))) {
            confirmBtn.setDisable(true);
        }
    }
}