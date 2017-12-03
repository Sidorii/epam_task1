package com.epam.trainee.model.dao;

import com.epam.trainee.model.entities.Packing;

public interface PackingDao {

    void addPackage(Packing packing);

    Packing getPackageByName(String name);

    void deletePackageByName(String name);

    void updatePackage(Packing packing);
}
