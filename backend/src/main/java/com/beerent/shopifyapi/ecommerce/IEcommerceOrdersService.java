package com.beerent.shopifyapi.ecommerce;

import com.beerent.shopifyapi.model.orders.Order;
import java.util.List;

/*
 * Interface for communicating with some Ecommerce instance.
 *
 * This interface is used to force Specific ecommerce communicators
 * to conform to the same API.
*/
public interface IEcommerceOrdersService {
    /*
     * Retrieve all orders from the ecommerce endpoint
    */
    public List<Order> FetchOrders();
}
