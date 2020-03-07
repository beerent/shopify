package com.beerent.shopifyapi.ecommerce.shopify;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.beerent.shopifyapi.model.containers.Order;
import com.beerent.shopifyapi.model.containers.Orders;
import com.beerent.shopifyapi.model.orders.OrderModel;
import com.beerent.shopifyapi.model.orders.OrderProductMapModel;
import com.beerent.shopifyapi.model.products.ProductModel;
import com.beerent.shopifyapi.model.containers.Products;
import com.beerent.shopifyapi.model.users.UserModel;
import org.aspectj.weaver.ast.Or;
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
    private static final String ORDER_PROCESSED_TIMESTAMP = "processed_at";

    private Map<Long, UserModel> users;


    public ShopifyOrderParser() {
        this.users = new HashMap<Long, UserModel>();
    }

    public List<OrderModel> ParseOrders(JSONObject obj) {
        JSONArray ordersJson = (JSONArray) obj.get(ORDERS);
        List<OrderModel> orders = ParseOrdersNew(ordersJson);
        return orders;
    }

    public List<OrderModel> ParseOrdersNew(JSONArray ordersJson) {
        ArrayList<OrderModel> orders = new ArrayList<OrderModel>();

        for (int i = 0; i < 1; i++) {
            JSONObject orderJson = (JSONObject) ordersJson.get(i);
            OrderModel order = ParseOrderNew(orderJson);
            orders.add(order);
        }

        return orders;
    }

    public OrderModel ParseOrderNew(JSONObject orderJson) {
        OrderModel order = new OrderModel();

        JSONObject userJson = (JSONObject) orderJson.get(USER);
        UserModel user = ParseUserNew(userJson);
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

    public UserModel ParseUserNew(JSONObject userJson) {
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



























    private List<OrderModel> ParseOrders(JSONArray ordersJson) {
        ArrayList<OrderModel> orders = new ArrayList<OrderModel>();

        for (int i = 0; i < ordersJson.size(); i++) {
            JSONObject orderJson = (JSONObject) ordersJson.get(i);
            OrderModel order = ParseOrder(orderJson);
            orders.add(order);
        }

        return orders;
    }

    private OrderModel ParseOrder(JSONObject orderJson) {
        OrderModel orderModel = new OrderModel();

        JSONObject userJson = (JSONObject) orderJson.get(USER);
        UserModel user = ParseUser(userJson);

        //user.addOrder(orderModel);

        JSONArray productsJson = (JSONArray) orderJson.get(PRODUCTS);
        Set<OrderProductMapModel> productMap = new HashSet<OrderProductMapModel>();

        Products products = ParseProducts(productsJson);
        for (ProductModel product : products.GetProducts()) {
            OrderProductMapModel map = new OrderProductMapModel(orderModel, product);
            productMap.add(map);
        }
        //orderModel.setOrderProductMaps(productMap);

        Long ecommerceId = (Long) orderJson.get(ORDER_ID);
        String processedTimestamp = (String) orderJson.get(ORDER_PROCESSED_TIMESTAMP);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = formatter.parse(processedTimestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        orderModel.setUser(user);
        orderModel.setOrdered(date);
        orderModel.setExternalOrderId(ecommerceId);

        return orderModel;
    }

    UserModel ParseUser(JSONObject userJson) {
        Long ecommerceId = (Long) userJson.get(USER_ID);
        String firstName = (String) userJson.get(USER_FIRST_NAME);
        String lastName = (String) userJson.get(USER_LAST_NAME);
        String email = (String) userJson.get(USER_EMAIL);
        String phoneNumber = (String) userJson.get(USER_PHONE_NUMBER);

        return new UserModel(firstName, lastName, email, phoneNumber);
    }

    Products ParseProducts(JSONArray productsJson) {
        ArrayList<ProductModel> products = new ArrayList<ProductModel>();

        for (int i = 0; i < productsJson.size(); i++) {
            JSONObject productJson = (JSONObject) productsJson.get(i);
            ProductModel product = ParseProduct(productJson);
            products.add(product);
        }

        return new Products(products);
    }

    ProductModel ParseProduct(JSONObject productJson) {
        Long ecommerceId = (Long) productJson.get(PRODUCT_ID);
        String name = (String) productJson.get(PRODUCT_NAME);
        Double price = Double.parseDouble((String) productJson.get(PRODUCT_PRICE));

        return new ProductModel(name, price);
    }
}
