package com.beerent.shopifyapi.ecommerce;

import com.beerent.shopifyapi.model.orders.Orders;
import org.json.simple.JSONObject;

public interface OrderParser {
    public Orders ParseOrders(JSONObject ordersJson);
}