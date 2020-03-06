package com.beerent.shopifyapi.database;

import com.beerent.shopifyapi.model.users.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class DatabaseCommunicator {
    private static EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("shopify");

    public void AddCustomer() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            User user = new User("Brent", "Ryczak", "brentryczaktest@gmail.com", "12321");
            em.persist(user);
            et.commit();
        } catch(Exception e) {
            et.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

}
