package com.beerent.shopifyapi.database.orders;

import com.beerent.shopifyapi.database.users.UserService;
import com.beerent.shopifyapi.model.orders.Orders;
import com.beerent.shopifyapi.model.users.User;

import java.util.ArrayList;
import java.util.List;

public class OrdersDao {
    public void PersistOrders(Orders orders) {
        new UserService().persist(orders.GetUsers());
    }
}
