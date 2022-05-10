package com.esstu.dymbrylov.controllers;

import com.esstu.dymbrylov.services.AdditiveService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;


public class AdditiveController extends AdditiveService implements Initializable {
    ModalController modalWindow = new ModalController();

    @FXML
    private TextField text_additive;

    @FXML
    public void getNameAndSetData() {
        Map.Entry<Boolean, String> response = setAdditiveByName(text_additive.getText());
        if (response.getKey()){
            modalWindow.showAlertInformation(response.getValue());
        }else {
            modalWindow.showAlertWarning(response.getValue());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
