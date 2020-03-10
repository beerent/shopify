package com.beerent.shopifyapi.model.orders;

import com.beerent.shopifyapi.model.users.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.DateFormat;
import java.util.List;
import java.util.Set;

public class OrderJsonUtil {
    public JSONObject toJson(List<Order> orders) {
        JSONObject json = new JSONObject();

        JSONArray ordersJson = new JSONArray();
        for (Order order : orders) {
            JSONObject orderJson = orderToJson(order);
            ordersJson.add(orderJson);
        }
        json.put("orders", ordersJson);

        return json;
    }

    private JSONObject orderToJson(Order order) {
        JSONObject orderJson = new JSONObject();

        orderJson.put("id", order.getId());
        orderJson.put("user", userToJson(order.getUser()));
        orderJson.put("external_order_id", order.getExternalId());
        String formattedDate =
                DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(order.getOrdered());
        orderJson.put("date_ordered", formattedDate);
        orderJson.put("products", productsToJson(order.getProducts()));

        return orderJson;
    }

    private JSONObject userToJson(User user) {
        JSONObject userJson = new JSONObject();

        userJson.put("id", user.getId());
        userJson.put("email", user.getEmail());
        userJson.put("first_name", user.getFirstName());
        userJson.put("last_name", user.getLastName());
        userJson.put("phone_number", user.getPhoneNumber());

        return userJson;
    }

    private JSONArray productsToJson(Set<OrderProductMap> products) {
        JSONArray productsJson = new JSONArray();

        for (OrderProductMap product : products) {
            JSONObject productJson = new JSONObject();
            productJson.put("id", product.getId());
            productJson.put("name", product.getProduct().getName());
            productJson.put("price", product.getProduct().getPrice());
            productJson.put("quantity", product.getQuantity());
            productsJson.add(productJson);
        }

        return productsJson;
    }


}
