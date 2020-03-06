package com.beerent.shopifyapi.endpoints;

import com.beerent.shopifyapi.ecommerce.OrderParser;
import com.beerent.shopifyapi.model.orders.Orders;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beerent.shopifyapi.ecommerce.EcommerceCommunicator;

import java.util.ArrayList;

@RestController
public class FetchOrdersEndpoint {
    private EcommerceCommunicator eCommerceCommunicator;
    private OrderParser orderParser;

    @Autowired
    FetchOrdersEndpoint(EcommerceCommunicator communicator, OrderParser orderParser) {
        this.eCommerceCommunicator = communicator;
        this.orderParser = orderParser;
    }

    @GetMapping("/fetch")
    @ResponseBody
    public String Fetch() {
        JSONObject ordersJson = eCommerceCommunicator.FetchOrders();
        Orders orders = this.orderParser.ParseOrders(ordersJson);
        return "fetching!\n";
    }
}