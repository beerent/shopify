package com.beerent.shopifyapi;

import com.beerent.shopifyapi.ecommerce.EcommerceCommunicator;

import com.beerent.shopifyapi.ecommerce.fake.FakeCommunicator;
import com.beerent.shopifyapi.ecommerce.shopify.ShopifyCommunicator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan("com.beerent.shopifyapi")
public class Config {
    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);

    private static final String PROPERTIES_NAME = "application.properties";
    private static final String ECOMMERCE_PROPERTY = "ecommerce";
    private static final String ECOMMERCE_PROPERTY_SHOPIFY = "shopify";

    private Properties properties;

    public Config() {
        this.properties = new java.util.Properties();
        try {
            this.properties.load(this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_NAME));
        }catch (IOException ioException) {
            ioException.printStackTrace();
            System.exit(1);
        }
    }

    @Bean
    public EcommerceCommunicator eCommerceCommunicator() {
        switch(this.properties.getProperty(ECOMMERCE_PROPERTY, "")) {
            case ECOMMERCE_PROPERTY_SHOPIFY:
                LOGGER.info("using [" + ECOMMERCE_PROPERTY_SHOPIFY + "] as ecommerce communicator");
                return new ShopifyCommunicator();
            default:
                LOGGER.info("using [default] as ecommerce communicator");
                return new FakeCommunicator();
        }
    }
}
