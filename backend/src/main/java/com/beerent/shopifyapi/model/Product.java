package com.beerent.shopifyapi.model;

public class Product {
    private Long ecommerceId;
    private String name;
    private Double price;

    public Product(Long ecommerceId, String name, Double price) {
        this.ecommerceId = ecommerceId;
        this.name = name;
        this.price = price;
    }
}
