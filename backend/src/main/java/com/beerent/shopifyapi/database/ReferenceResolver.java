package com.beerent.shopifyapi.database;

import com.beerent.shopifyapi.database.orders.OrderService;
import com.beerent.shopifyapi.database.products.ProductService;
import com.beerent.shopifyapi.database.users.UserService;
import com.beerent.shopifyapi.model.orders.Order;
import com.beerent.shopifyapi.model.orders.OrderProductMap;
import com.beerent.shopifyapi.model.products.Product;
import com.beerent.shopifyapi.model.users.User;

import java.util.List;
import java.util.Set;

public class ReferenceResolver {
    public void resolveReferences(List<Order> orders) {
        OrderService orderService = new OrderService();
        ProductService productService = new ProductService();
        UserService userService =  new UserService();

        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            Order existingOrder = orderService.findByExternalId(order.getExternalId());
            if (existingOrder != null) {
                orders.set(i, existingOrder);
            } else {
                resolveExistingUser(order);
                resolveExistingProducts(order);
            }
        }
    }

    private void resolveExistingUser(Order order) {
        UserService userService = new UserService();
        User existingUser = userService.findByExternalId(order.getUser().getExternalId());
        if (existingUser != null) {
            order.setUser(existingUser);
        }
    }

    private void resolveExistingProducts(Order order) {
        ProductService productService = new ProductService();
        Set<OrderProductMap> products = order.getProducts();
        for (OrderProductMap product : products) {
            Product existingProduct = productService.findByExternalId(product.getProduct().getExternalId());
            if (existingProduct != null) {
                product.setProduct(existingProduct);
            }
        }
    }
}
