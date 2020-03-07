package com.beerent.shopifyapi.database.orders;

import com.beerent.shopifyapi.model.orders.OrderModel;

import java.util.List;

public class OrderService {

    private static OrderDao orderDao;

    public OrderService() {
        orderDao = new OrderDao();
    }

    public void persist(List<OrderModel> orders) {
        orderDao.openCurrentSessionwithTransaction();

        for (int i = 0; i < orders.size(); i++) {
            OrderModel order = orders.get(i);
            OrderModel existingModel = orderDao.findByExternalOrderId(order.getExternalOrderId());
            if  (existingModel != null) {
                orders.set(i, existingModel);
                continue;
            }
            orderDao.persist(order);
        }

        orderDao.closeCurrentSessionwithTransaction();
    }

    public void persist(OrderModel order) {
        orderDao.openCurrentSessionwithTransaction();
        orderDao.persist(order);
        orderDao.closeCurrentSessionwithTransaction();
    }

    public void update(OrderModel order) {
        orderDao.openCurrentSessionwithTransaction();
        orderDao.update(order);
        orderDao.closeCurrentSessionwithTransaction();
    }

    public OrderModel findById(String id) {
        orderDao.openCurrentSession();
        OrderModel order = orderDao.findById(id);
        orderDao.closeCurrentSession();

        return order;
    }

    public List<OrderModel> findAll() {
        orderDao.openCurrentSession();
        List<OrderModel> orders = orderDao.findAll();
        orderDao.closeCurrentSession();

        return orders;
    }

    public void delete(String id) {
        orderDao.openCurrentSessionwithTransaction();
        OrderModel order = orderDao.findById(id);
        orderDao.delete(order);
        orderDao.closeCurrentSessionwithTransaction();
    }

    public void deleteAll() {
        orderDao.openCurrentSessionwithTransaction();
        orderDao.deleteAll();
        orderDao.closeCurrentSessionwithTransaction();
    }
}
