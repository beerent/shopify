package com.beerent.shopifyapi.endpoints;

import com.beerent.shopifyapi.database.orders.OrderService;
import com.beerent.shopifyapi.ecommerce.IEcommerceOrdersService;
import com.beerent.shopifyapi.model.orders.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FetchOrdersEndpoint {
    private IEcommerceOrdersService eCommerceOrdersService;

    @Autowired
    FetchOrdersEndpoint(IEcommerceOrdersService communicator) {
        this.eCommerceOrdersService = communicator;
    }

    @PostMapping(value = "/v1/orders/fetch")
    public ResponseEntity Fetch() {
        List<Order> orders = eCommerceOrdersService.FetchOrders();
        OrderService orderService = new OrderService();
        orderService.persist(orders);

        return ResponseEntity.ok().build();
    }
}