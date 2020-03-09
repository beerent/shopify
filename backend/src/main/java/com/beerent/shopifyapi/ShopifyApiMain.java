package com.beerent.shopifyapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.beerent.shopifyapi")
public class ShopifyApiMain {

	public static void main(String[] args) {
		SpringApplication.run(ShopifyApiMain.class, args);
	}

}
