package com.example.generaltemplate;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Timer;

public class HelloController {
    @FXML
    public TextField txtInput;
    @FXML
    public Button startPlayBtn, submitBtn, studyBtn, hangOutBtn, gymBtn, shoppingBtn, bedBtn, goBackBtn,
            confirmBtn, studyHomeActionToUpgradeBtn, hangOutHomeActionToUpgradeBtn, gymHomeActionToUpgradeBtn,
            chosenHomeActionToUpgradeBtn, confirmShoppingUpgradeBtn;
    @FXML
    public AnchorPane startViewAnchorPane,  playViewAnchorPane, playEndViewAnchorPane, atHomeViewAnchorPane,
            nextDayAnchorPane, studyViewAnchorPane, hangOutViewAnchorPane, gymViewAnchorPane, shoppingViewAnchorPane,
            shoppingUpgradeViewAnchorPane, bedViewAnchorPane, playerHomeStatsAnchorPane, startNextDayViewAnchorPane,
            homeActionViewAnchorPane, fullScreenImgViewAnchorPane;
    @FXML
    public Label timeLbl, wordToGuessLbl, statusTxtLbl, moneyLbl, staminaLbl, daysLeftLbl, votersGainedLbl;
    @FXML
    public TextArea guessedLettersTextArea, playEndStatsTextArea, homeActionStatEffectTextArea,
            homeActionDescriptionTextArea, upgradeStatEffectTextArea, upgradeDescriptionTextArea, endingsReachedTextArea;
    @FXML
    public ButtonBar homeActionToUpgradeBtnBar;
    @FXML
    public ImageView fullScreenImageView;
    private final FakeScreenController fakeScreenController = new FakeScreenController();
    public final static ArrayList<Timer> allTimers = new ArrayList<>();
    public final static int BASE_END_TIME = 60;     // in seconds
    private Game game;

    @FXML
    public void initialize() {
        guessedLettersTextArea.setEditable(false);
        playEndStatsTextArea.setEditable(false);
        homeActionStatEffectTextArea.setEditable(false);
        homeActionDescriptionTextArea.setEditable(false);
        upgradeStatEffectTextArea.setEditable(false);
        upgradeDescriptionTextArea.setEditable(false);
        endingsReachedTextArea.setEditable(false);

        statusTxtLbl.setText("");
        wordToGuessLbl.setText("");
        timeLbl.setText("");
        moneyLbl.setText("");
        staminaLbl.setText("");

        game = new Game();

        FakeScreen startView = new FakeScreen("startView");
        startView.addFXMLElement(startViewAnchorPane);
        startView.addFXMLElement(endingsReachedTextArea);
        fakeScreenController.add(startView);

        FakeScreen playingView = new FakeScreen("playView");
        playingView.addFXMLElement(playViewAnchorPane);
        playingView.addFXMLElement(daysLeftLbl);
        playingView.addFXMLElement(votersGainedLbl);
        fakeScreenController.add(playingView);

        FakeScreen playEndView = new FakeScreen("playEndView");
        playEndView.addFXMLElement(playEndViewAnchorPane);
        playEndView.addFXMLElement(daysLeftLbl);
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
        atHomeView.addFXMLElement(daysLeftLbl);
        fakeScreenController.add(atHomeView);

        FakeScreen studyView = new FakeScreen("studyView");
        studyView.addFXMLElement(studyViewAnchorPane);
        studyView.addFXMLElement(playerHomeStatsAnchorPane);
        studyView.addFXMLElement(goBackBtn);
        studyView.addFXMLElement(homeActionViewAnchorPane);
        studyView.addFXMLElement(daysLeftLbl);
        fakeScreenController.add(studyView);

        FakeScreen hangOutView = new FakeScreen("hangOutView");
        hangOutView.addFXMLElement(hangOutViewAnchorPane);
        hangOutView.addFXMLElement(playerHomeStatsAnchorPane);
        hangOutView.addFXMLElement(goBackBtn);
        hangOutView.addFXMLElement(homeActionViewAnchorPane);
        hangOutView.addFXMLElement(daysLeftLbl);
        fakeScreenController.add(hangOutView);

        FakeScreen gymView = new FakeScreen("gymView");
        gymView.addFXMLElement(gymViewAnchorPane);
        gymView.addFXMLElement(playerHomeStatsAnchorPane);
        gymView.addFXMLElement(goBackBtn);
        gymView.addFXMLElement(homeActionViewAnchorPane);
        gymView.addFXMLElement(daysLeftLbl);
        fakeScreenController.add(gymView);

        FakeScreen shoppingView = new FakeScreen("shoppingView");
        shoppingView.addFXMLElement(shoppingViewAnchorPane);
        shoppingView.addFXMLElement(playerHomeStatsAnchorPane);
        shoppingView.addFXMLElement(goBackBtn);
        shoppingView.addFXMLElement(homeActionViewAnchorPane);
        shoppingView.addFXMLElement(daysLeftLbl);
        fakeScreenController.add(shoppingView);

        FakeScreen shoppingUpgradeView = new FakeScreen("shoppingUpgradeView");
        shoppingUpgradeView.addFXMLElement(shoppingUpgradeViewAnchorPane);
        shoppingUpgradeView.addFXMLElement(playerHomeStatsAnchorPane);
        shoppingUpgradeView.addFXMLElement(goBackBtn);
        shoppingUpgradeView.addFXMLElement(daysLeftLbl);
        fakeScreenController.add(shoppingUpgradeView);

        FakeScreen bedView = new FakeScreen("bedView");
        bedView.addFXMLElement(bedViewAnchorPane);
        bedView.addFXMLElement(playerHomeStatsAnchorPane);
        bedView.addFXMLElement(goBackBtn);
        bedView.addFXMLElement(homeActionViewAnchorPane);
        bedView.addFXMLElement(daysLeftLbl);
        fakeScreenController.add(bedView);

        FakeScreen fullScreenImgView = new FakeScreen("fullScreenImgView");
        fullScreenImgView.addFXMLElement(fullScreenImgViewAnchorPane);

        fakeScreenController.activate("startView");
        updateEndingsReachedTextArea();
    }

