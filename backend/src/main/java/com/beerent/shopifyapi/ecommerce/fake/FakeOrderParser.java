package com.beerent.shopifyapi.ecommerce.fake;

import com.beerent.shopifyapi.model.orders.OrderModel;
import com.beerent.shopifyapi.model.products.ProductModel;
import com.beerent.shopifyapi.model.users.UserModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FakeOrderParser {

    private static final String ORDERS = "orders";

    private static final String USER = "customer";
    private static final String USER_FIRST_NAME = "first_name";
    private static final String USER_LAST_NAME = "last_name";
    private static final String USER_EMAIL = "email";
    private static final String USER_PHONE_NUMBER = "phone_number";
    private static final String USER_ID = "id";

    private static final String PRODUCTS = "products";
    private static final String PRODUCT_NAME = "name";
    private static final String PRODUCT_PRICE = "price";
    private static final String PRODUCT_ID = "id";

    private static final String ORDER_ID = "id";
    private static final String ORDER_PROCESSED_TIMESTAMP = "date";

    private Map<Long, UserModel> users;

    public FakeOrderParser() {
        this.users = new HashMap<Long, UserModel>();
    }

    public List<OrderModel> ParseOrders(JSONObject obj) {
        JSONArray ordersJson = (JSONArray) obj.get(ORDERS);
        return ParseOrders(ordersJson);
    }

    public List<OrderModel> ParseOrders(JSONArray ordersJson) {
        ArrayList<OrderModel> orders = new ArrayList<OrderModel>();

        for (int i = 0; i < ordersJson.size(); i++) {
            JSONObject orderJson = (JSONObject) ordersJson.get(i);
            OrderModel order = ParseOrder(orderJson);
            orders.add(order);
        }

        return orders;
    }

    public OrderModel ParseOrder(JSONObject orderJson) {
        OrderModel order = new OrderModel();

        JSONObject userJson = (JSONObject) orderJson.get(USER);
        UserModel user = ParseUser(userJson);
        order.setUser(user);

        Long ecommerceId = (Long) orderJson.get(ORDER_ID);
        String processedTimestamp = (String) orderJson.get(ORDER_PROCESSED_TIMESTAMP);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = formatter.parse(processedTimestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        order.setOrdered(date);
        order.setExternalOrderId(ecommerceId);

        return order;
    }

    public UserModel ParseUser(JSONObject userJson) {
        Long ecommerceId = (Long) userJson.get(USER_ID);

        UserModel user = null;
        if (this.users.containsKey(ecommerceId)) {
            user = this.users.get(ecommerceId);
        } else {
            String firstName = (String) userJson.get(USER_FIRST_NAME);
            String lastName = (String) userJson.get(USER_LAST_NAME);
            String email = (String) userJson.get(USER_EMAIL);
            String phoneNumber = (String) userJson.get(USER_PHONE_NUMBER);
            user = new UserModel(firstName, lastName, email, phoneNumber);
            this.users.put(ecommerceId, user);
        }

        return user;
    }

    ArrayList<ProductModel> ParseProducts(JSONArray productsJson) {
        ArrayList<ProductModel> products = new ArrayList<ProductModel>();

        for (int i = 0; i < productsJson.size(); i++) {
            JSONObject productJson = (JSONObject) productsJson.get(i);
            ProductModel product = ParseProduct(productJson);
            products.add(product);
        }

        return products;
    }

    ProductModel ParseProduct(JSONObject productJson) {
        String name = (String) productJson.get(PRODUCT_NAME);
        Double price = Double.parseDouble((String) productJson.get(PRODUCT_PRICE));

        return new ProductModel(name, price);
    }
}
