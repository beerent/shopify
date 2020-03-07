package com.beerent.shopifyapi;

import com.beerent.shopifyapi.database.users.UserService;
import com.beerent.shopifyapi.model.users.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopifyApiMain {

	public static void main(String[] args) {
		User user = new User("firster", "laster", "emailer", "phoner");
		UserService service = new UserService();
		service.persist(user);
		//SpringApplication.run(ShopifyApiMain.class, args);
	}

}
