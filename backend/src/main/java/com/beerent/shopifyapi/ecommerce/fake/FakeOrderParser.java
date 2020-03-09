package com.beerent.shopifyapi.ecommerce.fake;

import com.beerent.shopifyapi.model.orders.OrderModel;
import com.beerent.shopifyapi.model.products.ProductModel;
import com.beerent.shopifyapi.model.users.UserModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
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
    private Map<Long, ProductModel> products;

    public FakeOrderParser() {
        this.users = new HashMap<Long, UserModel>();
        this.products = new HashMap<Long, ProductModel>();
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

        Long ecommerceId = (Long) orderJson.get(ORDER_ID);
        UserModel user = ParseUser((JSONObject) orderJson.get(USER));
        Date ordered = ParseDate((String)orderJson.get(ORDER_PROCESSED_TIMESTAMP));
        Set<ProductModel> products = ParseProducts((JSONArray) orderJson.get(PRODUCTS));

        order.setExternalOrderId(ecommerceId);
        order.setUser(user);
        order.setOrdered(ordered);
        //order.setProducts(products);

        return order;
    }

    Date ParseDate(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;

        try {
            date = formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /*
     * parse User model
    */
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

    /*
     * parse Products model
     */
    Set<ProductModel> ParseProducts(JSONArray productsJson) {
        Set<ProductModel> products = new HashSet<ProductModel>();

        for (int i = 0; i < productsJson.size(); i++) {
            JSONObject productJson = (JSONObject) productsJson.get(i);
            ProductModel product = ParseProduct(productJson);
            products.add(product);
        }

        return products;
    }

    /*
     * parse Product model
     */
    ProductModel ParseProduct(JSONObject productJson) {
        Long ecommerceId = (Long) productJson.get(PRODUCT_ID);

        ProductModel product = null;
        if (this.products.containsKey(ecommerceId)) {
            product = this.products.get(ecommerceId);
        } else {
            String name = (String) productJson.get(PRODUCT_NAME);
            Double price = (Double) productJson.get(PRODUCT_PRICE);

            product = new ProductModel(name, price);
            this.products.put(ecommerceId, product);
        }

        return product;
    }
}
