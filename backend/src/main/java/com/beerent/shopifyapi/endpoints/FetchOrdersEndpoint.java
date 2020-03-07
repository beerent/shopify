package com.beerent.shopifyapi.endpoints;

import com.beerent.shopifyapi.database.orders.OrderService;
import com.beerent.shopifyapi.database.orders.OrdersDao;
import com.beerent.shopifyapi.model.containers.Orders;
import com.beerent.shopifyapi.model.orders.OrderModel;
import com.beerent.shopifyapi.model.users.UserModel;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beerent.shopifyapi.ecommerce.EcommerceCommunicator;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FetchOrdersEndpoint {
    private EcommerceCommunicator eCommerceCommunicator;
    private OrdersDao ordersDao;

    @Autowired
    FetchOrdersEndpoint(EcommerceCommunicator communicator) {
        this.eCommerceCommunicator = communicator;
        this.ordersDao = new OrdersDao();
    }

    @GetMapping("/fetch")
    @ResponseBody //what is this?
    public String Fetch() {
        List<OrderModel> orders = eCommerceCommunicator.FetchOrders();

        OrderService orderService = new OrderService();
        orderService.persist(orders);
        return "fetching!\n";
    }
}