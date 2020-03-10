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
            Order existingOrder = orderService.findByExternalId(order.getExternalId());
            if (existingOrder != null) {
                cacheExistingUser(existingOrder.getUser());
                cacheEdistingProduct(existingOrder.getProducts());
            } else {
                resolveExistingUser(order);
                resolveExistingProducts(order);
            }
        }
    }

    private void resolveExistingUser(Order order) {
        User existingUser = null;
        if (this.userCache.containsKey(order.getUser().getExternalId())) {
            existingUser = this.userCache.get(order.getUser().getExternalId());
        } else {
            UserService userService = new UserService();
            existingUser = userService.findByExternalId(order.getUser().getExternalId());
            if (existingUser != null) {
                this.userCache.put(existingUser.getExternalId(), existingUser);
            }
        }

        if (existingUser != null) {
            order.setUser(existingUser);
        }
    }

    private void resolveExistingProducts(Order order) {
        ProductService productService = new ProductService();
        Set<OrderProductMap> products = order.getProducts();
        for (OrderProductMap product : products) {

            Product existingProduct = null;
            if (this.productCache.containsKey(product.getProduct().getExternalId())) {
                existingProduct = this.productCache.get(product.getProduct().getExternalId());
            } else {
                existingProduct = productService.findByExternalId(product.getProduct().getExternalId());
                if (existingProduct != null) {
                    this.productCache.put(existingProduct.getExternalId(), existingProduct);
                }
            }

            if (existingProduct != null) {
                product.setProduct(existingProduct);
            } else {
                this.productCache.put(product.getProduct().getExternalId(), product.getProduct());
            }
        }
    }

    private void cacheExistingUser(User user) {
        this.userCache.put(user.getExternalId(), user);
    }

    private void cacheEdistingProduct(Set<OrderProductMap> products) {
         for (OrderProductMap product : products) {
             this.productCache.put(product.getProduct().getExternalId(), product.getProduct());
         }
    }
}
