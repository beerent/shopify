package com.beerent.shopifyapi.ecommerce.fake;

import com.beerent.shopifyapi.ecommerce.OrderParser;
import com.beerent.shopifyapi.model.Order;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class FakeOrderParser implements OrderParser {

    @Override
    public ArrayList<Order> ParseOrders(JSONObject ordersJson) {
        ArrayList<Order> orders = new ArrayList<Order>();
        return orders;
    }
}
