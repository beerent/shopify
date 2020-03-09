package com.beerent.shopifyapi.model.orders;

import com.beerent.shopifyapi.model.products.ProductModel;
import com.beerent.shopifyapi.model.users.UserModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Set;

public class OrderJsonUtil {
    public JSONObject toJson(List<OrderModel> orders) {
        JSONObject json = new JSONObject();

        JSONArray ordersJson = new JSONArray();
        for (OrderModel order : orders) {
            JSONObject orderJson = orderToJson(order);
            ordersJson.add(orderJson);
        }
        json.put("orders", ordersJson);

        return json;
    }

    private JSONObject orderToJson(OrderModel order) {
        JSONObject orderJson = new JSONObject();

        orderJson.put("id", order.getId());
        orderJson.put("user", userToJson(order.getUser()));
        orderJson.put("external_order_id", order.getExternalOrderId());
        orderJson.put("date_ordered", ""+order.getOrdered());
        //orderJson.put("products", productsToJson(order.getProducts()));

        return orderJson;
    }

    private JSONObject userToJson(UserModel user) {
        JSONObject userJson = new JSONObject();

        userJson.put("id", user.getId());
        userJson.put("email", user.getEmail());
        userJson.put("first_name", user.getFirstName());
        userJson.put("last_name", user.getLastName());
        userJson.put("phone_number", user.getPhoneNumber());

        return userJson;
    }

    private JSONArray productsToJson(Set<ProductModel> products) {
        JSONArray productsJson = new JSONArray();

        for (ProductModel product : products) {
            JSONObject productJson = new JSONObject();
            productJson.put("id", product.getId());
            productJson.put("name", product.getName());
            productJson.put("price", product.getPrice());
            productsJson.add(productJson);
        }

        return productsJson;
    }


}
