package com.esstu.dymbrylov.model;

public class Material {
    private final Integer id;
    private final String name;

    public Material(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
