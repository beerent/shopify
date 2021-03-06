package com.beerent.shopifyapi.database.products;

import com.beerent.shopifyapi.model.products.Product;
import java.util.List;

public class ProductService {

    private static ProductDao productDao;

    public ProductService() {
        productDao = new ProductDao();
    }

    public void persist(List<Product> products) {
        productDao.openCurrentSessionwithTransaction();

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            Product existingModel = productDao.findByExternalId(product.getExternalId());
            if  (existingModel != null) {
                product = existingModel;
            }

            productDao.persist(product);
        }

        productDao.closeCurrentSessionwithTransaction();
    }

    public void persist(Product product) {
        productDao.openCurrentSessionwithTransaction();
        productDao.persist(product);
        productDao.closeCurrentSessionwithTransaction();
    }

    public void update(Product product) {
        productDao.openCurrentSessionwithTransaction();
        productDao.update(product);
        productDao.closeCurrentSessionwithTransaction();
    }

    public Product findById(String id) {
        productDao.openCurrentSession();
        Product product = productDao.findById(id);
        productDao.closeCurrentSession();

        return product;
    }

    public Product findByExternalId(String id) {
        productDao.openCurrentSession();
        Product product = productDao.findByExternalId(id);
        productDao.closeCurrentSession();

        return product;
    }

    public List<Product> findAll() {
        productDao.openCurrentSession();
        List<Product> products = productDao.findAll();
        productDao.closeCurrentSession();

        return products;
    }

    public void delete(String id) {
        productDao.openCurrentSessionwithTransaction();
        Product product = productDao.findById(id);
        productDao.delete(product);
        productDao.closeCurrentSessionwithTransaction();
    }

    public void deleteAll() {
        productDao.openCurrentSessionwithTransaction();
        productDao.deleteAll();
        productDao.closeCurrentSessionwithTransaction();
    }
}
