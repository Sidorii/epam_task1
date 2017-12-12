package com.epam.trainee.service.impl;

import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.service.IngredientService;
import com.epam.trainee.model.dao.DaoFactory;
import com.epam.trainee.model.dao.IngredientDao;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class IngredientServiceImpl implements IngredientService {

    private static final IngredientService INSTANCE = new IngredientServiceImpl();

    private IngredientDao ingredientDao;

    public static IngredientService getInstance() {
        return INSTANCE;
    }

    private IngredientServiceImpl() {
        ingredientDao = DaoFactory.getInstance().getIngredientDao();
    }

    @Override
    public Ingredient createIngredient(Ingredient ingredient) {
        if (ingredient == null) {
            throw new IllegalArgumentException("Can't create null ingredient");
        }
        return ingredientDao.addEntity(ingredient);
    }

    @Override
    public Ingredient getIngredient(int id) {
        return ingredientDao.getEntity(id);
    }

    @Override
    public Ingredient getIngredientByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Ingredient name can't be null");
        }
        return ingredientDao.getIngredientByName(name);
    }

    @Override
    public Set<Ingredient> getAllIngredients() {
        return ingredientDao.getAll();
    }

    @Override
    public void updateIngredient(Ingredient ingredient) {
        if (ingredient == null) {
            throw new IllegalArgumentException("Can't update null ingredient");
        }
        ingredientDao.updateEntity(ingredient);
    }

    @Override
    public Set<Ingredient> getIngredientsById(Set<Integer> idSet) {
        return ingredientDao.findIngredientsById(idSet.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet()));
    }

    @Override
    public void removeIngredient(Integer id) {
        if (id == null) {
            return;
        }
        ingredientDao.removeEntity(id);
    }
}
