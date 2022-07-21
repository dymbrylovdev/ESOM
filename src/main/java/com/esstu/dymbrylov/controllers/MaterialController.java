package com.esstu.dymbrylov.controllers;

import com.esstu.dymbrylov.services.MaterialService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;


public class MaterialController extends MaterialService implements Initializable {
    ModalController modalWindow = new ModalController();

    @FXML
    private TextField text_material;
    @FXML
    private void setNameAndSetData() {
        Map.Entry<Boolean, String> response = setMaterialByName(text_material.getText());
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
