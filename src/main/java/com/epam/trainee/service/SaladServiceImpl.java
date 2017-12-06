package com.epam.trainee.service;

import com.epam.trainee.model.dao.DaoFactory;
import com.epam.trainee.model.dao.IngredientDao;
import com.epam.trainee.model.dao.SaladDao;
import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.IngredientStorage;
import com.epam.trainee.model.entities.dishes.Salad;

import java.util.*;

public class SaladServiceImpl implements SaladService {

    private SaladDao saladDao;
    private IngredientDao ingredientDao;

    public SaladServiceImpl() {
        saladDao = DaoFactory.getInstance().getSaladDao();
        ingredientDao = DaoFactory.getInstance().getIngredientDao();
    }

    public SaladServiceImpl(SaladDao saladDao, IngredientDao ingredientDao) {
        this.saladDao = saladDao;
        this.ingredientDao = ingredientDao;
    }

    @Override
    public Salad orderSalad(Set<Ingredient> ingredients) {
        if (ingredients == null || ingredients.size() == 0) {
            throw new IllegalArgumentException("Ingredients set for salad creating can't be null or empty");
        }
        Set<Ingredient> storedIngredients = ingredientDao.getIngredients(ingredients);
        IngredientStorage storage = new IngredientStorage(storedIngredients);
        ingredients = storage.produceIngredients(ingredients);
        Salad salad = new Salad(ingredients);
        storedIngredients = storage.getStoredIngredients();
        ingredientDao.batchUpdate(storedIngredients);
        return salad;
    }

    @Override
    public Salad orderSalad(String name) {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("Invalid name for salad: " + name);
        }
        return saladDao.getSalad(name);
    }

    @Override
    public void createSaladRecipe(String name, Set<Ingredient> recipe) {
        if (recipe == null || recipe.size() == 0) {
            throw new IllegalArgumentException("Ingredients set for salad creating can't be null or empty");
        }
        Salad salad = new Salad(recipe);
        salad.setName(name);
        saladDao.createSalad(salad);
    }

    //TODO:implement methods below
    @Override
    public Set<Ingredient> sortIngredients(Salad salad, Comparator<Ingredient> comparator) {
        return null;
    }

    @Override
    public Set<Ingredient> sortIngredients(Salad salad) {
        return null;
    }
}
