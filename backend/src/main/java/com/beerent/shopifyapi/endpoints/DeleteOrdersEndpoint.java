package com.beerent.shopifyapi.endpoints;

import com.beerent.shopifyapi.database.orders.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class DeleteOrdersEndpoint {

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity Delete() {
        new OrderService().deleteAll();
        return ResponseEntity.ok().build();
    }

}