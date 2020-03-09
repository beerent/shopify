package com.beerent.shopifyapi.ecommerce;

import com.beerent.shopifyapi.model.orders.Order;
import org.json.simple.JSONObject;

import java.util.List;

public interface IEcommerceOrderParser {
    List<Order> ParseOrders(JSONObject obj);
}
