package com.epam.trainee.model.dao.jdbc.mappers;

import com.epam.trainee.model.entities.dishes.Salad;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class SaladMapper extends ObjectMapper<Salad> {

    @Override
    protected Salad map(ResultSet rs) throws SQLException {
        Salad salad = new Salad();
        salad.setId(rs.getInt("salad.salad_id"));
        salad.setName(rs.getString("salad.salad.name"));
        salad.setDescription(rs.getString("salad.description"));
        return salad;
    }

    public Salad makeUnique(Map<Integer, Salad> cache, Salad salad) {
        cache.putIfAbsent(salad.getId(), salad);
        return cache.get(salad.getId());
    }
}
