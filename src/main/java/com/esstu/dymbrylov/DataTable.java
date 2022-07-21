package com.esstu.dymbrylov;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.ImageView;

public class DataTable {

    private final SimpleIntegerProperty count;
    private final SimpleStringProperty id;
    private final SimpleStringProperty nameMaterial;
    private final SimpleStringProperty nameAdditive;
    private final SimpleStringProperty layerCount;
    private final SimpleStringProperty percent;

    private ImageView photoAfter;
    private ImageView photoBefore;
    private ImageView photoAfterTest;
    private ImageView photoReverse;

    public DataTable(Integer count, String id, String material, String additive, String layer_count, String percent, ImageView photoAfter, ImageView photoBefore, ImageView photoAfterTest, ImageView photoReverse) {
        this.count = new SimpleIntegerProperty(count);
        this.id = new SimpleStringProperty(id);
        this.nameMaterial = new SimpleStringProperty(material);
        this.nameAdditive = new SimpleStringProperty(additive);
        this.layerCount = new SimpleStringProperty(layer_count);
        this.percent = new SimpleStringProperty(percent);
        this.photoAfter = photoAfter;
        this.photoBefore = photoBefore;
        this.photoAfterTest = photoAfterTest;
        this.photoReverse = photoReverse;
    }

    public int getCount() {
        return count.get();
    }

    public SimpleIntegerProperty countProperty() {
        return count;
    }

    public void setCount(int count) {
        this.count.set(count);
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

    public String getNameMaterial() {
        return nameMaterial.get();
    }

    public SimpleStringProperty nameMaterialProperty() {
        return nameMaterial;
    }

    public void setNameMaterial(String nameMaterial) {
        this.nameMaterial.set(nameMaterial);
    }

    public String getNameAdditive() {
        return nameAdditive.get();
    }

    public SimpleStringProperty nameAdditiveProperty() {
        return nameAdditive;
    }

    public void setNameAdditive(String nameAdditive) {
        this.nameAdditive.set(nameAdditive);
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
