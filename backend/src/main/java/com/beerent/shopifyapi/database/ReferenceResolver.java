package com.beerent.shopifyapi.database;

import com.beerent.shopifyapi.database.orders.OrderService;
import com.beerent.shopifyapi.database.products.ProductService;
import com.beerent.shopifyapi.database.users.UserService;
import com.beerent.shopifyapi.model.orders.Order;
import com.beerent.shopifyapi.model.orders.OrderProductMap;
import com.beerent.shopifyapi.model.products.Product;
import com.beerent.shopifyapi.model.users.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReferenceResolver {

    Map<String, Product> productCache;
    Map<String, User> userCache;

    public void resolveReferences(List<Order> orders) {
        productCache = new HashMap<String, Product>();
        userCache = new HashMap<String, User>();

        OrderService orderService = new OrderService();

        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            if (orderService.findByExternalId(order.getExternalId())  == null) {
                resolveReferences(order);
            }
        }
    }

    private void resolveReferences(Order order) {
        resolveExistingUserReference(order);
        resolveExistingProductsReferences(order);
    }

    private void resolveExistingUserReference(Order order) {
        User existingUser = this.userCache.get(order.getUser().getExternalId());
        if (existingUser == null) {
            UserService userService = new UserService();
            existingUser = userService.findByExternalId(order.getUser().getExternalId());
        }

        if (existingUser != null) {
            this.userCache.put(existingUser.getExternalId(), existingUser);
            order.setUser(existingUser);
        }
    }

    private void resolveExistingProductsReferences(Order order) {
        Set<OrderProductMap> products = order.getProducts();
        for (OrderProductMap product : products) {
            resolveExistingProduct(product);
        }
    }

    private void resolveExistingProduct(OrderProductMap product) {

        Product existingProduct = this.productCache.get(product.getProduct().getExternalId());
        if (existingProduct == null) {
            ProductService productService = new ProductService();
            existingProduct = productService.findByExternalId(product.getProduct().getExternalId());
        }

        if (existingProduct != null) {
            this.productCache.put(existingProduct.getExternalId(), existingProduct);
            product.setProduct(existingProduct);
        }
    }
}
