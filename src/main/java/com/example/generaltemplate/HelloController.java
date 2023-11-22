package com.example.generaltemplate;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    public Label lblDisplay;
    @FXML
    public TextField txtInput;
    @FXML
    public Button startPlayBtn;
    private int timeElapsed;
    private final FakeScreenController fakeScreenController = new FakeScreenController();
    private final ArrayList<Timer> allTimers = new ArrayList<>();

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
    public void startBtnClick(ActionEvent actionEvent) throws IOException {
        Timer timer = new Timer();
        allTimers.add(timer);
        fakeScreenController.activate("playView");
        timer.schedule(new TimerTicker(), 0 , 1000);
        System.out.println(getRandWord());
    }

    private String getRandWord() throws IOException {
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
    }

    private int generateRandNum(int minInc, int maxInc) {
        return minInc + (int) (Math.random()*((maxInc - minInc) + 1));
    }

    public class TimerTicker extends TimerTask {
        @Override
        public void run() {
            System.out.println("Start time is at :" +
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(scheduledExecutionTime()), ZoneId.systemDefault()));
            try {
                timeElapsed++;
                if (timeElapsed >= 5) {
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
            lblDisplay.setText("Time: " + timeElapsed);
        }
    }

    public ArrayList<Timer> getAllTimers() {
        return allTimers;
    }
}