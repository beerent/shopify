package com.beerent.shopifyapi.ecommerce.shopify;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.beerent.shopifyapi.ecommerce.IEcommerceOrderParser;
import com.beerent.shopifyapi.model.orders.Order;
import com.beerent.shopifyapi.model.orders.OrderProductMap;
import com.beerent.shopifyapi.model.products.Product;
import com.beerent.shopifyapi.model.users.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ShopifyOrderParser implements IEcommerceOrderParser {
    private static final String ORDERS = "orders";

    private static final String USER = "customer";
    private static final String USER_FIRST_NAME = "first_name";
    private static final String USER_LAST_NAME = "last_name";
    private static final String USER_EMAIL = "email";
    private static final String USER_PHONE_NUMBER = "phone";
    private static final String USER_ID = "id";

    private static final String PRODUCTS = "line_items";
    private static final String PRODUCT_NAME = "title";
    private static final String PRODUCT_QUANTITY = "quantity";
    private static final String PRODUCT_PRICE = "price";
    private static final String PRODUCT_ID = "id";

    private static final String ORDER_ID = "id";
    private static final String ORDER_PROCESSED_TIMESTAMP = "processed_at";

    @Override
    public List<Order> ParseOrders(JSONObject obj) {
        JSONArray ordersJson = (JSONArray) obj.get(ORDERS);
        List<Order> orders = ParseOrders(ordersJson);

        return orders;
    }

    private List<Order> ParseOrders(JSONArray ordersJson) {
        ArrayList<Order> orders = new ArrayList<Order>();

        for (int i = 0; i < ordersJson.size(); i++) {
            JSONObject orderJson = (JSONObject) ordersJson.get(i);
            Order order = ParseOrder(orderJson);
            orders.add(order);
        }

        return orders;
    }

    private Order ParseOrder(JSONObject orderJson) {
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
     * returns the user who made the order.
     *
     * can cache users if creation is redundant/ expensive.
     */
    private User ParseUser(JSONObject userJson) {
        User user = createUser(userJson);
        return user;
    }

    private User createUser(JSONObject userJson) {
        String firstName = (String) userJson.get(USER_FIRST_NAME);
        String lastName = (String) userJson.get(USER_LAST_NAME);
        String email = (String) userJson.get(USER_EMAIL);
        String phoneNumber = (String) userJson.get(USER_PHONE_NUMBER);

        return new User(firstName, lastName, email, phoneNumber);
    }

    /*
     * parse Products model
     *
     * returns a set of all products in an order.
     * the Pair is a map of product to the quantity ordered.
     */
    private Set<OrderProductMap> ParseProducts(JSONArray productsJson) {
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
     *
     * Pair return type maps the product to quantity ordered
     */
    private OrderProductMap ParseProduct(JSONObject productJson) {
        Product product = createProduct(productJson);
        Long quantity = (Long) productJson.get(PRODUCT_QUANTITY);

        OrderProductMap opm = new OrderProductMap();
        opm.setProduct(product);
        opm.setQuantity(quantity);

        product.addOrderProduct(opm);

        return opm;
    }

    /*
     * returns the user who made the order.
     *
     * can cache products if creation is redundant/ expensive.
     */
    private Product createProduct(JSONObject productJson) {
        String name = (String) productJson.get(PRODUCT_NAME);
        Double price = (Double) Double.parseDouble((String) productJson.get(PRODUCT_PRICE));

        return new Product(name, price);
    }
}
