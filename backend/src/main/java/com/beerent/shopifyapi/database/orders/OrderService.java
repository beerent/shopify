package com.beerent.shopifyapi.database.orders;

import com.beerent.shopifyapi.database.products.ProductDao;
import com.beerent.shopifyapi.database.products.ProductService;
import com.beerent.shopifyapi.database.users.UserDao;
import com.beerent.shopifyapi.database.users.UserService;
import com.beerent.shopifyapi.model.orders.Order;
import com.beerent.shopifyapi.model.orders.OrderProductMap;
import com.beerent.shopifyapi.model.products.Product;
import com.beerent.shopifyapi.model.users.User;

import java.util.List;
import java.util.Set;

public class OrderService {

    private static OrderDao orderDao;

    public OrderService() {
        orderDao = new OrderDao();
    }

    public void persist(List<Order> orders) {
        orderDao.openCurrentSessionwithTransaction();

        for (Order order : orders) {
            if (orderDao.findByExternalId(order.getExternalId())  != null) {
                continue;
            }
            orderDao.persist(order);
        }

        orderDao.closeCurrentSessionwithTransaction();
    }

    public void persist(Order order) {
        orderDao.openCurrentSessionwithTransaction();
        orderDao.persist(order);
        orderDao.closeCurrentSessionwithTransaction();
    }

    public void update(Order order) {
        orderDao.openCurrentSessionwithTransaction();
        orderDao.update(order);
        orderDao.closeCurrentSessionwithTransaction();
    }

    public Order findById(String id) {
        orderDao.openCurrentSession();
        Order order = orderDao.findById(id);
        orderDao.closeCurrentSession();

        return order;
    }

    public Order findByExternalId(String id) {
        orderDao.openCurrentSession();
        Order order = orderDao.findByExternalId(id);
        orderDao.closeCurrentSession();

        return order;
    }

    public List<Order> findAll() {
        orderDao.openCurrentSession();
        List<Order> orders = orderDao.findAll();
        orderDao.closeCurrentSession();

        return orders;
    }

    public void delete(String id) {
        orderDao.openCurrentSessionwithTransaction();
        Order order = orderDao.findById(id);
        orderDao.delete(order);
        orderDao.closeCurrentSessionwithTransaction();
    }

    public void deleteAll() {
        orderDao.openCurrentSessionwithTransaction();
        orderDao.deleteAll();
        orderDao.closeCurrentSessionwithTransaction();
    }
}
