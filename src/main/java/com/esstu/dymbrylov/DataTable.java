package com.esstu.dymbrylov;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DataTable {

    private SimpleStringProperty id;
    private SimpleIntegerProperty id_material;
    private SimpleIntegerProperty id_additive;
    private SimpleStringProperty layer_count;
    private SimpleStringProperty percent;

    public DataTable(String id, Integer id_material, Integer id_additive, String layer_count, String percent) {
        this.id = new SimpleStringProperty(id);
        this.id_material = new SimpleIntegerProperty(id_material);
        this.id_additive = new SimpleIntegerProperty(id_additive);
        this.layer_count = new SimpleStringProperty(layer_count);
        this.percent = new SimpleStringProperty(percent);
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

    public int getId_material() {
        return id_material.get();
    }

    public SimpleIntegerProperty id_materialProperty() {
        return id_material;
    }

    public void setId_material(int id_material) {
        this.id_material.set(id_material);
    }

    public int getId_additive() {
        return id_additive.get();
    }

    public SimpleIntegerProperty id_additiveProperty() {
        return id_additive;
    }

    public void setId_additive(int id_additive) {
        this.id_additive.set(id_additive);
    }

    public String getLayer_count() {
        return layer_count.get();
    }

    public SimpleStringProperty layer_countProperty() {
        return layer_count;
    }

    public void setLayer_count(String layer_count) {
        this.layer_count.set(layer_count);
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
}
