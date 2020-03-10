package com.beerent.shopifyapi;

import com.beerent.shopifyapi.database.orders.OrderService;
import com.beerent.shopifyapi.ecommerce.IEcommerceOrdersProvider;
import com.beerent.shopifyapi.endpoints.DeleteOrdersEndpoint;
import com.beerent.shopifyapi.endpoints.FetchOrdersEndpoint;
import com.beerent.shopifyapi.endpoints.GetOrdersEndpoint;
import com.beerent.shopifyapi.model.orders.Order;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ShopifyApiMainTests {

	@Test
	void testFetchOrdersReturnCode() {
		IEcommerceOrdersProvider comms = mock(IEcommerceOrdersProvider.class);
		List<Order> orders = new ArrayList<Order>();
		when(comms.FetchOrders()).thenReturn(orders);

		FetchOrdersEndpoint endpoint = new FetchOrdersEndpoint(comms);
		assertEquals(endpoint.Fetch().getStatusCode().value(), 204);
	}

	@Test
	void testDeleteOrdersReturnCode() {
		OrderService orderService = mock(OrderService.class);
		List<Order> orders = new ArrayList<Order>();
		when(orderService.findAll()).thenReturn(orders);

		DeleteOrdersEndpoint getOrdersEndpoint = new DeleteOrdersEndpoint(orderService);
		assertEquals(getOrdersEndpoint.Delete().getStatusCode().value(), 204);
	}

	@Test
	void testGetOrdersReturnCode() {
		OrderService orderService = mock(OrderService.class);
		List<Order> orders = new ArrayList<Order>();
		when(orderService.findAll()).thenReturn(orders);

		GetOrdersEndpoint getOrdersEndpoint = new GetOrdersEndpoint(orderService);
		assertEquals(getOrdersEndpoint.Get().getStatusCode().value(), 200);
	}
}
