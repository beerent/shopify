package com.beerent.shopifyapi.ecommerce;

import com.beerent.shopifyapi.model.orders.Orders;
import org.json.simple.JSONObject;

/*
 * Interface for parsing an ecommerce JSON order object
 *
 * This is used to help ecommerce orders retrevial to conform
 * to a common object.
*/
public interface OrderParser {

    /*
     * Returns a generic Orders object from an ecommerce-specific JSONObject
    */
    public Orders ParseOrders(JSONObject ordersJson);
}