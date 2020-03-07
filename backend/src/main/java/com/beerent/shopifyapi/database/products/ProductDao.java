package com.beerent.shopifyapi.database.products;

import java.util.List;

import com.beerent.shopifyapi.model.products.Product;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ProductDao {

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

    public void persist(Product product) {
        getCurrentSession().save(product);
    }

    public void update(Product product) {
        getCurrentSession().update(product);
    }

    public Product findByName(String name) {
        List<Product> products = (List<Product>) getCurrentSession()
                .createQuery("from Product where name = :name")
                .setParameter("name", name).list();

        if (products.isEmpty()) {
            return null;
        }

        return products.get(0); // DANGER if email is not unique
    }

    public Product findById(String id) {
        Product product = (Product) getCurrentSession().get(Product.class, id);
        return product;
    }

    public void delete(Product product) {
        getCurrentSession().delete(product);
    }

    @SuppressWarnings("unchecked")
    public List<Product> findAll() {
        List<Product> products = (List<Product>) getCurrentSession().createQuery("from products").list();
        return products;
    }

    public void deleteAll() {
        List<Product> entityList = findAll();
        for (Product product : entityList) {
            delete(product);
        }
    }
}