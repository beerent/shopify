package com.beerent.shopifyapi.endpoints;

import com.beerent.shopifyapi.database.OrderDatabaseReferenceResolver;
import com.beerent.shopifyapi.database.orders.OrderService;
import com.beerent.shopifyapi.ecommerce.IEcommerceOrdersProvider;
import com.beerent.shopifyapi.model.orders.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FetchOrdersEndpoint {
    private IEcommerceOrdersProvider ecommerceOrdersService;

    @Autowired
    public FetchOrdersEndpoint(IEcommerceOrdersProvider ordersService) {
        this.ecommerceOrdersService = ordersService;
    }

    @PostMapping(value = "/v1/orders/fetch")
    public ResponseEntity Fetch() {
        List<Order> orders = ecommerceOrdersService.FetchOrders();
        new OrderDatabaseReferenceResolver().resolveDatabaseReferences(orders);
        OrderService orderService = new OrderService();
        orderService.persist(orders);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}