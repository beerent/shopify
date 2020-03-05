package com.beerent.shopifyapi.ecommerce.fake;

import com.beerent.shopifyapi.ecommerce.EcommerceCommunicator;
import org.json.simple.JSONObject;

public class FakeCommunicator implements EcommerceCommunicator {
    private void CreateCustomer(String first, String last) {

    }

    private void CreateProduct() {

    }

    private void CreateOrder() {

    }

    @Override
    public JSONObject FetchOrders() {
        JSONObject obj = new JSONObject();
        return obj;
    }
}
