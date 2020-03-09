package com.beerent.shopifyapi.endpoints;

import com.beerent.shopifyapi.database.orders.OrderService;
import com.beerent.shopifyapi.ecommerce.EcommerceCommunicator;
import com.beerent.shopifyapi.model.orders.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FetchOrdersEndpoint {
    private EcommerceCommunicator eCommerceCommunicator;

    @Autowired
    FetchOrdersEndpoint(EcommerceCommunicator communicator) {
        this.eCommerceCommunicator = communicator;
    }

    @RequestMapping(value = "/fetch", method = RequestMethod.POST)
    public ResponseEntity Fetch() {
        List<Order> orders = eCommerceCommunicator.FetchOrders();
        OrderService orderService = new OrderService();
        orderService.persist(orders);

        return ResponseEntity.ok().build();
    }
}