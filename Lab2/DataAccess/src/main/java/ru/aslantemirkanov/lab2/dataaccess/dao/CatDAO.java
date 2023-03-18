package ru.aslantemirkanov.lab2.dataaccess.dao;

import ru.aslantemirkanov.lab2.dataaccess.entities.Cat;

import java.util.List;
import java.util.Set;

public interface CatDAO {
    Set<Cat> findAllFrineds(Cat cat);
    Cat findById(long id);
    long insertCat(Cat cat);
    void updateCat(Cat cat);
    void deleteCat(Cat cat);

    void addFriend(Cat cat1, Cat cat2);
}
