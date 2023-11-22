package com.example.generaltemplate;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class HelloController {

    @FXML
    public Label lblDisplay;

    @FXML
    public TextField txtInput;
    @FXML
    public Button startPlayBtn;
    private TimerTask task;
    private int currentTime;
    private final FakeScreenController fakeScreenController = new FakeScreenController();
    private ArrayList<Timer> allTimers = new ArrayList<>();

    @FXML
    public void initialize() {
        FakeScreen startView = new FakeScreen("startView");
        startView.addFXMLElement(startPlayBtn);
        fakeScreenController.add(startView);

        FakeScreen playingView = new FakeScreen("playView");
        playingView.addFXMLElement(lblDisplay);
        playingView.addFXMLElement(txtInput);
        fakeScreenController.add(playingView);

        FakeScreen playEndView = new FakeScreen("playEndView");
        fakeScreenController.add(playEndView);

        fakeScreenController.activate("startView");
    }

    @FXML
    public void startBtnClick(ActionEvent actionEvent) {
        Timer timer = new Timer();
        allTimers.add(timer);
        timer.schedule(new TimerTicker(), 0 , 1000);
    }

    public class TimerTicker extends TimerTask {
        @Override
        public void run() {
            System.out.println("Start time is at :" +
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(scheduledExecutionTime()), ZoneId.systemDefault()));
            try {
                currentTime++;
                if (currentTime >= 5) {
                    fakeScreenController.activate("playEndView");
                    cancel();
                }
                Platform.runLater(this::tick);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        private void tick() {
            lblDisplay.setText("Time: " + currentTime);
        }
    }

    public ArrayList<Timer> getAllTimers() {
        return allTimers;
    }
}