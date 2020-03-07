package com.beerent.shopifyapi.ecommerce;

import com.beerent.shopifyapi.model.containers.Orders;

/*
 * Interface for communicating with some Ecommerce instance.
 *
 * This interface is used to force Specific ecommerce communicators
 * to conform to the same API.
*/
public interface EcommerceCommunicator {

    /*
     * Retrieve all orders from the ecommerce endpoint
    */
    public Orders FetchOrders();
}
