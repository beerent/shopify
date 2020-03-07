package com.beerent.shopifyapi.database.orders;

import java.util.List;

import com.beerent.shopifyapi.model.orders.OrderModel;
import com.beerent.shopifyapi.model.products.ProductModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class OrderDao {

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

    public void persist(OrderModel order) {
        getCurrentSession().save(order);
    }

    public void update(OrderModel order) {
        getCurrentSession().update(order);
    }


    public OrderModel findByExternalOrderId(long id) {
        List<OrderModel> orders = (List<OrderModel>) getCurrentSession()
                .createQuery("from OrderModel where external_order_id = :id")
                .setParameter("id", id).list();

        if (orders.isEmpty()) {
            return null;
        }

        return orders.get(0); // DANGER if email is not unique
    }


    public OrderModel findById(String id) {
        OrderModel order = (OrderModel) getCurrentSession().get(OrderModel.class, id);
        return order;
    }

    public void delete(OrderModel order) {
        getCurrentSession().delete(order);
    }

    @SuppressWarnings("unchecked")
    public List<OrderModel> findAll() {
        List<OrderModel> orders = (List<OrderModel>) getCurrentSession().createQuery("from orders").list();
        return orders;
    }

    public void deleteAll() {
        List<OrderModel> entityList = findAll();
        for (OrderModel order : entityList) {
            delete(order);
        }
    }
}