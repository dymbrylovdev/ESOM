package com.esstu.dymbrylov.model;

public class SamplesReport {
    private final Integer id;
    private final String material;
    private final String additive;
    private final String layer_count;
    private final String percent;
    private final String photo_after;
    private final String photo_before;
    private final String photo_after_test;
    private final String photo_reverse;

    public SamplesReport(Integer id, String material, String additive, String layer_count, String percent, String photo_after, String photo_before, String photo_after_test, String photo_reverse) {
        this.id = id;
        this.material = material;
        this.additive = additive;
        this.layer_count = layer_count;
        this.percent = percent;
        this.photo_after = photo_after;
        this.photo_before = photo_before;
        this.photo_after_test = photo_after_test;
        this.photo_reverse = photo_reverse;
    }

    public Integer getId() {
        return id;
    }

    public String getMaterial() {
        return material;
    }

    public String getAdditive() {
        return additive;
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


}
