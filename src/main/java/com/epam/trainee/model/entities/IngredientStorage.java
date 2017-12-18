package com.epam.trainee.model.entities;

import com.epam.trainee.model.exceptions.MissingItemException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class IngredientStorage {

    private Set<Ingredient> storage;

    public IngredientStorage(Set<Ingredient> storage) {
        if (storage == null) {
            this.storage = new HashSet<>();
        }else {
            this.storage = storage;
        }
    }

    public Set<Ingredient> getStoredIngredients() {
        return Collections.unmodifiableSet(storage);
    }

    public Set<Ingredient> produceIngredientsFromStorage(Set<Ingredient> ingredients) {
        if (ingredients == null || ingredients.size() == 0) {
            throw new IllegalArgumentException("Ingredients set for producing can't be null or empty");
        }
        Set<Ingredient> pulledIngredient = new HashSet<>();
        for(Ingredient i: ingredients){
            Ingredient pulled = pullIngredient(i);
            pulledIngredient.add(pulled);
        }
        return pulledIngredient;
    }

    public Ingredient produceIngredient(Ingredient ingredient) {
        if (ingredient == null) {
            throw new IllegalArgumentException("Ingredient for producing can't be null");
        }
        return pullIngredient(ingredient);
    }

    private Ingredient pullIngredient(Ingredient i) {
        Ingredient pulled = findIngredientByName(i.getName());
        subtractIngredientWeight(pulled, i.getWeight());
        return new IngredientPortion(pulled, i.getWeight());
    }

    private Ingredient findIngredientByName(String name) throws MissingItemException {
        return storage.stream()
                .filter(ingredient -> name.equals(ingredient.getName()))
                .findFirst()
                .orElseThrow(() -> new MissingItemException(name));
    }

    private void subtractIngredientWeight(Ingredient ingredient, double weight) throws MissingItemException {
        double newWeight = ingredient.getWeight() - weight;
        try {
            ingredient.setWeight(newWeight);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new MissingItemException(ingredient,
                    "Store has less ingredient supply than salad " +
                    "producing required. Required weight = " + weight +
                    ", actual stock = " + ingredient.getWeight());
        }
    }
}
