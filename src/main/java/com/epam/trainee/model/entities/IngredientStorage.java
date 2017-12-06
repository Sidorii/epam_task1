package com.epam.trainee.model.entities;

import com.epam.trainee.model.exceptions.MissingIngredientException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
//TODO:create abstraction over IngredientStorage class
public class IngredientStorage {

    private Set<Ingredient> storage;

    public IngredientStorage(Set<Ingredient> storage) {
        this.storage = storage;
    }

    public Set<Ingredient> getStoredIngredients() {
        return Collections.unmodifiableSet(storage);
    }

    public Set<Ingredient> produceIngredients(Set<Ingredient> ingredients) {
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
            throw new IllegalArgumentException("Ingredient for prodcing can't be null");
        }
        return pullIngredient(ingredient);
    }

    private Ingredient pullIngredient(Ingredient i) {
        Ingredient pulled = findIngredientByName(i.getName());
        subtractIngredientWeight(pulled, i.getWeight());

        return IngredientImpl.getIngredientBuilder()
                .createFrom(pulled)
                .setWeight(i.getWeight())
                .createIngredient();
    }

    private Ingredient findIngredientByName(String name) throws MissingIngredientException{
        return storage.stream()
                .filter(ingredient -> name.equals(ingredient.getName()))
                .findFirst()
                .orElseThrow(() -> new MissingIngredientException(name));
    }

    private void subtractIngredientWeight(Ingredient ingredient, double weight) throws MissingIngredientException{
        double newWeight = ingredient.getWeight() - weight;
        try {
            ingredient.setWeight(newWeight);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new MissingIngredientException(ingredient,
                    "Store has less ingredient supply than salad " +
                    "producing required. Required weight = " + weight +
                    ", actual stock = " + ingredient.getWeight());
        }
    }
}