    public ArrayList<Timer> getAllTimers() {
        return allTimers;
    }

    private void updateEndingsReachedTextArea() {
        endingsReachedTextArea.setText("Endings Reached (" + game.getEndingsReached().size() + "/" + PossibleEndings.values().length + "):\n");
        for (PossibleEndings endingReached: game.getEndingsReached()) {
            endingsReachedTextArea.appendText(endingReached.getName()+"\n");
        }
    }

    private void updateVotersGainedLbl() {
        votersGainedLbl.setText("Voters Gained: " + game.getPlayer().getVotersGained() + "/" + game.getVotersNeeded());
    }

    @FXML
    public void startBtnClick(ActionEvent actionEvent) {
        jobGameInit();
    }

    private void jobGameInit() {
        game.incrementDaysPassed();
        if (!game.over()) {
            game.setCurrentJobGame(new JobGame(game.getPlayer()));
            fakeScreenController.activate("playView");
            game.getCurrentJobGame().start(new onTimerUpdateTask());
            System.out.println(game.getPlayer().getModifStats().getAll().toString());
            updatePlayView();
        } else {
            handleGameEndCon();
        }
    }

    private void handleGameEndCon() {
        fakeScreenController.activate("fullScreenImgView");
        game.addEndingReached(game.getCurEnding());
        setImageViewImage(fullScreenImageView, "src/main/resources/com/example/generaltemplate/img/Full_Screens/"
                + game.getCurEnding().getName() + ".png");
        for (Node node: startNextDayViewAnchorPane.getChildren()) {
            node.setVisible(false);
        }
    }

