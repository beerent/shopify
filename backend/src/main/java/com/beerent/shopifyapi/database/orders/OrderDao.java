package com.beerent.shopifyapi.database.orders;

import java.util.List;

import com.beerent.shopifyapi.model.orders.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class OrderDao {

    private SessionFactory sessionFactory;
    private Session currentSession;
    private Transaction currentTransaction;

    public OrderDao() {
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

    public void persist(Order order) {
        getCurrentSession().save(order);
    }

    public void update(Order order) {
        getCurrentSession().update(order);
    }

    public Order findByExternalOrderId(long id) {
        List<Order> orders = (List<Order>) getCurrentSession()
                .createQuery("from Order where external_order_id = :id")
                .setParameter("id", id).list();

        if (orders.isEmpty()) {
            return null;
        }

        return orders.get(0);
    }

    public Order findById(String id) {
        Order order = (Order) getCurrentSession().get(Order.class, id);
        return order;
    }

    public void delete(Order order) {
        getCurrentSession().delete(order);
    }

    public List<Order> findAll() {
        return (List<Order>) getCurrentSession().
                createQuery("from Order", Order.class).list();
    }

    public void deleteAll() {
        List<Order> entityList = findAll();
        for (Order order : entityList) {
            delete(order);
        }
    }
}