package com.beerent.shopifyapi.database.products;

import java.util.List;

import com.beerent.shopifyapi.model.products.ProductModel;

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

    public void persist(ProductModel product) {
        getCurrentSession().save(product);
    }

    public void update(ProductModel product) {
        getCurrentSession().update(product);
    }

    public ProductModel findByName(String name) {
        List<ProductModel> products = (List<ProductModel>) getCurrentSession()
                .createQuery("from ProductModel where name = :name")
                .setParameter("name", name).list();

        if (products.isEmpty()) {
            return null;
        }

        return products.get(0); // DANGER if email is not unique
    }

    public ProductModel findById(String id) {
        ProductModel product = (ProductModel) getCurrentSession().get(ProductModel.class, id);
        return product;
    }

    public void delete(ProductModel product) {
        getCurrentSession().delete(product);
    }

    @SuppressWarnings("unchecked")
    public List<ProductModel> findAll() {
        List<ProductModel> products = (List<ProductModel>) getCurrentSession().createQuery("from products").list();
        return products;
    }

    public void deleteAll() {
        List<ProductModel> entityList = findAll();
        for (ProductModel product : entityList) {
            delete(product);
        }
    }
}