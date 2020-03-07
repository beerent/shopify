package com.beerent.shopifyapi.model.containers;

import com.beerent.shopifyapi.model.orders.OrderModel;
import com.beerent.shopifyapi.model.users.UserModel;

public class Order {
    private long externalId;
    private String processedDate;
    private UserModel user;
    private Products products;

    public Order(long externalId, String processedDate, UserModel user, Products products) {
        this.externalId = externalId;
        this.processedDate = processedDate;
        this.user = user;
        this.products = products;
    }

    public OrderModel getOrder() {
        return new OrderModel(this.externalId, this.user.getId());
    }

    public UserModel getUser() {
        return user;
    }

    public Products getProducts() {
        return products;
    }
}
