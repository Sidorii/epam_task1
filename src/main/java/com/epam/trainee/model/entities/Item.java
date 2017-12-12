package com.epam.trainee.model.entities;

import com.epam.trainee.model.SaladVisitor;

public interface Item {

    Integer getId();

    String getName();

    float getPrice();

    double getWeight();

    String getDescription();

    void acceptVisitor(SaladVisitor visitor);
}
