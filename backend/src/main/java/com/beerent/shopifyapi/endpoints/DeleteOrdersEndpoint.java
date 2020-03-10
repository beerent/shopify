package com.beerent.shopifyapi.endpoints;

import com.beerent.shopifyapi.database.orders.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteOrdersEndpoint {
    private OrderService orderService;

    public DeleteOrdersEndpoint() {
        this.orderService = new OrderService();
    }

    public DeleteOrdersEndpoint(OrderService orderService) {
        this.orderService = orderService;
    }

    @DeleteMapping(value = "/v1/orders")
    public ResponseEntity Delete() {
        new OrderService().deleteAll();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}