package com.beerent.shopifyapi;

import com.beerent.shopifyapi.ecommerce.EcommerceCommunicator;
import com.beerent.shopifyapi.ecommerce.shopify.ShopifyCommunicator;
import com.beerent.shopifyapi.endpoints.FetchOrdersEndpoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ShopifyApiMain {
	public static void main(String[] args) {
		EcommerceCommunicator communicator = new ShopifyCommunicator();

		ConfigurableApplicationContext context = SpringApplication.run(ShopifyApiMain.class, args);

		FetchOrdersEndpoint fetchEndpoint = context.getBean(FetchOrdersEndpoint.class);
		//fetchEndpoint.SetECommerceCommunicator(communicator);
	}
}
