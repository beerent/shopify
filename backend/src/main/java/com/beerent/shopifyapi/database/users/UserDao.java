package com.beerent.shopifyapi.database.users;

import java.util.List;

import com.beerent.shopifyapi.model.users.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class UserDao {

    private SessionFactory sessionFactory;
    private Session currentSession;
    private Transaction currentTransaction;

    public UserDao() {
    }

    public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }

    public Session openCurrentSessionwithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSession() {
        currentSession.close();
        sessionFactory.close();
    }

    public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
        sessionFactory.close();
    }

    private SessionFactory getSessionFactory() {
        if (this.sessionFactory == null || this.sessionFactory.isClosed()) {
            this.sessionFactory = new Configuration().configure().buildSessionFactory();
        }

        return this.sessionFactory;
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    public void persist(User user) {
        getCurrentSession().save(user);
    }

    public void update(User user) {
        getCurrentSession().update(user);
    }

    public User findByExternalId(String id) {
        List<User> users = (List<User>) getCurrentSession()
                .createQuery("from User where external_id = :id")
                .setParameter("id", id).list();

        if (users.isEmpty()) {
            return null;
        }

        return users.get(0);
    }

    public User findById(String id) {
        User user = (User) getCurrentSession().get(User.class, id);
        return user;
    }

    public void delete(User user) {
        getCurrentSession().delete(user);
    }

    public List<User> findAll() {
        return (List<User>) getCurrentSession().
                createQuery("from User", User.class).list();
    }

    public void deleteAll() {
        List<User> entityList = findAll();
        for (User user : entityList) {
            delete(user);
        }
    }
}