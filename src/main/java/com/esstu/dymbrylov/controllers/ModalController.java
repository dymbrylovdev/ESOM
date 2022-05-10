package com.esstu.dymbrylov.controllers;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ModalController {


    // Показать информационное предупреждение с текстом заголовка
    public void showAlertInformation(String massage) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Сообщение");
        alert.setHeaderText(null);
        alert.setContentText(massage);
        alert.showAndWait();
    }

    // Показать информационное предупреждение с текстом заголовка
    public void showAlertWarning(String massage) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Сообщение");
        alert.setHeaderText(null);
        alert.setContentText(massage);
        alert.showAndWait();
    }


    public void showError(Exception e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error alert");
        alert.setHeaderText(e.getMessage());

        VBox dialogPaneContent = new VBox();

        Label label = new Label("Stack Trace:");

        String stackTrace = this.getStackTrace(e);
        TextArea textArea = new TextArea();
        textArea.setText(stackTrace);

        dialogPaneContent.getChildren().addAll(label, textArea);

        alert.getDialogPane().setContent(dialogPaneContent);

        alert.showAndWait();
    }
    private String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String s = sw.toString();
        return s;
    }
}
