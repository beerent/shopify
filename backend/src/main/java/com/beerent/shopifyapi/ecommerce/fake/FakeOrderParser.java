package com.beerent.shopifyapi.ecommerce.fake;

import com.beerent.shopifyapi.ecommerce.IEcommerceOrderParser;
import com.beerent.shopifyapi.model.orders.Order;
import com.beerent.shopifyapi.model.orders.OrderProductMap;
import com.beerent.shopifyapi.model.products.Product;
import com.beerent.shopifyapi.model.users.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FakeOrderParser implements IEcommerceOrderParser {

    private static final String ORDERS = "orders";

    private static final String USER = "customer";
    private static final String USER_FIRST_NAME = "first_name";
    private static final String USER_LAST_NAME = "last_name";
    private static final String USER_EMAIL = "email";
    private static final String USER_PHONE_NUMBER = "phone_number";

    private static final String PRODUCTS = "products";
    private static final String PRODUCT_NAME = "name";
    private static final String PRODUCT_QUANTITY = "quantity";
    private static final String PRODUCT_PRICE = "price";

    private static final String ORDER_ID = "id";
    private static final String ORDER_PROCESSED_TIMESTAMP = "date";

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
        Set<OrderProductMap> products = ParseProducts((JSONArray) orderJson.get(PRODUCTS));
        for (OrderProductMap product : products) {
            product.setOrder(order);
        }

        order.setExternalOrderId(ecommerceId);
        order.setUser(user);
        order.setOrdered(ordered);
        order.setProducts(products);

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
        String firstName = (String) userJson.get(USER_FIRST_NAME);
        String lastName = (String) userJson.get(USER_LAST_NAME);
        String email = (String) userJson.get(USER_EMAIL);
        String phoneNumber = (String) userJson.get(USER_PHONE_NUMBER);

        User user = new User(firstName, lastName, email, phoneNumber);
        return user;
    }

    /*
     * parse Products model
     */
    Set<OrderProductMap> ParseProducts(JSONArray productsJson) {
        Set<OrderProductMap> products = new HashSet<OrderProductMap>();

        for (int i = 0; i < productsJson.size(); i++) {
            JSONObject productJson = (JSONObject) productsJson.get(i);
            OrderProductMap product = ParseProduct(productJson);
            products.add(product);
        }

        return products;
    }

    /*
     * parse Product model
     */
    OrderProductMap ParseProduct(JSONObject productJson) {
        String name = (String) productJson.get(PRODUCT_NAME);
        Double price = (Double) productJson.get(PRODUCT_PRICE);
        Product product = new Product(name, price);

        Long quantity = new Long(1);
        if (productJson.get(PRODUCT_QUANTITY) != null) {
            quantity = (Long) productJson.get(PRODUCT_QUANTITY);
        }

        OrderProductMap opm = new OrderProductMap();
        opm.setQuantity(quantity);
        opm.setProduct(product);

        product.addOrderProduct(opm);

        return opm;
    }
}
