package com.beerent.shopifyapi.model;

import java.util.ArrayList;

public class Order {
    private Long ecommerceId;
    private User user;
    private ArrayList<Product> products;

    public Order(Long ecommerceId, User user, ArrayList<Product> products) {
        this.ecommerceId = ecommerceId;
        this.user = user;
        this.products = products;
    }
}
