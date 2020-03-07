package com.beerent.shopifyapi.model.orders;

import com.beerent.shopifyapi.model.products.Product;
import com.beerent.shopifyapi.model.users.User;

import java.util.ArrayList;
import java.util.List;

public class Orders {
    private ArrayList<Order> orders;

    public Orders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public List<Order> GetOrders() {
        return this.orders;
    }

    public List<User> GetUsers() {
        List<User> users = new ArrayList<User>();

        for (Order order : orders) {
            users.add(order.getUser());
        }

        return users;
    }

    public List<Product> GetProducts() {
        List<Product> allProducts = new ArrayList<Product>();

        for (Order order : orders) {
            allProducts.addAll(order.getProducts().GetProducts());
        }

        return allProducts;
    }
}
