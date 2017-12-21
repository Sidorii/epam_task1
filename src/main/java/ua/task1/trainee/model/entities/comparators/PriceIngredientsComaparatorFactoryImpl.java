package ua.task1.trainee.model.entities.comparators;

import ua.task1.trainee.model.entities.Ingredient;
import ua.task1.trainee.model.entities.Item;

import java.util.Comparator;

public class PriceIngredientsComaparatorFactoryImpl extends IngredientsComparatorFactory {

    @Override
    public Comparator<Ingredient> getAscending() {
        return Comparator.comparing(Item::getPrice);
    }

    @Override
    public Comparator<Ingredient> getDescending() {
        return getAscending().reversed();
    }
}
