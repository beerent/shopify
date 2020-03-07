package com.beerent.shopifyapi.database.users;

import java.util.List;

import com.beerent.shopifyapi.model.users.UserModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class UserDao {

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
    }

    public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    private static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure();
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        return sessionFactory;
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

    public void persist(UserModel user) {
        getCurrentSession().save(user);
    }

    public void update(UserModel user) {
        getCurrentSession().update(user);
    }

    public UserModel findByEmail(String email) {
        List<UserModel> users = (List<UserModel>) getCurrentSession()
                .createQuery("from UserModel where email = :email")
                .setParameter("email", email).list();

        if (users.isEmpty()) {
            return null;
        }

        return users.get(0); // DANGER if email is not unique
    }

    public UserModel findById(String id) {
        UserModel user = (UserModel) getCurrentSession().get(UserModel.class, id);
        return user;
    }

    public void delete(UserModel entity) {
        getCurrentSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    public List<UserModel> findAll() {
        List<UserModel> users = (List<UserModel>) getCurrentSession().createQuery("from users").list();
        return users;
    }

    public void deleteAll() {
        List<UserModel> entityList = findAll();
        for (UserModel entity : entityList) {
            delete(entity);
        }
    }
}