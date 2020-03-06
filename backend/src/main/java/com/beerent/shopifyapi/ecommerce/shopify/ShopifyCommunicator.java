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

/*
 * This implementation of an EcommerceCommunicator is used to
 * communicate specifically to Shopify.
*/
public class ShopifyCommunicator implements EcommerceCommunicator {
    private static final String FETCH_ENDPOINT = "orders";

    //required fields for sending Shopify API requests.
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

    /*
     * Fetches all customer orders from Shopify.
     *
     * see EcommerceCommunicator::FetchOrders()
    */
    @Override
    public JSONObject FetchOrders() {
        String uri = GetApiUri();
        HttpEntity<String> request = GetHTTPEntity();
        ResponseEntity<String> response = ExecuteApiExchange(uri, HttpMethod.GET, request, String.class);

        return ResponseToJson(response);
    }

    private String GetApiUri() {
        return String.format("https://%s/admin/api/%s/%s.json",
                this.store,
                this.version,
                FETCH_ENDPOINT);
    }

    private HttpEntity<String> GetHTTPEntity() {
        String base64Creds = GetAuthorizationString();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);

        return new HttpEntity<String>(headers);
    }

    private String GetAuthorizationString() {
        String plainCreds = this.apiKey + ":" + this.password;

        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        return base64Creds;
    }

    private ResponseEntity<String> ExecuteApiExchange(String uri, HttpMethod method, HttpEntity<String> request, Class classInstance) {
        return new RestTemplate().exchange(uri, method, request, classInstance);
    }

    JSONObject ResponseToJson(ResponseEntity<String> response) {
        JSONObject obj = new JSONObject();
        JSONParser parser = new JSONParser();
        try {
            obj = (JSONObject) parser.parse(response.getBody());
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return obj;
    }
}
