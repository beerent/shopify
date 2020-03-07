package com.beerent.shopifyapi.model.orders;

import com.beerent.shopifyapi.model.products.Product;
import com.beerent.shopifyapi.model.products.Products;
import com.beerent.shopifyapi.model.users.User;

import java.util.ArrayList;

public class Order {
    private User user;
    private Products products;

    public Order(User user, Products products) {
        this.user = user;
        this.products = products;
    }

    public User getUser() {
        return user;
    }

    public Products getProducts() {
        return products;
    }
}
