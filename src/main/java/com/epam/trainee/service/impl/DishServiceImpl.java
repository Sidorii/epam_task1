package com.epam.trainee.service.impl;

import com.epam.trainee.model.entities.dishes.Dish;
import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.Packing;
import com.epam.trainee.model.entities.PackingType;
import com.epam.trainee.model.entities.dishes.Salad;
import com.epam.trainee.model.entities.dishes.SaladDish;
import com.epam.trainee.service.DishService;
import com.epam.trainee.service.SaladService;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class DishServiceImpl implements DishService {

    private SaladService saladService;

    public DishServiceImpl(SaladService saladService) {
        this.saladService = saladService;
    }

    @Override
    public Dish orderSalad(Set<Ingredient> ingredients) {
        throwIfInvalidIngredients(ingredients);
        return createDish(ingredients);
    }

    private void throwIfInvalidIngredients(Collection ingredients) {
        if ((ingredients == null || ingredients.size() < 1)) {
            throw new IllegalArgumentException("Ingredients is null or has" +
                    " invalid size: " + ingredients);
        }
    }

    private void throwIfInvalidName(String name) {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("Invalid salad name: " + '\'' + name + '\'');
        }
    }

    private Dish createDish(Set<Ingredient> ingredients) {
        Salad salad = saladService.orderSalad(ingredients);
        Packing packing = PackingType.PLATE;
        return newDish(salad, packing);
    }

    private Dish newDish(Salad salad, Packing packing) {
        return new SaladDish(salad, packing);
    }

    @Override
    public Dish orderSalad(String name) {
        throwIfInvalidName(name);
        return newDish(saladService.orderSalad(name), PackingType.PLATE);
    }

    @Override
    public Set<Dish> getAllSalads() {
        return saladService.getAllSalads().stream()
                .map((salad -> new SaladDish(salad, PackingType.PLATE)))
                .collect(Collectors.toSet());
    }

    @Override
    public void createSaladRecipe(String saladName, Set<Ingredient> ingredients) {
        throwIfInvalidIngredients(ingredients);
        throwIfInvalidName(saladName);
        saladService.createSaladRecipe(saladName, ingredients);
    }
}
