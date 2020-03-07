package com.beerent.shopifyapi.endpoints;

import com.beerent.shopifyapi.database.orders.OrdersDao;
import com.beerent.shopifyapi.model.orders.Orders;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beerent.shopifyapi.ecommerce.EcommerceCommunicator;

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
        Orders orders = eCommerceCommunicator.FetchOrders();
        ordersDao.PersistOrders(orders);
        return "fetching!\n";
    }
}