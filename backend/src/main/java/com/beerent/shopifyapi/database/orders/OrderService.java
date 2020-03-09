package com.beerent.shopifyapi.database.orders;

import com.beerent.shopifyapi.model.orders.Order;

import java.util.List;

public class OrderService {

    private static OrderDao orderDao;

    public OrderService() {
        orderDao = new OrderDao();
    }

    public void persist(List<Order> orders) {
        orderDao.openCurrentSessionwithTransaction();

        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            Order existingModel = orderDao.findByExternalOrderId(order.getExternalOrderId());
            if  (existingModel != null) {
                orders.set(i, existingModel);
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
