package com.esstu.dymbrylov;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.ImageView;


public class DataTable {

    private final SimpleStringProperty id;
    private final SimpleIntegerProperty idMaterial;
    private final SimpleIntegerProperty idAdditive;
    private final SimpleStringProperty layerCount;
    private final SimpleStringProperty percent;

    private ImageView photoAfter;
    private ImageView photoBefore;
    private ImageView photoAfterTest;
    private ImageView photoReverse;

    public DataTable(String id, Integer id_material, Integer id_additive, String layer_count, String percent, ImageView photoAfter, ImageView photoBefore, ImageView photoAfterTest, ImageView photoReverse) {
        this.id = new SimpleStringProperty(id);
        this.idMaterial = new SimpleIntegerProperty(id_material);
        this.idAdditive = new SimpleIntegerProperty(id_additive);
        this.layerCount = new SimpleStringProperty(layer_count);
        this.percent = new SimpleStringProperty(percent);
        this.photoAfter = photoAfter;
        this.photoBefore = photoBefore;
        this.photoAfterTest = photoAfterTest;
        this.photoReverse = photoReverse;
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public int getIdMaterial() {
        return idMaterial.get();
    }

    public SimpleIntegerProperty idMaterialProperty() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial.set(idMaterial);
    }

    public int getIdAdditive() {
        return idAdditive.get();
    }

    public SimpleIntegerProperty idAdditiveProperty() {
        return idAdditive;
    }

    public void setIdAdditive(int idAdditive) {
        this.idAdditive.set(idAdditive);
    }

    public String getLayerCount() {
        return layerCount.get();
    }

    public SimpleStringProperty layerCountProperty() {
        return layerCount;
    }

    public void setLayerCount(String layerCount) {
        this.layerCount.set(layerCount);
    }

    public String getPercent() {
        return percent.get();
    }

    public SimpleStringProperty percentProperty() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent.set(percent);
    }

    public ImageView getPhotoAfter() {
        return photoAfter;
    }

    public void setPhotoAfter(ImageView photoAfter) {
        this.photoAfter = photoAfter;
    }

    public ImageView getPhotoBefore() {
        return photoBefore;
    }

    public void setPhotoBefore(ImageView photoBefore) {
        this.photoBefore = photoBefore;
    }

    public ImageView getPhotoAfterTest() {
        return photoAfterTest;
    }

    public void setPhotoAfterTest(ImageView photoAfterTest) {
        this.photoAfterTest = photoAfterTest;
    }

    public ImageView getPhotoReverse() {
        return photoReverse;
    }

    public void setPhotoReverse(ImageView photoReverse) {
        this.photoReverse = photoReverse;
    }
}
