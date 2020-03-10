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

/*
 * This class updates all order's children to point to
 * existing database references if they already exists.
 *
 * This process is used to prevent duplicate insert attempts
 * and NonUniqueObjectExceptions.
 */
public class OrderDatabaseReferenceResolver {

    OrderService orderService;
    UserService userService;
    ProductService productService;

    Map<String, Product> productCache;
    Map<String, User> userCache;

    public OrderDatabaseReferenceResolver() {
        this.orderService = new OrderService();
        this.userService = new UserService();
        this.productService = new ProductService();

        this.userCache = new HashMap<String, User>();
        this.productCache = new HashMap<String, Product>();
    }

    public void resolveDatabaseReferences(List<Order> orders) {
        //optimization if we know there are no references to be made.
        if(DatabaseContainsOrders() == false) {
            return;
        }

        clearCaches();

        for (Order order : orders) {
            if (this.orderService.findByExternalId(order.getExternalId())  == null) {
                resolveDatabaseReferences(order);
            }
        }
    }

    private void resolveDatabaseReferences(Order order) {
        resolveUserDatabaseReference(order);
        resolveProductsDatabaseReference(order);
    }

    private void resolveUserDatabaseReference(Order order) {
        User existingUser = this.userCache.get(order.getUser().getExternalId());
        if (existingUser == null) {
            existingUser = this.userService.findByExternalId(order.getUser().getExternalId());
        }

        if (existingUser != null) {
            this.userCache.put(existingUser.getExternalId(), existingUser);
            order.setUser(existingUser);
        }
    }

    private void resolveProductsDatabaseReference(Order order) {
        for (OrderProductMap product : order.getProducts()) {
            resolveProductDatabaseReference(product);
        }
    }

    private void resolveProductDatabaseReference(OrderProductMap product) {
        Product existingProduct = this.productCache.get(product.getProduct().getExternalId());
        if (existingProduct == null) {
            existingProduct = this.productService.findByExternalId(product.getProduct().getExternalId());
        }

        if (existingProduct != null) {
            this.productCache.put(existingProduct.getExternalId(), existingProduct);
            product.setProduct(existingProduct);
        }
    }

    private boolean DatabaseContainsOrders() {
        return this.orderService.findAll().size() > 0;
    }

    private void clearCaches() {
        this.userCache.clear();
        this.productCache.clear();
    }
}
