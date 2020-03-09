package com.beerent.shopifyapi.ecommerce.fake;

import com.beerent.shopifyapi.model.orders.Order;
import com.beerent.shopifyapi.model.orders.OrderProductMap;
import com.beerent.shopifyapi.model.products.Product;
import com.beerent.shopifyapi.model.users.User;
import javafx.util.Pair;
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
    private static final String PRODUCT_QUANTITY = "quantity";
    private static final String PRODUCT_PRICE = "price";
    private static final String PRODUCT_ID = "id";

    private static final String ORDER_ID = "id";
    private static final String ORDER_PROCESSED_TIMESTAMP = "date";

    private Map<Long, User> users;
    private Map<Long, Product> products;

    public FakeOrderParser() {
        this.users = new HashMap<Long, User>();
        this.products = new HashMap<Long, Product>();
    }

    public List<Order> ParseOrders(JSONObject obj) {
        JSONArray ordersJson = (JSONArray) obj.get(ORDERS);
        return ParseOrders(ordersJson);
    }

    public List<Order> ParseOrders(JSONArray ordersJson) {
        ArrayList<Order> orders = new ArrayList<Order>();

        for (int i = 0; i < ordersJson.size(); i++) {
            JSONObject orderJson = (JSONObject) ordersJson.get(i);
            Order order = ParseOrder(orderJson);
            orders.add(order);
        }

        return orders;
    }

    public Order ParseOrder(JSONObject orderJson) {
        Order order = new Order();

        Long ecommerceId = (Long) orderJson.get(ORDER_ID);
        User user = ParseUser((JSONObject) orderJson.get(USER));
        Date ordered = ParseDate((String)orderJson.get(ORDER_PROCESSED_TIMESTAMP));
        Set<Pair<Product, Long>> products = ParseProducts((JSONArray) orderJson.get(PRODUCTS));

        Set<OrderProductMap> orderProductMap = new HashSet<OrderProductMap>();
        for (Pair<Product, Long> product : products) {
            OrderProductMap opr = new OrderProductMap(order, product.getKey(), product.getValue());
            product.getKey().addOrderProduct(opr);
            orderProductMap.add(opr);
        }

        order.setExternalOrderId(ecommerceId);
        order.setUser(user);
        order.setOrdered(ordered);
        order.setProducts(orderProductMap);

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
    public User ParseUser(JSONObject userJson) {
        Long ecommerceId = (Long) userJson.get(USER_ID);

        User user = null;
        if (this.users.containsKey(ecommerceId)) {
            user = this.users.get(ecommerceId);
        } else {
            String firstName = (String) userJson.get(USER_FIRST_NAME);
            String lastName = (String) userJson.get(USER_LAST_NAME);
            String email = (String) userJson.get(USER_EMAIL);
            String phoneNumber = (String) userJson.get(USER_PHONE_NUMBER);
            user = new User(firstName, lastName, email, phoneNumber);
            this.users.put(ecommerceId, user);
        }

        return user;
    }

    /*
     * parse Products model
     */
    Set<Pair<Product, Long>> ParseProducts(JSONArray productsJson) {
        Set<Pair<Product, Long>> products = new HashSet<Pair<Product, Long>>();

        for (int i = 0; i < productsJson.size(); i++) {
            JSONObject productJson = (JSONObject) productsJson.get(i);
            Pair<Product, Long> product = ParseProduct(productJson);
            products.add(product);
        }

        return products;
    }

    /*
     * parse Product model
     */
    Pair<Product, Long> ParseProduct(JSONObject productJson) {
        Long ecommerceId = (Long) productJson.get(PRODUCT_ID);

        Product product = null;
        if (this.products.containsKey(ecommerceId)) {
            product = this.products.get(ecommerceId);
        } else {
            String name = (String) productJson.get(PRODUCT_NAME);
            Double price = (Double) productJson.get(PRODUCT_PRICE);

            product = new Product(name, price);
            this.products.put(ecommerceId, product);
        }

        Long quantity = new Long(1);
        if (productJson.get(PRODUCT_QUANTITY) != null) {
            quantity = (Long) productJson.get(PRODUCT_QUANTITY);
        }

        return new Pair<Product, Long>(product, quantity);
    }
}
