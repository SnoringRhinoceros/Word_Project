package com.example.generaltemplate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class HelloController {

    @FXML
    public Label lblDisplay;

    @FXML
    public TextField txtInput;
    private TimerTask task;
    private int currentTime;

    @FXML
    public void startBtnClick(ActionEvent actionEvent) {
        new Timer().schedule(new EndingGameTask(), 0 , 1000);
    }

    public class EndingGameTask extends TimerTask {
        @Override
        public void run() {
            System.out.println("Start time is at :" +
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(scheduledExecutionTime()), ZoneId.systemDefault()));
            System.out.println("End game in 1 seconds");
            try {
                TimeUnit.SECONDS.sleep(1);
                currentTime++;
                lblDisplay.setText(String.valueOf(currentTime));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}