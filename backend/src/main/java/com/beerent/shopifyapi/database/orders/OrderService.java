package com.beerent.shopifyapi.database.orders;

import com.beerent.shopifyapi.model.orders.Order;

import java.util.List;

public class OrderService {

    private static OrderDao orderDao;
    private Boolean updateOrders;

    public OrderService() {
        orderDao = new OrderDao();
        this.updateOrders = false;
    }

    public OrderService(Boolean updateOrders) {
        orderDao = new OrderDao();
        this.updateOrders = updateOrders;
    }

    public void persist(List<Order> orders) {
        orderDao.openCurrentSessionwithTransaction();

        for (int i = 0; i < orders.size(); i++) {
             Order order = orders.get(i);
            Order existingModel = orderDao.findByExternalId(order.getExternalId());

            if (existingModel != null) {
                if (this.updateOrders) {
                    existingModel.update(order);
                }

                order = existingModel;
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

    public Order findById(Integer id) {
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

    public void delete(Integer id) {
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
