package ru.aslantemirkanov.lab2.dataaccess.dao;

import org.hibernate.Session;
import ru.aslantemirkanov.lab2.dataaccess.entities.Cat;
import ru.aslantemirkanov.lab2.dataaccess.hibernate.Hibernate;

import java.util.List;
import java.util.Set;

public class CatPostgreDAO implements CatDAO {
    @Override
    public Set<Cat> findAllFriends(Cat cat) {
        return cat.getCatFriends();
    }

    @Override
    public Cat findById(long id) {
        try (Session session = Hibernate.getSessionFactory().openSession();) {
            session.getTransaction().begin();
            Cat findCat = session.get(Cat.class, id);
            session.getTransaction().commit();
            return findCat;
        }
    }


    @Override
    public long insertCat(Cat cat) {
        try (Session session = Hibernate.getSessionFactory().openSession();) {
            session.getTransaction().begin();
            session.persist(cat);
            session.getTransaction().commit();
            return cat.getId();
        }
    }

    @Override
    public void updateCat(Cat cat) {
        try (Session session = Hibernate.getSessionFactory().openSession();) {
            session.getTransaction().begin();
            session.merge(cat);
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteCat(Cat cat) {
        try (Session session = Hibernate.getSessionFactory().openSession();) {
            session.getTransaction().begin();
            session.remove(cat);
            session.getTransaction().commit();
        }
    }

    @Override
    public void addFriend(Cat cat1, Cat cat2) {
        try (Session session = Hibernate.getSessionFactory().openSession();) {
            session.getTransaction().begin();
            Set<Cat> friends = cat1.getCatFriends();
            friends.add(cat2);
            session.merge(cat1);
            session.getTransaction().commit();
        }
    }
}
