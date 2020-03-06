package com.beerent.shopifyapi;

import com.beerent.shopifyapi.database.DatabaseCommunicator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopifyApiMain {

	public static void main(String[] args) {
		DatabaseCommunicator communicator = new DatabaseCommunicator();
		communicator.AddCustomer();
		//SpringApplication.run(ShopifyApiMain.class, args);
	}

}
