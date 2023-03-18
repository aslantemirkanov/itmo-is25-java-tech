package ru.aslantemirkanov.lab2.dataaccess.dao;

import org.hibernate.Session;
import ru.aslantemirkanov.lab2.dataaccess.entities.Cat;
import ru.aslantemirkanov.lab2.dataaccess.entities.CatOwner;
import ru.aslantemirkanov.lab2.dataaccess.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;

public class CatOwnerPostgreDAO implements CatOwnerDAO {
    @Override
    public List<CatOwner> findAll() {
        try (Session session = Hibernate.getSessionFactory().openSession();) {
            session.getTransaction().begin();
            List<CatOwner> findCatOwners = session.createNativeQuery(
                    "select * from tables.cat_owners order by id",
                    CatOwner.class).list();
            session.getTransaction().commit();
            return findCatOwners;
        }
    }

    @Override
    public CatOwner findById(long id) {
        try (Session session = Hibernate.getSessionFactory().openSession();) {
            session.getTransaction().begin();
            CatOwner findCatOwner = session.get(CatOwner.class, id);
            session.getTransaction().commit();
            return findCatOwner;
        }
    }


    @Override
    public long insertCatOwner(CatOwner catOwner) {
        try (Session session = Hibernate.getSessionFactory().openSession();) {
            session.getTransaction().begin();
            session.persist(catOwner);
            session.getTransaction().commit();
            return catOwner.getId();
        }
    }

    @Override
    public void updateCatOwner(CatOwner catOwner) {
        try (Session session = Hibernate.getSessionFactory().openSession();) {
            session.getTransaction().begin();
            session.merge(catOwner);
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteCatOwner(CatOwner catOwner) {
        try (Session session = Hibernate.getSessionFactory().openSession();) {
            session.getTransaction().begin();
            session.remove(catOwner);
            session.getTransaction().commit();
        }
    }

    @Override
    public void addOwner(Cat cat, CatOwner catOwner) {
        try (Session session = Hibernate.getSessionFactory().openSession();) {
            session.getTransaction().begin();
            cat.setCatOwner(catOwner);
            List<Cat> catList = catOwner.getCats();
            catList.add(cat);
            session.merge(catOwner);
            session.merge(cat);
            session.getTransaction().commit();
        }
    }

    @Override
    public void addOwnerById(long catId, long catOwnerId) {
        try (Session session = Hibernate.getSessionFactory().openSession();) {
            session.getTransaction().begin();
            CatOwner findCatOwner = session.get(CatOwner.class, catOwnerId);
            Cat findCat = session.get(Cat.class, catId);
            findCat.setCatOwner(findCatOwner);
            findCatOwner.getCats().add(findCat);
            session.merge(findCatOwner);
            session.merge(findCat);
            session.getTransaction().commit();
        }
    }
}
