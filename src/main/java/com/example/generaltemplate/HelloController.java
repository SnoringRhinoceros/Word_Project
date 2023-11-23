package com.example.generaltemplate;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class HelloController {
    @FXML
    public TextField txtInput;
    @FXML
    public Button startPlayBtn;
    @FXML
    public AnchorPane startViewAnchorPane,  playViewAnchorPane, endPlayViewAnchorPane;
    @FXML
    public Label timeLbl, wordToGuessLbl;
    private final FakeScreenController fakeScreenController = new FakeScreenController();
    public final static ArrayList<Timer> allTimers = new ArrayList<>();
    private JobGame currentJobGame;

    @FXML
    public void initialize() {
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
    }
    public class onTimerUpdateTask implements Runnable {
        @Override
        public void run() {
            if (currentJobGame.getTimeElapsed() >= 5) {
                fakeScreenController.activate("playEndView");
                currentJobGame.getTimer().cancel();
            }
            Platform.runLater(this::updateFXMLElementsOnTimerUpdate);
        }

        private void updateFXMLElementsOnTimerUpdate() {
            updatePlayView();
        }
    }

    public void updatePlayView() {
        timeLbl.setText("Time: " + currentJobGame.getTimeElapsed());
        wordToGuessLbl.setText(currentJobGame.getHiddenChosenWord());
    }

    @FXML
    public void submitBtnClick(ActionEvent actionEvent) {

    }

}