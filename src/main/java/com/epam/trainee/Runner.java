package com.epam.trainee;

import com.epam.trainee.model.entities.IngredientType;
import com.epam.trainee.model.entities.SaladIngredient;

public class Runner {

    public static void main(String[] args) {
        SaladIngredient ingredient = SaladIngredient.getIngredientBuilder(1000)
                .setCalories(200)
                .setFresh(true)
                .setName("Cucumber")
                .setPrice(5)
                .setType(IngredientType.VEGETABLE)
                .createIngredient();

        System.out.println(ingredient);
    }
}
