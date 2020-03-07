package com.beerent.shopifyapi.endpoints;

import com.beerent.shopifyapi.database.orders.OrderService;
import com.beerent.shopifyapi.model.orders.OrderModel;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beerent.shopifyapi.ecommerce.EcommerceCommunicator;

import java.util.List;

@RestController
public class FetchOrdersEndpoint {
    private EcommerceCommunicator eCommerceCommunicator;

    @Autowired
    FetchOrdersEndpoint(EcommerceCommunicator communicator) {
        this.eCommerceCommunicator = communicator;
    }

    @GetMapping("/fetch")
    @ResponseBody //what is this?
    public String Fetch() {
        List<OrderModel> orders = eCommerceCommunicator.FetchOrders();

        OrderService orderService = new OrderService();
        orderService.persist(orders);
        List<OrderModel> queriedOrders = orderService.findAll();
        return "fetching!\n";
    }
}