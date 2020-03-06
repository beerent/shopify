package com.beerent.shopifyapi.model.products;

import java.util.ArrayList;

public class Products {
    ArrayList<Product> products;

    public Products(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Product> GetProducts() {
        return this.products;
    }
}
