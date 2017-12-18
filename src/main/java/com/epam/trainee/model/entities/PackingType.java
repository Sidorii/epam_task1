package com.epam.trainee.model.entities;

public enum PackingType implements Packing {

    BOX(0.25f, .1,true),
    PLATE(0.25f,.3,false);

    private float price;
    private boolean isPortable;
    private double weight;

    PackingType(float price, double weight, boolean isPortable) {
        this.price = price;
        this.weight = weight;
        this.isPortable = isPortable;
    }

    public String getName() {
        return this.name().toLowerCase();
    }

    @Override
    public Integer getId() {
        return this.ordinal();
    }

    public float getPrice() {
        return price;
    }

    public String getDescription() {
        return "";
    }

    public double getWeight() {
        return weight;
    }

    public boolean isPortable() {
        return isPortable;
    }

}
