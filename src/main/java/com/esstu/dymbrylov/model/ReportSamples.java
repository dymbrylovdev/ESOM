package com.esstu.dymbrylov.model;

public class ReportSamples {
    private String id;
    private String id_material;
    private String id_additive;
    private String layer_count;
    private String percent;
    private String photo_after;
    private String photo_before;
    private String photo_after_test;
    private String photo_reverse;

    public ReportSamples(String id, String id_material, String id_additive, String layer_count, String percent, String photo_after, String photo_before, String photo_after_test, String photo_reverse) {
        this.id = id;
        this.id_material = id_material;
        this.id_additive = id_additive;
        this.layer_count = layer_count;
        this.percent = percent;
        this.photo_after = photo_after;
        this.photo_before = photo_before;
        this.photo_after_test = photo_after_test;
        this.photo_reverse = photo_reverse;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_material() {
        return id_material;
    }

    public void setId_material(String id_material) {
        this.id_material = id_material;
    }

    public String getId_additive() {
        return id_additive;
    }

    public void setId_additive(String id_additive) {
        this.id_additive = id_additive;
    }

    public String getLayer_count() {
        return layer_count;
    }

    public void setLayer_count(String layer_count) {
        this.layer_count = layer_count;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getPhoto_after() {
        return photo_after;
    }

    public void setPhoto_after(String photo_after) {
        this.photo_after = photo_after;
    }

    public String getPhoto_before() {
        return photo_before;
    }

    public void setPhoto_before(String photo_before) {
        this.photo_before = photo_before;
    }

    public String getPhoto_after_test() {
        return photo_after_test;
    }

    public void setPhoto_after_test(String photo_after_test) {
        this.photo_after_test = photo_after_test;
    }

    public String getPhoto_reverse() {
        return photo_reverse;
    }

    public void setPhoto_reverse(String photo_reverse) {
        this.photo_reverse = photo_reverse;
    }

    @Override
    public String toString() {
        return "ReportSamples{" +
                "id='" + id + '\'' +
                ", id_material='" + id_material + '\'' +
                ", id_additive='" + id_additive + '\'' +
                ", layer_count='" + layer_count + '\'' +
                ", percent='" + percent + '\'' +
                ", photo_after='" + photo_after + '\'' +
                ", photo_before='" + photo_before + '\'' +
                ", photo_after_test='" + photo_after_test + '\'' +
                ", photo_reverse='" + photo_reverse + '\'' +
                '}';
    }
}
