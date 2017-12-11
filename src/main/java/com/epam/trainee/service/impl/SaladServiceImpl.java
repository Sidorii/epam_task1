package com.epam.trainee.service.impl;

import com.epam.trainee.model.dao.DaoFactory;
import com.epam.trainee.model.dao.IngredientDao;
import com.epam.trainee.model.dao.SaladDao;
import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.IngredientStorage;
import com.epam.trainee.model.entities.dishes.Salad;
import com.epam.trainee.service.SaladService;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;

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
        throwIfInvalidIngredients(ingredients);

        Set<Ingredient> storedIngredients = ingredientDao.getIngredients(ingredients);
        IngredientStorage storage = new IngredientStorage(storedIngredients);
        ingredients = storage.produceIngredients(ingredients);
        Salad salad = new Salad(ingredients);
        storedIngredients = storage.getStoredIngredients();
        ingredientDao.batchUpdate(storedIngredients);
        return salad;
    }

    private void throwIfInvalidIngredients(Collection ingredients) {
        if ((ingredients == null || ingredients.size() < 1)) {
            throw new IllegalArgumentException("Ingredients is null or has" +
                    " invalid size: " + ingredients);
        }
    }

    @Override
    public Salad orderSalad(String name) {
        throwIfInvalidName(name);
        return saladDao.getSaladByName(name);
    }

    private void throwIfInvalidName(String name) {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("Invalid salad name: " + '\'' + name + '\'');
        }
    }

    @Override
    public void createSaladRecipe(String name, Set<Ingredient> recipe) {
        throwIfInvalidIngredients(recipe);
        throwIfInvalidName(name);

        Salad salad = new Salad(recipe);
        salad.setName(name);
        saladDao.addEntity(salad);
    }

    @Override
    public Set<Salad> getAllSalads() {
        return saladDao.getAll();
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
