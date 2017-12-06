package com.epam.trainee.model.entities;

import com.epam.trainee.model.SaladVisitor;

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

    public void acceptVisitor(SaladVisitor visitor) {

    }
}
