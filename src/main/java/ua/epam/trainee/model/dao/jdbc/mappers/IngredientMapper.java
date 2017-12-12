package ua.epam.trainee.model.dao.jdbc.mappers;

import ua.epam.trainee.model.entities.Ingredient;
import ua.epam.trainee.model.entities.IngredientImpl;
import ua.epam.trainee.model.entities.IngredientType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class IngredientMapper extends ObjectMapper<Ingredient> {

    private ExtractStrategy<IngredientType> typeExtractStrategy;

    public IngredientMapper(ExtractType extractType) {
        setExtractType(extractType);
    }

    public void setExtractType(ExtractType extractType) {
        switch (extractType) {
            case DEMO:
                typeExtractStrategy = new DemoExtractStrategy();
                break;
            case FULL:
                typeExtractStrategy = new FullExtractStrategy();
                break;
            default:
                throw new IllegalArgumentException("Strategy for Extract Type: "
                        + extractType + " not found.");
        }
    }

    @Override
    protected Ingredient map(ResultSet rs) throws SQLException {
        return IngredientImpl.getIngredientBuilder()
                .setId(rs.getInt("ingredient_id"))
                .setName(rs.getString("i_name"))
                .setPrice(rs.getFloat("price"))
                .setWeight(rs.getDouble("weight"))
                .setFresh(rs.getBoolean("fresh"))
                .setCalories(rs.getFloat("calories"))
                .setDescription(rs.getString("i_description"))
                .setType(typeExtractStrategy.extract(rs))
                .createIngredient();
    }


    private class DemoExtractStrategy implements ExtractStrategy<IngredientType> {

        @Override
        public IngredientType extract(ResultSet rs) {
            return null;
        }
    }

    private class FullExtractStrategy implements ExtractStrategy<IngredientType> {

        @Override
        public IngredientType extract(ResultSet rs) throws SQLException {
            return new IngredientTypeMapper().extractFromResultSet(rs);
        }
    }

    public Ingredient makeUnique(Map<Integer, Ingredient> cache, Ingredient ingredient) {
        cache.putIfAbsent(ingredient.getId(), ingredient);
        return cache.get(ingredient.getId());
    }
}
