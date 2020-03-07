package com.beerent.shopifyapi.database.orders;

import com.beerent.shopifyapi.model.orders.OrderProductMapModel;

import java.util.List;

public class OrderProductMapService {

    private static OrderProductMapDao orderProductMapDaoDao;

    public OrderProductMapService() {
        orderProductMapDaoDao = new OrderProductMapDao();
    }

    public void persist(List<OrderProductMapModel> orders) {
        orderProductMapDaoDao.openCurrentSessionwithTransaction();

        for (OrderProductMapModel order : orders) {
            //if  (orderDao.findByName(order.getName()) != null) {
            //    continue;
            //}
            orderProductMapDaoDao.persist(order);
        }

        orderProductMapDaoDao.closeCurrentSessionwithTransaction();
    }

    public void persist(OrderProductMapModel order) {
        orderProductMapDaoDao.openCurrentSessionwithTransaction();
        orderProductMapDaoDao.persist(order);
        orderProductMapDaoDao.closeCurrentSessionwithTransaction();
    }

    public void update(OrderProductMapModel order) {
        orderProductMapDaoDao.openCurrentSessionwithTransaction();
        orderProductMapDaoDao.update(order);
        orderProductMapDaoDao.closeCurrentSessionwithTransaction();
    }

    public OrderProductMapModel findById(String id) {
        orderProductMapDaoDao.openCurrentSession();
        OrderProductMapModel order = orderProductMapDaoDao.findById(id);
        orderProductMapDaoDao.closeCurrentSession();

        return order;
    }

    public List<OrderProductMapModel> findAll() {
        orderProductMapDaoDao.openCurrentSession();
        List<OrderProductMapModel> orders = orderProductMapDaoDao.findAll();
        orderProductMapDaoDao.closeCurrentSession();

        return orders;
    }

    public void delete(String id) {
        orderProductMapDaoDao.openCurrentSessionwithTransaction();
        OrderProductMapModel order = orderProductMapDaoDao.findById(id);
        orderProductMapDaoDao.delete(order);
        orderProductMapDaoDao.closeCurrentSessionwithTransaction();
    }

    public void deleteAll() {
        orderProductMapDaoDao.openCurrentSessionwithTransaction();
        orderProductMapDaoDao.deleteAll();
        orderProductMapDaoDao.closeCurrentSessionwithTransaction();
    }
}
