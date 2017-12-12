package com.epam.trainee.service.impl;

import com.epam.trainee.model.dao.DaoFactory;
import com.epam.trainee.model.dao.SaladDao;
import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.IngredientStorage;
import com.epam.trainee.service.SaladService;
import com.epam.trainee.model.dao.IngredientDao;
import com.epam.trainee.model.entities.dishes.Salad;
import com.epam.trainee.model.exceptions.MissingItemException;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;

public class SaladServiceImpl implements SaladService {

    private static final SaladServiceImpl INSTANCE = new SaladServiceImpl();

    protected SaladDao saladDao;
    protected IngredientDao ingredientDao;

    private SaladServiceImpl() {
        saladDao = DaoFactory.getInstance().getSaladDao();
        ingredientDao = DaoFactory.getInstance().getIngredientDao();
    }

    public static SaladServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Salad orderSalad(Salad salad) {
        if (salad == null) {
            throw new MissingItemException("Salad can't be null");
        }
        Set<Ingredient> ingredients = salad.getIngredients();
        throwIfInvalidIngredients(ingredients);

        Set<Ingredient> storedIngredients = ingredientDao.getIngredients(ingredients);
        IngredientStorage storage = new IngredientStorage(storedIngredients);
        ingredients = storage.produceIngredientsFromStorage(ingredients);
        salad.setIngredients(ingredients);
        storedIngredients = storage.getStoredIngredients();
        ingredientDao.mergeIngredientsWeight(storedIngredients);
        return salad;
    }

    private void throwIfInvalidIngredients(Collection ingredients) {
        if ((ingredients == null || ingredients.size() < 1)) {
            throw new IllegalArgumentException("Ingredients is null or has" +
                    " invalid size: " + ingredients);
        }
    }

    @Override
    public Salad getSaladByName(String name) {
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

    @Override
    public void removeSalad(Integer id) {
        if (id == null) {
            return;
        }
        saladDao.removeEntity(id);
    }
}
