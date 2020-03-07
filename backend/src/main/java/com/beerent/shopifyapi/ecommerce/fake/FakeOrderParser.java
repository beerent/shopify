package com.beerent.shopifyapi.ecommerce.fake;

import com.beerent.shopifyapi.model.containers.Order;
import com.beerent.shopifyapi.model.containers.Orders;
import com.beerent.shopifyapi.model.orders.OrderModel;
import com.beerent.shopifyapi.model.orders.OrderProductMapModel;
import com.beerent.shopifyapi.model.products.ProductModel;
import com.beerent.shopifyapi.model.containers.Products;
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

    public List<OrderModel> ParseOrders(JSONObject obj) {
        JSONArray ordersJson = (JSONArray) obj.get(ORDERS);
        return ParseOrders(ordersJson);
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
        JSONObject userJson = (JSONObject) orderJson.get(USER);
        UserModel user = ParseUser(userJson);

        JSONArray productsJson = (JSONArray) orderJson.get(PRODUCTS);
        Set<OrderProductMapModel> productMap = new HashSet<OrderProductMapModel>();
        OrderModel orderModel = new OrderModel();

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

        //orderModel.setUser(user);
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
        Double price = (Double) productJson.get(PRODUCT_PRICE);

        return new ProductModel(name, price);
    }
}
