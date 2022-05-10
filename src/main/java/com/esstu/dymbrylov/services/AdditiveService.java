package com.esstu.dymbrylov.services;

import com.esstu.dymbrylov.controllers.ModalController;
import com.esstu.dymbrylov.model.Additive;
import com.esstu.dymbrylov.model.Material;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

public class AdditiveService extends MainService {
    ModalController modalWindow = new ModalController();

    public AdditiveService() {
        super();
    }

    public ObservableList<Additive> getAllAdditive() {
        connect = ConnectorDB();
        ObservableList<Additive> additives = FXCollections.observableArrayList();
        ResultSet resultSet = null;
        String query = "SELECT * FROM additive";
        try {
            preparedStatement = connect.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Additive additive = new Additive(id, name);
                additives.add(additive);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            modalWindow.showError(e);
        } finally {
            if (connect != null) {
                try {
                    connect.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    modalWindow.showError(e);
                }
            }
        }
        return additives;
    }

    public Map.Entry<Boolean, String> setAdditiveByName(String textName) {
        ObservableList<Additive> additives = getAllAdditive();
        Map.Entry<Boolean, String> response = null;
        String query = "INSERT INTO additive(name) VALUES(?)";
        connect = ConnectorDB();
        Object[] newAdditives = additives.stream().
                filter(additive -> Objects.equals(additive.getName(), textName)).toArray();
        try {
            if (Objects.equals(textName, "")) {
                response = Map.entry(false, "Заполните поле");

            } else if (newAdditives.length == 0) {
                preparedStatement = connect.prepareStatement(query);
                preparedStatement.setString(1, textName);
                int result = preparedStatement.executeUpdate();

                if (result == 1) {
                    response = Map.entry(true, "Добавка добавлена");
                } else {
                    response = Map.entry(false, "Произошла ошибка при сохранении добавки");
                }
            } else {
                response = Map.entry(false, "Уже есть такая добавка");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            modalWindow.showError(e);
        } finally {
            if (connect != null) {
                try {
                    connect.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    modalWindow.showError(e);
                }
            }
        }
        return response;
    }

    public Integer getAdditiveByName(String name) {
        Integer idAdditive = null;
        connect = ConnectorDB();
        String query = "SELECT * from additive where name = ?";
        try {
            preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                idAdditive = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            modalWindow.showError(e);
        } finally {
            if (connect != null) {
                try {
                    connect.close(); // <-- This is important
                } catch (SQLException e) {
                    e.printStackTrace();
                    modalWindow.showError(e);
                }
            }
        }
        return idAdditive;
    }
}
