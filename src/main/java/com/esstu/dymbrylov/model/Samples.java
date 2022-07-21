package com.esstu.dymbrylov.model;



public class Samples {
    private final String id;
    private final Integer id_material;
    private final Integer id_additive;
    private final String layer_count;
    private final String percent;
    private final String photo_after;
    private final String photo_before;
    private final String photo_after_test;
    private final String photo_reverse;

    public Samples(String id, Integer id_material,
                   Integer id_additive, String layer_count,
                   String percent, String photo_after, String photo_before,
                   String photo_after_test, String photo_reverse) {
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

    public Integer getId_material() {
        return id_material;
    }

    public Integer getId_additive() {
        return id_additive;
    }

    public String getLayer_count() {
        return layer_count;
    }

    public String getPercent() {
        return percent;
    }

    public String getPhoto_after() {
        return photo_after;
    }

    public String getPhoto_before() {
        return photo_before;
    }

    public String getPhoto_after_test() {
        return photo_after_test;
    }

    public String getPhoto_reverse() {
        return photo_reverse;
    }


    @Override
    public String toString() {
        return "Samples{" +
                "id='" + id + '\'' +
                ", id_material=" + id_material +
                ", id_additive=" + id_additive +
                ", layer_count='" + layer_count + '\'' +
                ", percent='" + percent + '\'' +
                ", photo_after='" + photo_after + '\'' +
                ", photo_before='" + photo_before + '\'' +
                ", photo_after_test='" + photo_after_test + '\'' +
                ", photo_reverse='" + photo_reverse + '\'' +
                '}';
    }
}
