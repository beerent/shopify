package com.beerent.shopifyapi.database.orders;

import java.util.List;

import com.beerent.shopifyapi.model.orders.OrderProductMapModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class OrderProductMapDao {

    private Session currentSession;
    private Transaction currentTransaction;

    public OrderProductMapDao() {
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

    public void persist(OrderProductMapModel order) {
        getCurrentSession().save(order);
    }

    public void update(OrderProductMapModel order) {
        getCurrentSession().update(order);
    }

    public OrderProductMapModel findById(String id) {
        OrderProductMapModel order = (OrderProductMapModel) getCurrentSession().get(OrderProductMapModel.class, id);
        return order;
    }

    public void delete(OrderProductMapModel order) {
        getCurrentSession().delete(order);
    }

    @SuppressWarnings("unchecked")
    public List<OrderProductMapModel> findAll() {
        List<OrderProductMapModel> orders = (List<OrderProductMapModel>) getCurrentSession().createQuery("from orders").list();
        return orders;
    }

    public void deleteAll() {
        List<OrderProductMapModel> entityList = findAll();
        for (OrderProductMapModel order : entityList) {
            delete(order);
        }
    }
}