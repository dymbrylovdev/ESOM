package com.esstu.dymbrylov.services;

import com.esstu.dymbrylov.controllers.ModalController;
import com.esstu.dymbrylov.model.Additive;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

public class AdditiveService extends SuperService {
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

    public Additive getAdditiveByName(String name) {
        Additive additive = null;
        connect = ConnectorDB();
        String query = "SELECT * FROM additive WHERE name = ?";
        try {
            preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                additive = new Additive(resultSet.getInt("id"), resultSet.getString("name"));
            }
        } catch (SQLException e) {
            try {
                String query2 = "create table additive (id integer not null constraint additive_pk primary key autoincrement, name text not null);";
                connect = ConnectorDB();
                preparedStatement = connect.prepareStatement(query2);
                preparedStatement.executeUpdate();
            }catch (SQLException err) {
                e.printStackTrace();
                modalWindow.showError(e);
            }
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
        return additive;
    }

    public Additive getAdditiveById(Integer id) {
        Additive additive = null;
        connect = ConnectorDB();
        String query = "SELECT * FROM additive WHERE id = ?";
        try {
            preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                additive = new Additive(resultSet.getInt("id"), resultSet.getString("name"));
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
        return additive;
    }

    public Map.Entry<Boolean,String> deleteAdditiveByName(String name) {
        Additive additive = getAdditiveByName(name);
        Map.Entry<Boolean, String> response = Map.entry(false, "Произошла ошибка при удалении добавки");
        connect = ConnectorDB();
        String query = "DELETE FROM additive WHERE name = ?";
        try {
            preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, name);
            int result = preparedStatement.executeUpdate();
            connect.close();
            connect = ConnectorDB();
            preparedStatement.close();
            resultSet.close();
            String query2 = "UPDATE samples SET id_additive = NULL WHERE id_additive = ?;";
            preparedStatement = connect.prepareStatement(query2);
            preparedStatement.setInt(1, additive.getId());
            preparedStatement.executeUpdate();
            if (result == 1) {
                response = Map.entry(true, "Доюавка удалена");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            modalWindow.showError(e);
        }finally {
            if (connect != null) {
                try {
                    connect.close();
                    preparedStatement.close();
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    modalWindow.showError(e);
                }
            }
        }
        return response;
    }
}
