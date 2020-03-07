package com.beerent.shopifyapi.model.containers;

import com.beerent.shopifyapi.model.users.UserModel;

public class Order {
    private UserModel user;
    private Products products;

    public Order(UserModel user, Products products) {
        this.user = user;
        this.products = products;
    }

    public UserModel getUser() {
        return user;
    }

    public Products getProducts() {
        return products;
    }
}
