package com.beerent.shopifyapi.model.util;

import java.util.ArrayList;
import com.beerent.shopifyapi.model.Order;
import com.beerent.shopifyapi.model.Product;
import com.beerent.shopifyapi.model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ShopifyOrderParser {
    private static final String ORDERS = "orders";

    private static final String USER = "customer";
    private static final String USER_FIRST_NAME = "first_name";
    private static final String USER_LAST_NAME = "last_name";
    private static final String USER_EMAIL = "email";
    private static final String USER_PHONE_NUMBER = "phone";
    private static final String USER_ID = "id";

    private static final String PRODUCTS = "line_items";
    private static final String PRODUCT_NAME = "title";
    private static final String PRODUCT_PRICE = "price";
    private static final String PRODUCT_ID = "id";

    private static final String ORDER_ID = "id";


    public ArrayList<Order> ParseOrders(JSONObject obj) {
        JSONArray ordersJson = (JSONArray) obj.get(ORDERS);
        return ParseOrders(ordersJson);
    }

    private ArrayList<Order> ParseOrders(JSONArray ordersJson) {
        ArrayList<Order> orders = new ArrayList<Order>();

        for (int i = 0; i < ordersJson.size(); i++) {
            JSONObject orderJson = (JSONObject) ordersJson.get(i);
            Order order = ParseOrder(orderJson);
            orders.add(order);
        }

        return orders;
    }

    private Order ParseOrder(JSONObject orderJson) {
        Long ecommerceId = (Long) orderJson.get(ORDER_ID);

        JSONObject userJson = (JSONObject) orderJson.get(USER);
        User user = ParseUser(userJson);

        JSONArray productsJson = (JSONArray) orderJson.get(PRODUCTS);
        ArrayList<Product> products = ParseProducts(productsJson);

        Order order = new Order(ecommerceId, user, products);
        return order;
    }

    User ParseUser(JSONObject userJson) {
        Long ecommerceId = (Long) userJson.get(USER_ID);
        String firstName = (String) userJson.get(USER_FIRST_NAME);
        String lastName = (String) userJson.get(USER_LAST_NAME);
        String email = (String) userJson.get(USER_EMAIL);
        String phoneNumber = (String) userJson.get(USER_PHONE_NUMBER);

        return new User(ecommerceId, firstName, lastName, email, phoneNumber);
    }

    ArrayList<Product> ParseProducts(JSONArray productsJson) {
        ArrayList<Product> products = new ArrayList<Product>();

        for (int i = 0; i < productsJson.size(); i++) {
            JSONObject productJson = (JSONObject) productsJson.get(i);
            Product product = ParseProduct(productJson);
            products.add(product);
        }

        return products;
    }

    Product ParseProduct(JSONObject productJson) {
        Long ecommerceId = (Long) productJson.get(PRODUCT_ID);
        String name = (String) productJson.get(PRODUCT_NAME);
        Double price = Double.parseDouble((String) productJson.get(PRODUCT_PRICE));

        return new Product(ecommerceId, name, price);
    }
}
