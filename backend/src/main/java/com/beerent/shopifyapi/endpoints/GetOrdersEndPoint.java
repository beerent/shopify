package com.beerent.shopifyapi.endpoints;

import com.beerent.shopifyapi.database.orders.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetOrdersEndPoint {
    @GetMapping("/get")
    @ResponseBody
    public String Get() {
        int count = new OrderService().findAll().size();
        return "found "+ count +" orders!\n";
    }

}