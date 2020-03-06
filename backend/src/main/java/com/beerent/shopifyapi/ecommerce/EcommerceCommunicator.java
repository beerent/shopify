package com.beerent.shopifyapi.ecommerce;

import org.json.simple.JSONObject;

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
    public JSONObject FetchOrders();
}
