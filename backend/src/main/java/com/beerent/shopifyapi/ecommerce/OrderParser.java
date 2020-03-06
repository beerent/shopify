package com.beerent.shopifyapi.ecommerce;

import java.util.ArrayList;
import com.beerent.shopifyapi.model.Order;
import org.json.simple.JSONObject;

public interface OrderParser {
    ArrayList<Order> ParseOrders(JSONObject ordersJson);
}