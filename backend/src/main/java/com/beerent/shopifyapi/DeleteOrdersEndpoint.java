package com.beerent.shopifyapi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DeleteOrdersEndpoint {

    @GetMapping("/delete")
    @ResponseBody
    public String HelloWorld() {
        return "deleting orders!";
    }

}