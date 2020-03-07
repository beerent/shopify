package com.beerent.shopifyapi.database.orders;

import com.beerent.shopifyapi.database.products.ProductService;
import com.beerent.shopifyapi.database.users.UserService;
import com.beerent.shopifyapi.model.orders.Orders;

public class OrdersDao {
    public void PersistOrders(Orders orders) {
        new UserService().persist(orders.GetUsers());
        new ProductService().persist(orders.GetProducts());
    }
}
