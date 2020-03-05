package com.beerent.shopifyapi.endpoints;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DeleteOrdersEndpoint {

    @GetMapping("/delete")
    @ResponseBody
    public String Delete() {
        return "deleting orders!";
    }

}