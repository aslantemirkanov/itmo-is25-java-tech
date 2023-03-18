package ru.aslantemirkanov.lab2.dataaccess.dao;

import ru.aslantemirkanov.lab2.dataaccess.entities.Cat;
import ru.aslantemirkanov.lab2.dataaccess.entities.CatOwner;

import java.util.List;

public interface CatOwnerDAO {
    List<CatOwner> findAll();
    CatOwner findById(long id);
    long insertCatOwner(CatOwner catOwner);
    void updateCatOwner(CatOwner catOwner);
    void deleteCatOwner(CatOwner catOwner);

    void addOwner(Cat cat, CatOwner catOwner);
    void addOwnerById(long catId, long catOwnerId);
}
