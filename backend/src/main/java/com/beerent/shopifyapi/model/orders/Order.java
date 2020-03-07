package com.beerent.shopifyapi.model.orders;

import com.beerent.shopifyapi.model.products.Product;
import com.beerent.shopifyapi.model.products.Products;
import com.beerent.shopifyapi.model.users.User;

import java.util.ArrayList;

public class Order {
    private Long ecommerceId;
    private User user;
    private Products products;

    public Order(Long ecommerceId, User user, Products products) {
        this.ecommerceId = ecommerceId;
        this.user = user;
        this.products = products;
    }

    public Long getEcommerceId() {
        return ecommerceId;
    }

    public User getUser() {
        return user;
    }

    public Products getProducts() {
        return products;
    }
}
