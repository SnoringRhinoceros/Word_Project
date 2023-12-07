package com.example.generaltemplate;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Timer;

public class HelloController {
    @FXML
    public TextField txtInput;
    @FXML
    public Button startPlayBtn, submitBtn, studyBtn, hangOutBtn, gymBtn, shoppingBtn, bedBtn, goBackBtn,
            confirmBtn;
    @FXML
    public AnchorPane startViewAnchorPane,  playViewAnchorPane, playEndViewAnchorPane, atHomeViewAnchorPane,
            nextDayAnchorPane, studyViewAnchorPane, hangOutViewAnchorPane, gymViewAnchorPane, shoppingViewAnchorPane,
            shoppingUpgradeViewAnchorPane, bedViewAnchorPane, playerHomeStatsAnchorPane, startNextDayViewAnchorPane, homeActionViewAnchorPane;
    @FXML
    public Label timeLbl, wordToGuessLbl, statusTxtLbl, moneyLbl, staminaLbl;
    @FXML
    public TextArea guessedLettersTextArea, playEndStatsTextArea, homeActionStatEffectTextArea,
            homeActionDescriptionTextArea, upgradeStatEffectTextArea, upgradeDescriptionTextArea;
    @FXML
    public ButtonBar possibleUpgradesButtonBar;
    private final FakeScreenController fakeScreenController = new FakeScreenController();
    public final static ArrayList<Timer> allTimers = new ArrayList<>();
    public final static int BASE_END_TIME = 1;     // in seconds
    private Game game;

    @FXML
    public void initialize() {
        guessedLettersTextArea.setEditable(false);
        playEndStatsTextArea.setEditable(false);
        homeActionStatEffectTextArea.setEditable(false);
        homeActionDescriptionTextArea.setEditable(false);
        upgradeStatEffectTextArea.setEditable(false);
        upgradeDescriptionTextArea.setEditable(false);

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

        FakeScreen startNextDayView = new FakeScreen("startNextDayView");
        startNextDayView.addFXMLElement(startNextDayViewAnchorPane);
        fakeScreenController.add(startNextDayView);


        // home stuff ↓
        FakeScreen atHomeView = new FakeScreen("atHomeView");
        atHomeView.addFXMLElement(atHomeViewAnchorPane);
        atHomeView.addFXMLElement(playerHomeStatsAnchorPane);
        fakeScreenController.add(atHomeView);

        FakeScreen studyView = new FakeScreen("studyView");
        studyView.addFXMLElement(studyViewAnchorPane);
        studyView.addFXMLElement(playerHomeStatsAnchorPane);
        studyView.addFXMLElement(goBackBtn);
        studyView.addFXMLElement(homeActionViewAnchorPane);
        fakeScreenController.add(studyView);

        FakeScreen hangOutView = new FakeScreen("hangOutView");
        hangOutView.addFXMLElement(hangOutViewAnchorPane);
        hangOutView.addFXMLElement(playerHomeStatsAnchorPane);
        hangOutView.addFXMLElement(goBackBtn);
        hangOutView.addFXMLElement(homeActionViewAnchorPane);
        fakeScreenController.add(hangOutView);

        FakeScreen gymView = new FakeScreen("gymView");
        gymView.addFXMLElement(gymViewAnchorPane);
        gymView.addFXMLElement(playerHomeStatsAnchorPane);
        gymView.addFXMLElement(goBackBtn);
        gymView.addFXMLElement(homeActionViewAnchorPane);
        fakeScreenController.add(gymView);

        FakeScreen shoppingView = new FakeScreen("shoppingView");
        shoppingView.addFXMLElement(shoppingViewAnchorPane);
        shoppingView.addFXMLElement(playerHomeStatsAnchorPane);
        shoppingView.addFXMLElement(goBackBtn);
        shoppingView.addFXMLElement(homeActionViewAnchorPane);
        fakeScreenController.add(shoppingView);

        FakeScreen shoppingUpgradeView = new FakeScreen("shoppingUpgradeView");
        shoppingUpgradeView.addFXMLElement(shoppingUpgradeViewAnchorPane);
        shoppingUpgradeView.addFXMLElement(playerHomeStatsAnchorPane);
        shoppingUpgradeView.addFXMLElement(goBackBtn);
        fakeScreenController.add(shoppingUpgradeView);

        FakeScreen bedView = new FakeScreen("bedView");
        bedView.addFXMLElement(bedViewAnchorPane);
        bedView.addFXMLElement(playerHomeStatsAnchorPane);
        bedView.addFXMLElement(goBackBtn);
        bedView.addFXMLElement(homeActionViewAnchorPane);
        fakeScreenController.add(bedView);

        fakeScreenController.activate("startView");
    }

    public ArrayList<Timer> getAllTimers() {
        return allTimers;
    }

    @FXML
    public void startBtnClick(ActionEvent actionEvent) {
        jobGameInit();
    }

    private void jobGameInit() {
        game.setCurrentJobGame(new JobGame(game.getPlayer()));
        fakeScreenController.activate("playView");
        game.getCurrentJobGame().start(new onTimerUpdateTask());
        System.out.println(game.getPlayer().getModifStats().getAll().toString());
        updatePlayView();
    }

    public class onTimerUpdateTask implements Runnable {
        @Override
        public void run() {
            if (game.getCurrentJobGame().getTimeElapsed() >= game.getCurrentJobGame().getEndTime()) {
                fakeScreenController.activate("playEndView");
                game.getCurrentJobGame().getTimer().cancel();
                txtInput.clear();
                game.getCurrentJobGame().onEnd();
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
        moneyLbl.setText("Money: " + game.getPlayer().getBaseStats().get(StatTypes.MONEY));
        staminaLbl.setText("Stamina: " + game.getPlayer().getBaseStats().get(StatTypes.STAMINA) + "/" + game.getPlayer().getBaseStats().get(StatTypes.MAX_STAMINA));
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
        if (action.equals("bed")) {
            fakeScreenController.activate("startNextDayView");
        } else {
            updatePlayerStatsAnchorPane();
            updateHomeActionViews();
        }
    }

    private void updateHomeActionViews() {
        String nameOfCurrentHomeView = fakeScreenController.getCurrentScreen().getName();
        nameOfCurrentHomeView = nameOfCurrentHomeView.substring(0, nameOfCurrentHomeView.indexOf("View"));
        confirmBtn.setDisable(false);
        homeActionStatEffectTextArea.clear();
        if (!game.getPlayer().getHomeActionStatCost(nameOfCurrentHomeView).getString().isEmpty()) {
            homeActionStatEffectTextArea.appendText("Stat Cost:\n" + game.getPlayer().getHomeActionStatCost(nameOfCurrentHomeView).getString());
        }
        if (!game.getPlayer().getHomeActionStatBonus(nameOfCurrentHomeView).getString().isEmpty()) {
            homeActionStatEffectTextArea.appendText("\nStat Bonus:\n" + game.getPlayer().getHomeActionStatBonus(nameOfCurrentHomeView).getString());
        }
        homeActionDescriptionTextArea.setText(game.getPlayer().getHomeActionDescription(nameOfCurrentHomeView));
        if (!game.getPlayer().getBaseStats().canAdd(game.getPlayer().getHomeActionStatCost(nameOfCurrentHomeView))) {
            confirmBtn.setDisable(true);
        }
    }

    @FXML
    public void startNextDayBtnClick(ActionEvent actionEvent) {
        jobGameInit();
    }
}