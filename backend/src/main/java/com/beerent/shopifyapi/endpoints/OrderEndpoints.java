package com.beerent.shopifyapi.endpoints;

import com.beerent.shopifyapi.database.orders.OrderService;
import com.beerent.shopifyapi.ecommerce.EcommerceCommunicator;
import com.beerent.shopifyapi.model.orders.OrderJsonUtil;
import com.beerent.shopifyapi.model.orders.OrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderEndpoints {
    private EcommerceCommunicator eCommerceCommunicator;

    @Autowired
    OrderEndpoints(EcommerceCommunicator communicator) {
        this.eCommerceCommunicator = communicator;
    }

    @RequestMapping(value = "/fetch", method = RequestMethod.POST)
    public ResponseEntity Fetch() {
        List<OrderModel> orders = eCommerceCommunicator.FetchOrders();
        OrderService orderService = new OrderService();
        orderService.persist(orders);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity Get() {
        List<OrderModel> orders = new OrderService().findAll();
        String responseJson = new OrderJsonUtil().toJson(orders).toJSONString();

        return ResponseEntity.ok().body(responseJson);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity Delete() {
        new OrderService().deleteAll();

        return ResponseEntity.ok().build();
    }

}