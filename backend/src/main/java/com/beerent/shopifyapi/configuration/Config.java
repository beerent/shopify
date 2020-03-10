package com.beerent.shopifyapi.configuration;

import com.beerent.shopifyapi.ecommerce.IEcommerceOrderParser;
import com.beerent.shopifyapi.ecommerce.IEcommerceOrdersProvider;

import com.beerent.shopifyapi.ecommerce.fake.FakeOrderParser;
import com.beerent.shopifyapi.ecommerce.fake.FakeOrdersProvider;
import com.beerent.shopifyapi.ecommerce.shopify.ShopifyOrderParser;
import com.beerent.shopifyapi.ecommerce.shopify.ShopifyOrdersProvider;

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
    private static final String ECOMMERCE_PROPERTY_SHOPIFY_API_KEY = "shopify_api_key";
    private static final String ECOMMERCE_PROPERTY_SHOPIFY_API_PASSWORD = "shopify_api_password";
    private static final String ECOMMERCE_PROPERTY_SHOPIFY_STORE = "shopify_store";
    private static final String ECOMMERCE_PROPERTY_SHOPIFY_VERSION = "shopify_version";

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

    ShopifyOrdersProvider GetShopifyCommunicator() {
        String apiKey = this.properties.getProperty(ECOMMERCE_PROPERTY_SHOPIFY_API_KEY);
        String password = this.properties.getProperty(ECOMMERCE_PROPERTY_SHOPIFY_API_PASSWORD);
        String store = this.properties.getProperty(ECOMMERCE_PROPERTY_SHOPIFY_STORE);
        String version = this.properties.getProperty(ECOMMERCE_PROPERTY_SHOPIFY_VERSION);

        if (apiKey == null ) {
            LOGGER.error("missing property: " + ECOMMERCE_PROPERTY_SHOPIFY_API_KEY);
            System.exit(1);
        }

        if (password == null ) {
            LOGGER.error("missing property: " + ECOMMERCE_PROPERTY_SHOPIFY_API_PASSWORD);
            System.exit(1);
        }

        if (store == null ) {
            LOGGER.error("missing property: " + ECOMMERCE_PROPERTY_SHOPIFY_STORE);
            System.exit(1);
        }

        if (version == null ) {
            LOGGER.error("missing property: " + ECOMMERCE_PROPERTY_SHOPIFY_VERSION);
            System.exit(1);
        }

        return new ShopifyOrdersProvider(apiKey, password, store, version);
    }

    @Bean
    public IEcommerceOrdersProvider eCommerceCommunicator() {
        switch(this.properties.getProperty(ECOMMERCE_PROPERTY)) {
            case ECOMMERCE_PROPERTY_SHOPIFY:
                LOGGER.info("using [" + ECOMMERCE_PROPERTY_SHOPIFY + "] as ecommerce communicator");
                return GetShopifyCommunicator();
            default:
                LOGGER.info("using [default] as ecommerce communicator");
                return new FakeOrdersProvider();
        }
    }

    @Bean
    public IEcommerceOrderParser eCommerceOrderParser() {
        switch(this.properties.getProperty(ECOMMERCE_PROPERTY)) {
            case ECOMMERCE_PROPERTY_SHOPIFY:
                LOGGER.info("using [" + ECOMMERCE_PROPERTY_SHOPIFY + "] as ecommerce order parser");
                return new ShopifyOrderParser();
            default:
                LOGGER.info("using [default] as ecommerce order parser");
                return new FakeOrderParser();
        }
    }
}
