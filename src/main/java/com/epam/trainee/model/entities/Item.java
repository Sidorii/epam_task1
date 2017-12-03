package com.epam.trainee.model.entities;

import com.epam.trainee.model.SaladVisitor;

public interface Item {

    String getName();

    float getPrice();

    String getDescription();

    void acceptVisitor(SaladVisitor visitor);
}
