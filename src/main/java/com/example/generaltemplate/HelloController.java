package com.example.generaltemplate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {

    @FXML
    public Label lblDisplay;

    @FXML
    public TextField txtInput;

    @FXML
    public void handleClick(ActionEvent actionEvent) {
        System.out.println("test");
        lblDisplay.setText("test this");
        String infoFromTxtField = txtInput.getText();
        System.out.println(infoFromTxtField);

    }
}