    private void setImageViewImage(ImageView imageView, String path) {
        try {
            FileInputStream input = new FileInputStream(path);
            imageView.setImage(new Image(input));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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

    private void updateDaysLeftLbl() {
        daysLeftLbl.setText("Days left: " + game.getTimeLeft());
    }

    public void updatePlayView() {
        updateDaysLeftLbl();
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
            String legalWords = "abcdefghijklmnopqrstuvwxyz";
            if (!txtInput.getText().isEmpty() && txtInput.getText().length() == 1 && legalWords.contains(String.valueOf(txtInput.getText().charAt(0)).toLowerCase())) {
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
        updateVotersGainedLbl();
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

    private String getShoppingUpgradeBtnAction(String shoppingUpgradeBtnId) {
        return shoppingUpgradeBtnId.substring(0, (shoppingUpgradeBtnId.indexOf("HomeActionToUpgrade")));
    }

    private void updateShoppingUpgradeView() {
        confirmShoppingUpgradeBtn.setDisable(true);
        if (chosenHomeActionToUpgradeBtn != null) {
            for (Node button: homeActionToUpgradeBtnBar.getButtons()) {
                button.setDisable(button.getId().equals(chosenHomeActionToUpgradeBtn.getId()));
            }
            String action = getShoppingUpgradeBtnAction(chosenHomeActionToUpgradeBtn.getId());
            upgradeStatEffectTextArea.setText("Cost to Upgrade:\n" + game.getPlayer().getHomeActionCostToUpgrade(action)
                    + "\n\nOriginal Stat Bonus:\n"
                    + game.getPlayer().getHomeActionStatBonus(action).getString(false)
                    + "\nNew Stat Bonus:\n"
                    + game.getPlayer().getNextUpgradeStats(action).getString(false));
            upgradeDescriptionTextArea.setText(game.getPlayer().getHomeActionDescription(action));
            if (game.getPlayer().getHomeActionUpgradeBuyable(action)) {
                confirmShoppingUpgradeBtn.setDisable(false);
            }
        }
        updateDaysLeftLbl();
    }

    @FXML
    private void possibleUpgradesButtonBarClick(ActionEvent actionEvent) {
        chosenHomeActionToUpgradeBtn = (Button) actionEvent.getTarget();
        updateShoppingUpgradeView();
    }

    @FXML
    public void confirmBtnClick(ActionEvent actionEvent) {
        String action = fakeScreenController.getCurrentScreen().getName();
        action = action.substring(0, action.indexOf("View"));
        game.getPlayer().doHomeAction(action);
        if (action.equals("bed")) {
            fakeScreenController.activate("startNextDayView");
        } else if (action.equals("shopping")) {
            fakeScreenController.activate("shoppingUpgradeView");
            updateShoppingUpgradeView();
        }else {
            updateHomeActionViews();
        }
        updatePlayerStatsAnchorPane();
    }

    private void updateHomeActionViews() {
        String nameOfCurrentHomeView = fakeScreenController.getCurrentScreen().getName();
        nameOfCurrentHomeView = nameOfCurrentHomeView.substring(0, nameOfCurrentHomeView.indexOf("View"));

        homeActionStatEffectTextArea.clear();
        if (!game.getPlayer().getHomeActionStatCost(nameOfCurrentHomeView).getString(true).isEmpty()) {
            homeActionStatEffectTextArea.appendText("Stat Cost:\n" + game.getPlayer().getHomeActionStatCost(nameOfCurrentHomeView).getString(true));
        }
        if (!game.getPlayer().getHomeActionStatBonus(nameOfCurrentHomeView).getString(true).isEmpty()) {
            homeActionStatEffectTextArea.appendText("\nStat Bonus:\n" + game.getPlayer().getHomeActionStatBonus(nameOfCurrentHomeView).getString(true));
        }
        homeActionDescriptionTextArea.setText(game.getPlayer().getHomeActionDescription(nameOfCurrentHomeView)
                + "\n\nMax Total Times Doable:\n" + (game.getPlayer().getHomeActionMaxTotalUsePoints(nameOfCurrentHomeView)-game.getPlayer().getHomeActionUsePoints(nameOfCurrentHomeView)));

        confirmBtn.setDisable(false);
        if (!game.getPlayer().getBaseStats().canAdd(game.getPlayer().getHomeActionStatCost(nameOfCurrentHomeView))
        || game.getPlayer().getHomeActionUsePoints(nameOfCurrentHomeView) >= game.getPlayer().getHomeActionMaxTotalUsePoints(nameOfCurrentHomeView)) {
            confirmBtn.setDisable(true);
        }
        updateDaysLeftLbl();
    }

    @FXML
    public void startNextDayBtnClick(ActionEvent actionEvent) {
        jobGameInit();
    }

    @FXML
    public void confirmShoppingUpgradeBtnClick(ActionEvent actionEvent) {
        String action = getShoppingUpgradeBtnAction(chosenHomeActionToUpgradeBtn.getId());
        game.getPlayer().getBaseStats().add(StatTypes.MONEY, -game.getPlayer().getHomeActionCostToUpgrade(action));
        game.getPlayer().incrementUpgradePoints(action);
        updateShoppingUpgradeView();
        updatePlayerStatsAnchorPane();
    }
}