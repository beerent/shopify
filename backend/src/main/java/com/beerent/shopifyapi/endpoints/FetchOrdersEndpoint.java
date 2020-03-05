package com.beerent.shopifyapi.endpoints;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beerent.shopifyapi.ecommerce.EcommerceCommunicator;

@RestController
public class FetchOrdersEndpoint {
    private EcommerceCommunicator eCommerceCommunicator;

    @Autowired
    FetchOrdersEndpoint(EcommerceCommunicator communicator) {
        this.eCommerceCommunicator = communicator;
    }

    @GetMapping("/fetch")
    @ResponseBody
    public String Fetch() {
        eCommerceCommunicator.FetchOrders();
        return "fetching!\n";
    }
}