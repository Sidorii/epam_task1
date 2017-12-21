package ua.task1.trainee.model.dao.jdbc.mappers;

import ua.task1.trainee.model.entities.Ingredient;
import ua.task1.trainee.model.entities.dishes.Salad;
import ua.task1.trainee.model.exceptions.MissingEntityException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SaladMapper extends ObjectMapper<Salad> {

    private ExtractStrategy<Ingredient> ingredientExtractStrategy;
    private Map<Integer, Ingredient> ingredientsCache;
    private Map<Integer, Salad> saladsCache = new HashMap<>();

    public SaladMapper(ExtractType extractType) {
        ingredientsCache = new HashMap<>();
        saladsCache = new HashMap<>();
        setExtractType(extractType);
    }

    public void setExtractType(ExtractType extractType) {
        switch (extractType) {
            case FULL:
                ingredientExtractStrategy = new FullExtractStrategy();
                break;
            case DEMO:
                ingredientExtractStrategy = new DemoExtractStrategy();
                break;
            default:
        }
    }

    @Override
    protected Salad map(ResultSet rs) throws SQLException {
        return extractSalads(rs).stream()
                .findFirst()
                .orElseThrow(
                        () -> new MissingEntityException("Can't extract salad from ResultSet")
                );
    }

    @Override
    public Set<Salad> extractSetFromResultSet(ResultSet rs) throws SQLException {
        return new HashSet<>(extractSalads(rs));
    }

    private Collection<Salad> extractSalads(ResultSet rs) throws SQLException {
        ingredientsCache.clear();
        saladsCache.clear();

        do {
            Salad salad = extractSalad(rs);
            salad = makeUnique(salad);
            Ingredient ingredient = ingredientExtractStrategy.extract(rs);
            salad.addIngredient(ingredient);
        } while (rs.next());
        return saladsCache.values();
    }

    private Salad extractSalad(ResultSet rs) throws SQLException {
        Salad salad = new Salad();
        salad.setId(rs.getInt("salad_id"));
        salad.setName(rs.getString("s_name"));
        salad.setDescription(rs.getString("s_description"));
        return salad;
    }

    public Salad makeUnique(Salad salad) {
        saladsCache.putIfAbsent(salad.getId(), salad);
        return saladsCache.get(salad.getId());
    }



    private class DemoExtractStrategy implements ExtractStrategy<Ingredient> {

        @Override
        public Ingredient extract(ResultSet rs) throws SQLException {
            return null;
        }
    }

    private class FullExtractStrategy implements ExtractStrategy<Ingredient> {

        private final IngredientMapper mapper = new IngredientMapper(ExtractType.FULL);

        @Override
        public Ingredient extract(ResultSet rs) throws SQLException {
            Ingredient ingredient = mapper.extractFromResultSet(rs);
            return mapper.makeUnique(ingredientsCache, ingredient);
        }
    }
}
