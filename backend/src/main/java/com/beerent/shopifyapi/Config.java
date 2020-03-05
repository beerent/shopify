package com.beerent.shopifyapi;

import com.beerent.shopifyapi.ecommerce.EcommerceCommunicator;

import com.beerent.shopifyapi.ecommerce.shopify.ShopifyCommunicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.beerent.shopifyapi")
public class Config {

    @Bean
    public EcommerceCommunicator eCommerceCommunicator() {
        return new ShopifyCommunicator();
    }
}
