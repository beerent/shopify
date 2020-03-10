package com.beerent.shopifyapi.database.orders;

import com.beerent.shopifyapi.model.orders.Order;

import java.util.List;

public class OrderService {

    private static OrderDao orderDao;

    public OrderService() {
        orderDao = new OrderDao();
    }

    //TODO: update existing references with any new data.
    //
    // note: as this stands this will not update records. this just adds new ones
    // and returns a list full of records from the database.
    public void persist(List<Order> orders) {
        orderDao.openCurrentSessionwithTransaction();

        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            Order existingOrder = orderDao.findByExternalId(order.getExternalId());

            if (existingOrder != null) {
                orders.set(i, existingOrder); // update list with reference to order from database
            } else {
                // merge is needed because cascading references have different foreign key references
                orderDao.merge(order);
            }
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
