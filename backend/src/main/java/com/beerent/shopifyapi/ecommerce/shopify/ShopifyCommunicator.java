package com.beerent.shopifyapi.ecommerce.shopify;

import com.beerent.shopifyapi.ecommerce.EcommerceCommunicator;
import org.json.simple.JSONObject;

public class ShopifyCommunicator implements EcommerceCommunicator {
    @Override
    public JSONObject FetchOrders() {
        JSONObject obj = new JSONObject();
        return obj;
    }
}
