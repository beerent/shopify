package com.beerent.shopifyapi.endpoints;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beerent.shopifyapi.ecommerce.EcommerceCommunicator;

@Controller
public class FetchOrdersEndpoint {
    private EcommerceCommunicator eCommerceCommunicator;
    private boolean eCommerceCommunicatorSet;

    FetchOrdersEndpoint() {
        this.eCommerceCommunicatorSet = false;
    }

    public void SetECommerceCommunicator(EcommerceCommunicator communicator) {
        this.eCommerceCommunicator = communicator;
        this.eCommerceCommunicatorSet = true;
    }

    @GetMapping("/fetch")
    @ResponseBody
    public String Fetch() {
        return "fetching!";
    }
}