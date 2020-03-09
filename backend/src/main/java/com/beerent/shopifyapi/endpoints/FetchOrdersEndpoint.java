package com.beerent.shopifyapi.endpoints;

import com.beerent.shopifyapi.database.orders.OrderService;
import com.beerent.shopifyapi.ecommerce.IEcommerceOrdersService;
import com.beerent.shopifyapi.model.orders.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FetchOrdersEndpoint {
    private IEcommerceOrdersService eCommerceOrdersService;

    @Autowired
    private Boolean updateOrders;

    @Autowired
    FetchOrdersEndpoint(IEcommerceOrdersService communicator, Boolean updateOrders) {
        this.eCommerceOrdersService = communicator;
    }

    @PostMapping(value = "/v1/orders/fetch")
    public ResponseEntity Fetch() {
        List<Order> orders = eCommerceOrdersService.FetchOrders();
        OrderService orderService = new OrderService(this.updateOrders);
        orderService.persist(orders);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}