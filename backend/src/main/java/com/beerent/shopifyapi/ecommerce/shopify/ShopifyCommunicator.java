package com.beerent.shopifyapi.ecommerce.shopify;

import com.beerent.shopifyapi.ecommerce.EcommerceCommunicator;
import org.json.simple.parser.JSONParser;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

public class ShopifyCommunicator implements EcommerceCommunicator {
    private static final String FETCH_ENDPOINT = "orders";

    private String apiKey;
    private String password;
    private String store;
    private String version;

    public ShopifyCommunicator(String apiKey, String password, String store, String version) {
        this.apiKey = apiKey;
        this.password = password;
        this.store = store;
        this.version = version;
    }

    @Override
    public JSONObject FetchOrders() {
        String uri = String.format("https://%s/admin/api/%s/%s.json",
        this.store,
        this.version,
        FETCH_ENDPOINT);

        String plainCreds = this.apiKey + ":" + this.password;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);

        JSONObject obj = new JSONObject();
        JSONParser parser = new JSONParser();
        try {
            obj = (JSONObject) parser.parse(result.getBody());
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return obj;
    }
}
