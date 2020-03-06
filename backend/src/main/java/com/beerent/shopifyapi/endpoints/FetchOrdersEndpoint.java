package com.beerent.shopifyapi.endpoints;

import com.beerent.shopifyapi.model.Order;
import com.beerent.shopifyapi.model.util.ShopifyOrderParser;
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
    private ShopifyOrderParser orderParser;

    @Autowired
    FetchOrdersEndpoint(EcommerceCommunicator communicator) {
        this.eCommerceCommunicator = communicator;
        this.orderParser = new ShopifyOrderParser();
    }

    @GetMapping("/fetch")
    @ResponseBody
    public String Fetch() {
        JSONObject ordersJson = eCommerceCommunicator.FetchOrders();
        ArrayList<Order> orders = this.orderParser.ParseOrders(ordersJson);
        return "fetching!\n";
    }
}