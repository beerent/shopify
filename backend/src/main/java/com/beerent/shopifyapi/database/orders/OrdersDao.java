package com.beerent.shopifyapi.database.orders;

import com.beerent.shopifyapi.database.products.ProductService;
import com.beerent.shopifyapi.database.users.UserService;
import com.beerent.shopifyapi.model.orders.OrderModel;
import com.beerent.shopifyapi.model.users.UserModel;

import java.util.ArrayList;
import java.util.List;

public class OrdersDao {
    public void PersistOrders(List<OrderModel> orders) {
        List<UserModel> users = new ArrayList<UserModel>();
        for (OrderModel order : orders) {
            //users.add(order.getUser());
        }
        new UserService().persist(users);
        int x = 5;

        //new ProductService().persist(orders.GetProducts());
        //new OrderService().persist(orders.GetOrders());
        //new OrderProductMapService().persist(orders.GetOrderProductMaps());
    }
}
