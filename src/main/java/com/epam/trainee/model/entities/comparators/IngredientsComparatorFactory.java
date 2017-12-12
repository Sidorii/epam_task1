package com.epam.trainee.model.entities.comparators;

import com.epam.trainee.model.entities.Ingredient;

import java.util.Comparator;

public abstract class IngredientsComparatorFactory {

    private static IngredientsComparatorFactory instance;

    public static IngredientsComparatorFactory getInstance() {
        if (instance == null) {
            synchronized (IngredientsComparatorFactory.class) {
                if (instance == null) {
                    instance = new PriceIngredientsComaparatorFactoryImpl();
                }
            }
        }
        return instance;
    }

    public abstract Comparator<Ingredient> getAscending();

    public abstract Comparator<Ingredient> getDescending();
}
