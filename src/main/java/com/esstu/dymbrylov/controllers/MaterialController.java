package com.esstu.dymbrylov.controllers;

import com.esstu.dymbrylov.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class MaterialController extends MainController implements Initializable {

    @FXML
    private TextField text_material;

    Connection connect;

    PreparedStatement preparedStatement;
    @FXML
    private void getNameAndSetData(){
        connect = ConnectorDB();
        String materialName = text_material.getText();
        String query = "INSERT INTO material(name) VALUES(?)";
        try  {
            preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, materialName);
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                JOptionPane.showMessageDialog(null, "Материал добавлен");
            }else {
                JOptionPane.showMessageDialog(null, "Произошла ошибка при сохранении материала");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
