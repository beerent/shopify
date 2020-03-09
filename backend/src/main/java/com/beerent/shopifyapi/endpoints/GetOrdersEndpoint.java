package com.beerent.shopifyapi.endpoints;

import com.beerent.shopifyapi.database.orders.OrderService;
import com.beerent.shopifyapi.model.orders.OrderJsonUtil;
import com.beerent.shopifyapi.model.orders.Order;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetOrdersEndpoint {
    @GetMapping(value = "/v1/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity Get() {
        List<Order> orders = new OrderService().findAll();
        String responseJson = new OrderJsonUtil().toJson(orders).toJSONString();

        return ResponseEntity.ok().body(responseJson);
    }
}