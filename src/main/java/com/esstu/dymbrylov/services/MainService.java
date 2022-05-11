package com.esstu.dymbrylov.services;

import com.esstu.dymbrylov.MainApplication;
import com.esstu.dymbrylov.MainController;
import com.esstu.dymbrylov.controllers.ModalController;
import com.esstu.dymbrylov.DataTable;
import com.esstu.dymbrylov.model.Samples;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import javax.swing.text.Element;
import javax.swing.text.html.ImageView;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainService {
    ModalController modalWindow = new ModalController();

    protected static PreparedStatement preparedStatement;
    protected static Connection connect;
    protected static Statement statement;
    protected static ResultSet resultSet;

    public Connection ConnectorDB() {
        try {
            connect = DriverManager.getConnection("jdbc:sqlite:maindb.sqlite");
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE,
                    LocalDateTime.now() + ": Could not connect to SQLite DB at ");
            modalWindow.showError(e);
            return null;
        }
        return connect;
    }

    public Map.Entry<Boolean, String> setFormData(Samples samples) {
        Map.Entry<Boolean, String> response = Map.entry(false, "Произошла ошибка при сохранении образца");
        if (samples.getId_material() == null || samples.getId_additive() == null){
            return response = Map.entry(false, "Заполните поля");
        }
        Map.Entry<Boolean, String> resultCode = getSamplesById(samples.getId());
        if (resultCode.getKey()){
            return response = Map.entry(false, resultCode.getValue());
        }
        try  {
            String query = "INSERT INTO samples(id, id_material, id_additive, layer_count, percent, photo_after, photo_before, photo_after_test, photo_reverse) VALUES(?,?,?,?,?,?,?,?,?)";
            connect = ConnectorDB();
            preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, samples.getId());
            preparedStatement.setInt(2, samples.getId_material());
            preparedStatement.setInt(3, samples.getId_additive());
            preparedStatement.setString(4, samples.getLayer_count());
            preparedStatement.setString(5, samples.getPercent());
            preparedStatement.setString(6, samples.getPhoto_after());
            preparedStatement.setString(7, samples.getPhoto_before());
            preparedStatement.setString(8, samples.getPhoto_after_test());
            preparedStatement.setString(9, samples.getPhoto_reverse());
            int result = preparedStatement.executeUpdate();

            if (result == 1) {
                response = Map.entry(true, "Образец добавлен");
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

    public Map.Entry<Boolean, String> getSamplesById(String id){
        Map.Entry<Boolean, String> response = Map.entry(false, "");
        connect = ConnectorDB();
        String query = "SELECT * from samples where id = ?";
        try  {
            preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                response = Map.entry(true, "Образец под номером \""+resultSet.getString("id")+"\" уже сушествует");
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


    public ObservableList<DataTable> getAllSamples(int from, int to) {
        connect = ConnectorDB();
        ObservableList<DataTable> dataList = FXCollections.observableArrayList();
        String query = "SELECT * from samples limit "+ from + "," + to;
        try  {
            preparedStatement = connect.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String url1 = resultSet.getString("photo_after");
                String url2 = resultSet.getString("photo_before");
                String url3 = resultSet.getString("photo_after_test");
                String url4 = resultSet.getString("photo_reverse");
                ImageView photo_after = Objects.equals(url1,"") ? null : new ImageView((Element) new Image(this.getClass().getResourceAsStream(url1)));
                ImageView photo_before = Objects.equals(url2,"") ? null : new ImageView( (Element) new Image(this.getClass().getResourceAsStream(url2)));
                ImageView photo_after_test = Objects.equals(url3,"") ? null : new ImageView( (Element) new Image(this.getClass().getResourceAsStream(url3)));
                ImageView photo_reverse = Objects.equals(url4,"") ? null : new ImageView((Element) new Image(this.getClass().getResourceAsStream("")));

                DataTable data = new DataTable(
                        resultSet.getString("id"), resultSet.getInt("id_material"),
                        resultSet.getInt("id_additive"), resultSet.getString("layer_count"),
                        resultSet.getString("percent"), photo_after, photo_before, photo_after_test, photo_reverse);
                dataList.add(data);
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
        return dataList;
    }



    public Integer getCountPage() {
        connect = ConnectorDB();
        int count = 0;
        try {
            String query = "SELECT count(*) from samples";
            preparedStatement = connect.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            count = resultSet.getInt(1);
        }catch (SQLException e) {
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
        return count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(modalWindow);
    }
}
