package com.beerent.shopifyapi.database.products;

import java.util.List;

import com.beerent.shopifyapi.model.products.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ProductDao {

    private SessionFactory sessionFactory;
    private Session currentSession;
    private Transaction currentTransaction;

    public ProductDao() {
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

    public void persist(Product product) {
        getCurrentSession().save(product);
    }

    public void update(Product product) {
        getCurrentSession().update(product);
    }

    public Product findByExternalId(String id) {
        List<Product> products = (List<Product>) getCurrentSession()
                .createQuery("from Product where external_id = :id")
                .setParameter("id", id).list();

        if (products.isEmpty()) {
            return null;
        }

        return products.get(0);
    }

    public Product findById(String id) {
        Product product = (Product) getCurrentSession().get(Product.class, id);
        return product;
    }

    public void delete(Product product) {
        getCurrentSession().delete(product);
    }

    public List<Product> findAll() {
        return (List<Product>) getCurrentSession().
                createQuery("from Product", Product.class).list();
    }

    public void deleteAll() {
        List<Product> entityList = findAll();
        for (Product product : entityList) {
            delete(product);
        }
    }
}