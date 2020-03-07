package com.beerent.shopifyapi.model.containers;

import com.beerent.shopifyapi.model.containers.Order;
import com.beerent.shopifyapi.model.orders.OrderModel;
import com.beerent.shopifyapi.model.products.ProductModel;
import com.beerent.shopifyapi.model.users.UserModel;

import java.util.ArrayList;
import java.util.List;

public class Orders {
    private ArrayList<Order> orders;

    public Orders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public List<OrderModel> GetOrders() {
        List<OrderModel> orders = new ArrayList<OrderModel>();

        for (Order order : this.orders) {
            orders.add(order.getOrder());
        }

        return orders;
    }

    public List<UserModel> GetUsers() {
        List<UserModel> users = new ArrayList<UserModel>();

        for (Order order : orders) {
            users.add(order.getUser());
        }

        return users;
    }

    public List<ProductModel> GetProducts() {
        List<ProductModel> allProducts = new ArrayList<ProductModel>();

        for (Order order : orders) {
            allProducts.addAll(order.getProducts().GetProducts());
        }

        return allProducts;
    }
}
