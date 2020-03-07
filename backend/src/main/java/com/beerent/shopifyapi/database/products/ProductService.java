package com.beerent.shopifyapi.database.products;

import com.beerent.shopifyapi.model.products.ProductModel;

import java.util.List;

public class ProductService {

    private static ProductDao productDao;

    public ProductService() {
        productDao = new ProductDao();
    }

    public void persist(List<ProductModel> products) {
        productDao.openCurrentSessionwithTransaction();

        for (ProductModel product : products) {
            ProductModel existingProduct = productDao.findByName(product.getName());
            if  (existingProduct != null) {
                product.setId(existingProduct.getId());
                continue;
            }
            productDao.persist(product);
        }

        productDao.closeCurrentSessionwithTransaction();
    }

    public void persist(ProductModel product) {
        productDao.openCurrentSessionwithTransaction();
        productDao.persist(product);
        productDao.closeCurrentSessionwithTransaction();
    }

    public void update(ProductModel product) {
        productDao.openCurrentSessionwithTransaction();
        productDao.update(product);
        productDao.closeCurrentSessionwithTransaction();
    }

    public ProductModel findById(String id) {
        productDao.openCurrentSession();
        ProductModel product = productDao.findById(id);
        productDao.closeCurrentSession();

        return product;
    }

    public List<ProductModel> findAll() {
        productDao.openCurrentSession();
        List<ProductModel> products = productDao.findAll();
        productDao.closeCurrentSession();

        return products;
    }

    public void delete(String id) {
        productDao.openCurrentSessionwithTransaction();
        ProductModel product = productDao.findById(id);
        productDao.delete(product);
        productDao.closeCurrentSessionwithTransaction();
    }

    public void deleteAll() {
        productDao.openCurrentSessionwithTransaction();
        productDao.deleteAll();
        productDao.closeCurrentSessionwithTransaction();
    }
}
