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
    private static final String SHOPIFY_ID = "shopify";

    private static final String ORDERS = "orders";

    private static final String USER = "customer";
    private static final String USER_FIRST_NAME = "first_name";
    private static final String USER_LAST_NAME = "last_name";
    private static final String USER_EMAIL = "email";
    private static final String USER_PHONE_NUMBER = "phone";

    private static final String PRODUCTS = "line_items";
    private static final String PRODUCT_NAME = "title";
    private static final String PRODUCT_QUANTITY = "quantity";
    private static final String PRODUCT_PRICE = "price";

    private static final String ORDER_ID = "id";
    private static final String ORDER_PROCESSED_TIMESTAMP = "processed_at";

    Map<String, Product> productCache;
    Map<String, User> userCache;

    public ShopifyOrderParser() {
        this.productCache = new HashMap<String, Product>();
        this.userCache = new HashMap<String, User>();
    }

    @Override
    public List<Order> ParseOrders(JSONObject obj) {
        this.productCache.clear();
        this.userCache.clear();

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
        String uniqueId = hashId("" + ecommerceId);
        User user = ParseUser((JSONObject) orderJson.get(USER));
        Date ordered = ParseDate((String)orderJson.get(ORDER_PROCESSED_TIMESTAMP));
        Set<OrderProductMap> products = ParseProducts((JSONArray) orderJson.get(PRODUCTS));
        for (OrderProductMap product : products) {
            product.setOrder(order);
        }

        order.setExternalId(uniqueId);
        order.setUser(user);
        order.setOrdered(ordered);
        order.setProducts(products);

        return order;
    }

    Date ParseDate(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

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
     */
    private User ParseUser(JSONObject userJson) {
        User user = getUser(userJson);
        return user;
    }

    private User getUser(JSONObject userJson) {
        String firstName = (String) userJson.get(USER_FIRST_NAME);
        String lastName = (String) userJson.get(USER_LAST_NAME);
        String email = (String) userJson.get(USER_EMAIL);
        String phoneNumber = (String) userJson.get(USER_PHONE_NUMBER);
        String externalId = hashId(firstName + lastName + email + phoneNumber);

        User user = this.userCache.get(externalId);
        if (user == null) {
            user = new User(externalId, firstName, lastName, email, phoneNumber);
            this.userCache.put(externalId, user);
        }

        return user;
    }

    /*
     * parse Products model
     *
     * returns a set of all products in an order.
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
     * returns an OrderProductMap pairing a product with the quantity purchased.
     */
    private OrderProductMap ParseProduct(JSONObject productJson) {
        Product product = getProduct(productJson);
        Long quantity = (Long) productJson.get(PRODUCT_QUANTITY);

        OrderProductMap opm = new OrderProductMap();
        opm.setProduct(product);
        opm.setQuantity(quantity);

        product.addOrderProduct(opm);

        return opm;
    }

    /*
     * returns a product object associated with the product json data.
     *   note - uses cache to prevent duplicate products.
     */
    private Product getProduct(JSONObject productJson) {
        String name = (String) productJson.get(PRODUCT_NAME);
        Double price = (Double) Double.parseDouble((String) productJson.get(PRODUCT_PRICE));
        String externalId = hashId(name + price);

        Product product = this.productCache.get(externalId);
        if (product == null) {
            product = new Product(externalId, name, price);
            this.productCache.put(externalId, product);
        }

        return product;
    }

    String hashId(String id) {
        return "" + (SHOPIFY_ID + id.hashCode());
    }
}
