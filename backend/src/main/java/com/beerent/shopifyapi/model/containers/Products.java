package com.beerent.shopifyapi.model.containers;

import com.beerent.shopifyapi.model.products.ProductModel;

import java.util.ArrayList;

public class Products {
    ArrayList<ProductModel> products;

    public Products(ArrayList<ProductModel> products) {
        this.products = products;
    }

    public ArrayList<ProductModel> GetProducts() {
        return this.products;
    }
}
