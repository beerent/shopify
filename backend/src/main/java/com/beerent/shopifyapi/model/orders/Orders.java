package com.beerent.shopifyapi.model.orders;

import java.util.ArrayList;

public class Orders {
    private ArrayList<Order> orders;

    public Orders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public ArrayList<Order> GetOrders() {
        return this.orders;
    }
}
