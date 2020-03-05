package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FetchOrdersEndpoint {

    @GetMapping("/fetch")
    @ResponseBody
    public String HelloWorld() {
        return "fetching orders!";
    }

}