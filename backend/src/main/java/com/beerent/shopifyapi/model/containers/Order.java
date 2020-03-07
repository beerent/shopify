package com.beerent.shopifyapi.model.containers;

import com.beerent.shopifyapi.model.orders.OrderModel;
import com.beerent.shopifyapi.model.users.UserModel;

import java.util.Date;

public class Order {
    private long externalId;
    private Date ordered;
    private UserModel user;
    private Products products;

    public Order(long externalId, Date ordered, UserModel user, Products products) {
        this.externalId = externalId;
        this.ordered = ordered;
        this.user = user;
        this.products = products;
    }

    public OrderModel getOrder() {
        return new OrderModel(this.externalId, this.ordered, this.user);
    }

    public UserModel getUser() {
        return user;
    }

    public Products getProducts() {
        return products;
    }
}
