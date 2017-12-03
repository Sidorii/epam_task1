package com.epam.trainee.model.entities;

import com.epam.trainee.model.SaladVisitor;

public enum PackingType implements Packing {

    BOX(0.25f,true),
    PLATE(0.25f,false);


    private float price;
    private boolean isPortable;

    PackingType(float price, boolean isPortable) {
        this.price = price;
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

    public void acceptVisitor(SaladVisitor visitor) {

    }

    public boolean isPortable() {
        return isPortable;
    }
}
