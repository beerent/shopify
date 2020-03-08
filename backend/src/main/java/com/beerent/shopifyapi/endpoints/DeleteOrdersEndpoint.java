package com.beerent.shopifyapi.endpoints;

import com.beerent.shopifyapi.database.orders.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DeleteOrdersEndpoint {

    @DeleteMapping("/delete")
    @ResponseBody
    public String Delete() {
        new OrderService().deleteAll();
        return "deleting orders!\n";
    }

}