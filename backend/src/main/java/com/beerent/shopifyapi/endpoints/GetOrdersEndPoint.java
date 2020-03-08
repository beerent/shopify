package com.beerent.shopifyapi.endpoints;

import com.beerent.shopifyapi.database.orders.OrderService;
import com.beerent.shopifyapi.model.orders.OrderJsonUtil;
import com.beerent.shopifyapi.model.orders.OrderModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetOrdersEndPoint {
    @GetMapping("/get")
    @ResponseBody
    public String Get() {
        List<OrderModel> orders = new OrderService().findAll();
        return new OrderJsonUtil().toJson(orders).toJSONString();
    }

}