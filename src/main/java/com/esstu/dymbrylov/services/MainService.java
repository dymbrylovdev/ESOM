package com.esstu.dymbrylov.services;

import com.esstu.dymbrylov.controllers.ModalController;
import com.esstu.dymbrylov.DataTable;
import com.esstu.dymbrylov.model.Material;
import com.esstu.dymbrylov.model.Samples;
import com.esstu.dymbrylov.model.SamplesReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;



public class MainService extends SuperService {
    MaterialService materialService = new MaterialService();
    AdditiveService additiveService = new AdditiveService();
    ModalController modalWindow = new ModalController();

    protected static PreparedStatement preparedStatement;
    protected static Connection connect;
    protected static Statement statement;
    protected static ResultSet resultSet;


    public Map.Entry<Boolean, String> saveFormData(Samples samples) {
        Map.Entry<Boolean, String> response = Map.entry(false, "Произошла ошибка при сохранении образца");

        Map.Entry<Boolean, String> resultCode = getSamplesById(samples.getId());
        if (resultCode.getKey()) {
            return response = Map.entry(false, resultCode.getValue());
        }
        try {
            String query = "INSERT INTO samples(id, id_material, id_additive, layer_count, percent, photo_after, photo_before, photo_after_test, photo_reverse) VALUES(?,?,?,?,?,?,?,?,?)";
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
            if (samples.getPhoto_after() != null) {
                preparedStatement.setString(6, samples.getPhoto_after());
            }
            if (samples.getPhoto_before() != null) {
                preparedStatement.setString(7, samples.getPhoto_before());
            }
            if (samples.getPhoto_after_test() != null) {
                preparedStatement.setString(8, samples.getPhoto_after_test());
            }
            if (samples.getPhoto_reverse() != null) {
                preparedStatement.setString(9, samples.getPhoto_reverse());
            }
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
        try {
            connect = ConnectorDB();
            String query = "UPDATE samples SET id_material=?, id_additive=?, layer_count=?, percent=?, photo_after=?, photo_before=?, photo_after_test=?, photo_reverse=? WHERE id=?";
            preparedStatement = connect.prepareStatement(query);
            if (samples.getId_material() != null) {
                preparedStatement.setInt(1, samples.getId_material());
            }
            if (samples.getId_additive() != null) {
                preparedStatement.setInt(2, samples.getId_additive());
            }
            preparedStatement.setString(3, samples.getLayer_count());
            preparedStatement.setString(4, samples.getPercent());
            if (samples.getPhoto_after() != null) {
                preparedStatement.setString(5, samples.getPhoto_after());
            }
            if (samples.getPhoto_before() != null) {
                preparedStatement.setString(6, samples.getPhoto_before());
            }
            if (samples.getPhoto_after_test() != null) {
                preparedStatement.setString(7, samples.getPhoto_after_test());
            }
            if (samples.getPhoto_reverse() != null) {
                preparedStatement.setString(8, samples.getPhoto_reverse());
            }
            preparedStatement.setString(9, samples.getId());
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                response = Map.entry(true, "Образец обновлен");
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
        long time = System.currentTimeMillis();
        String query = "SELECT * FROM samples" + createQueryFilter(strFilterSamples, strFilterMaterial, strFilterAdditive);
        query += " limit " + from + "," + to;
        connect = ConnectorDB();
        try (PreparedStatement preparedStatement = connect.prepareStatement(query)) {
            int count = from + 1;
            ResultSet resultSet = preparedStatement.executeQuery();
            preparedStatement.clearParameters();
            while (resultSet.next()) {
                Image newImage1 = null;
                Image newImage2 = null;
                Image newImage3 = null;
                Image newImage4 = null;

                if (resultSet.getString("photo_after") != null) {
                    {
                        newImage1 = new Image(new File(resultSet.getString("photo_after")).toURI().toURL().toString());
                    }
                }
                if (resultSet.getString("photo_before") != null) {
                    {
                        newImage2 = new Image(new File(resultSet.getString("photo_before")).toURI().toURL().toString());
                    }
                }
                if (resultSet.getString("photo_after_test") != null) {
                    {
                        newImage3 = new Image(new File(resultSet.getString("photo_after_test")).toURI().toURL().toString());
                    }
                }
                if (resultSet.getString("photo_reverse") != null) {
                    {
                        newImage4 = new Image(new File(resultSet.getString("photo_reverse")).toURI().toURL().toString());
                    }
                }
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
        System.out.println((double) (System.currentTimeMillis() - time));
        return dataList;
    }

    public Map.Entry<Boolean, String> deleteSamplesById(List<DataTable> items) {
        Map.Entry<Boolean, String> result = Map.entry(false, "Ошибка удаления");
        connect = ConnectorDB();
        try {
            int response = 0;
            String parent = System.getProperty("user.dir");
            File dir = new File(parent, "img");
            for (DataTable item : items) {
                List<Image> list = new ArrayList<>(Arrays.asList(
                        item.getPhotoAfter().getImage() ,
                        item.getPhotoBefore().getImage(),
                        item.getPhotoAfterTest().getImage(),
                        item.getPhotoReverse().getImage()
                ));
                for (Image img : list) {
                    if (img != null) {
                        String imgFile = new File(img.getUrl()).getName();
                        File newImgFIle = new File(dir, imgFile);
                        if (newImgFIle.delete()) {
                            System.out.println("Deleted the file: " + newImgFIle.getName());
                        } else {
                            System.out.println("Failed to delete the file.");
                        }
                    }
                }
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
            try {
                String query2 = "create table samples (id text not null constraint samples_pk primary key, id_material integer REFERENCES material(id), id_additive integer REFERENCES additive(id), layer_count text, percent text, photo_after text, photo_before text, photo_after_test text, photo_reverse text);";
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

    public List<Object> getAllSamplesFromJasper() {
        List<Object> list = new ArrayList();
        connect = ConnectorDB();
        try {
            String sql = "select *, additive.name as name_additive, material.name as name_material from samples as s LEFT JOIN additive  ON additive.id  = s.id_additive LEFT JOIN material  ON material.id  = s.id_material";
            preparedStatement = connect.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name_material = resultSet.getString("name_material");
                String name_additive = resultSet.getString("name_additive");
                String layer_count = resultSet.getString("layer_count");
                String percent = resultSet.getString("percent");
                String photo_after = resultSet.getString("photo_after");
                String photo_before = resultSet.getString("photo_before");
                String photo_after_test = resultSet.getString("photo_after_test");
                String photo_reverse = resultSet.getString("photo_reverse");

                SamplesReport material = new SamplesReport(id, name_material, name_additive,
                        layer_count,percent,photo_after,photo_before,photo_after_test,photo_reverse);
                list.add(material);
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
        return list;
    }


    public void updateDataMaterial() {
        connect = ConnectorDB();
        int count = 0;
        try {
            String query = "UPDATE samples SET id_material = NULL WHERE id_material = ?;";
            preparedStatement = connect.prepareStatement(query);
            preparedStatement.executeQuery();
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
    }
}
