package ua.epam.trainee.model.entities.dishes;

import ua.epam.trainee.model.SaladVisitor;
import ua.epam.trainee.model.entities.Ingredient;
import ua.epam.trainee.model.entities.IngredientType;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Salad implements Dish{

    private Integer id;
    protected Set<Ingredient> ingredients;
    private String name;
    private String description;

    public Salad() {
        ingredients = new HashSet<>();
    }

    public Salad(Set<Ingredient> ingredients) {
        setIngredients(ingredients);
    }

    public Salad(String name, Set<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        if (ingredients == null) {
            ingredients = new HashSet<>();
        }
        ingredients.add(ingredient);
    }

    public void removeIngredient(Ingredient ingredient) {
        if (ingredients == null) {
            return;
        }
        ingredients.remove(ingredient);
    }

    public Set<Ingredient> getIngredients() {
        return Collections.unmodifiableSet(ingredients);
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        if (ingredients == null) {
            throw new IllegalArgumentException("Ingredients can't be null");
        }
        this.ingredients = ingredients;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isVegan() {
        return ingredients.stream()
                .noneMatch(i -> i.getType() == null ||
                        i.getType().equals(IngredientType.MEAT));
    }

    public float getCalories() {
        return (float) ingredients.stream()
                .mapToDouble(Ingredient::getCalories)
                .sum();
    }

    public double getWeight() {
        return ingredients.stream()
                .mapToDouble(Ingredient::getWeight)
                .sum();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return (float) ingredients.stream()
                .mapToDouble(Ingredient::getPrice)
                .sum();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void acceptVisitor(SaladVisitor visitor) {
        ingredients.forEach(ingr -> visitor.visitIngredient((ingr)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Salad)) return false;

        Salad salad = (Salad) o;
        if (name != null && !name.equals(salad.getName())) return false;
        return ingredients != null ? ingredients.equals(salad.ingredients) : salad.ingredients == null;
    }

    @Override
    public int hashCode() {
        int result = 31 * (name != null ? name.hashCode() : 0);
        return result + (ingredients != null ? ingredients.hashCode() : 0);
    }

    @Override
    public String toString() {
        return "Salad{" +
                "id=" + id +
                ", ingredients=" + ingredients +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
