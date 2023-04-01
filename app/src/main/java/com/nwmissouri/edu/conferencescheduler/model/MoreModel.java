package com.nwmissouri.edu.conferencescheduler.model;

public class MoreModel {
    private String id;
    private String name;
    private int drawableResource;

    public MoreModel(String id, String name, int drawableResource) {
        this.id = id;
        this.name = name;
        this.drawableResource = drawableResource;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDrawableResource() {
        return drawableResource;
    }

    public void setDrawableResource(int drawableResource) {
        this.drawableResource = drawableResource;
    }
}
