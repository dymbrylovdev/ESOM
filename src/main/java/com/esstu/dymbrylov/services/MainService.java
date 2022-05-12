package com.esstu.dymbrylov.services;

import com.esstu.dymbrylov.controllers.ModalController;
import com.esstu.dymbrylov.DataTable;
import com.esstu.dymbrylov.model.Samples;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainService extends SuperService {
    MaterialService materialService = new MaterialService();
    AdditiveService additiveService = new AdditiveService();
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

    public Map.Entry<Boolean, String> saveFormData(Samples samples) {
        Map.Entry<Boolean, String> response = Map.entry(false, "Произошла ошибка при сохранении образца");

        Map.Entry<Boolean, String> resultCode = getSamplesById(samples.getId());
        if (resultCode.getKey()) {
            return response = Map.entry(false, resultCode.getValue());
        }
        try {
            String query = "UPDATE samples SET id=?, id_material=?, id_additive=?, layer_count=?, percent=?, photo_after=?, photo_before=?, photo_after_test=?, photo_reverse=? WHERE id=?";
            connect = ConnectorDB();
            preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, samples.getId());
            if (samples.getId_material() != null) {
                preparedStatement.setInt(2, samples.getId_material());
            }
            if (samples.getId_additive() != null) {
                preparedStatement.setInt(3, samples.getId_additive());
            }
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

    public Map.Entry<Boolean, String> updateFormData(Samples samples) {
        Map.Entry<Boolean, String> response = Map.entry(false, "Произошла ошибка при обновлении данных");
        return response;
    }
    public Map.Entry<Boolean, String> getSamplesById(String id) {
        Map.Entry<Boolean, String> response = Map.entry(false, "");
        connect = ConnectorDB();
        String query = "SELECT * from samples where id = ?";
        try {
            preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                response = Map.entry(true, "Образец под номером \"" + resultSet.getString("id") + "\" уже сушествует");
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

    public ObservableList<DataTable> getAllSamples(int from, int to,
                                                   String strFilterSamples,
                                                   String strFilterMaterial,
                                                   String strFilterAdditive
    ) {
        ObservableList<DataTable> dataList = FXCollections.observableArrayList();

        String query = "SELECT * FROM samples" + createQueryFilter(strFilterSamples, strFilterMaterial, strFilterAdditive);
        query += " limit " + from + "," + to;
        connect = ConnectorDB();
        try (PreparedStatement preparedStatement = connect.prepareStatement(query)) {
            int count = from + 1;
            ResultSet resultSet = preparedStatement.executeQuery();
            preparedStatement.clearParameters();
            while (resultSet.next()) {
                Image newImage1 = new Image(new File(resultSet.getString("photo_after")).toURI().toURL().toString());
                Image newImage2 = new Image(new File(resultSet.getString("photo_before")).toURI().toURL().toString());
                Image newImage3 = new Image(new File(resultSet.getString("photo_after_test")).toURI().toURL().toString());
                Image newImage4 = new Image(new File(resultSet.getString("photo_reverse")).toURI().toURL().toString());
                ImageView photo_after = setParamsWithImg(newImage1);
                ImageView photo_before = setParamsWithImg(newImage2);
                ImageView photo_after_test = setParamsWithImg(newImage3);
                ImageView photo_reverse = setParamsWithImg(newImage4);
                int id_material = resultSet.getInt("id_material");
                int id_additive = resultSet.getInt("id_additive");
                String material = id_material != 0 ? materialService.getMaterialById(id_material).getName() : "-";
                String additive = id_additive != 0 ? additiveService.getAdditiveById(id_additive).getName() : "-";

                DataTable data = new DataTable(
                        count, resultSet.getString("id"), material, additive, resultSet.getString("layer_count"),
                        resultSet.getString("percent"), photo_after, photo_before, photo_after_test, photo_reverse);
                dataList.add(data);
                count += 1;
            }
        } catch (SQLException | MalformedURLException e) {
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

    public Map.Entry<Boolean, String> deleteSamplesById(List<DataTable> items) {
        Map.Entry<Boolean, String> result = Map.entry(false, "Ошибка удаления");
        connect = ConnectorDB();
        try {
            int response = 0;
            for (DataTable item : items) {
                String strItem = "id=\'" + item.getId() + "\'";
                String query = "DELETE FROM samples where " + strItem;
                preparedStatement = connect.prepareStatement(query);
                response = preparedStatement.executeUpdate();
                preparedStatement.clearParameters();
            }
            if (response == 1) {
                result = Map.entry(true, "Образец(ы) удален(ы)");
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
        return result;
    }

    public Integer getCountPage(String strFilterSamples,
                                String strFilterMaterial,
                                String strFilterAdditive) {
        connect = ConnectorDB();
        int count = 0;
        try {
            String query = "SELECT count(*) from samples" + createQueryFilter(strFilterSamples, strFilterMaterial, strFilterAdditive);
            preparedStatement = connect.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            count = resultSet.getInt(1);
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
        return count;
    }

    private String createQueryFilter(String strFilterSamples,
                                     String strFilterMaterial,
                                     String strFilterAdditive) {
        String query = "";
        Integer idMaterial = materialService.getMaterialByName(strFilterMaterial) == null ?
                null : materialService.getMaterialByName(strFilterMaterial).getId();
        Integer idAdditive = additiveService.getAdditiveByName(strFilterAdditive) == null ?
                null : additiveService.getAdditiveByName(strFilterAdditive).getId();

        if (!Objects.equals(strFilterSamples, "") || !Objects.equals(strFilterMaterial, "") || !Objects.equals(strFilterAdditive, "")) {
            query += " where ";
            if (!Objects.equals(strFilterSamples, "") && !Objects.equals(strFilterMaterial, "") && !Objects.equals(strFilterAdditive, "")) {
                query += "id=\'" + strFilterSamples + "\'" + "and id_material=" + idMaterial + " and id_additive=" + idAdditive;
            } else if (!Objects.equals(strFilterSamples, "") && !Objects.equals(strFilterMaterial, "")) {
                query += "id=\'" + strFilterSamples + "\'" + "and id_material=" + idMaterial;
            } else if (!Objects.equals(strFilterSamples, "") && !Objects.equals(strFilterAdditive, "")) {
                query += "id=\'" + strFilterSamples + "\'" + " and id_additive=" + idAdditive;
            } else if (!Objects.equals(strFilterMaterial, "") && !Objects.equals(strFilterAdditive, "")) {
                query += "id_material=" + idMaterial + " and id_additive=" + idAdditive;
            } else if (!Objects.equals(strFilterSamples, "")) {
                query += "id=\'" + strFilterSamples + "\'";
            } else if (!Objects.equals(strFilterMaterial, "")) {
                query += "id_material=" + idMaterial;
            } else if (!Objects.equals(strFilterAdditive, "")) {
                query += "id_additive=" + idAdditive;
            }
        }
        return query;
    }

    public static ImageView setParamsWithImg(Image img) {
        ImageView view = new ImageView(img);
        view.setFitHeight(200);
        view.setFitWidth(200);
        return view;
    }
}
