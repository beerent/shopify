package com.beerent.shopifyapi.ecommerce.shopify;

import com.beerent.shopifyapi.ecommerce.IEcommerceOrderParser;
import com.beerent.shopifyapi.ecommerce.IEcommerceOrdersService;
import com.beerent.shopifyapi.model.orders.Order;
import org.json.simple.parser.JSONParser;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/*
 * This implementation of an EcommerceCommunicator is used to
 * communicate specifically to Shopify.
*/
public class ShopifyOrdersService implements IEcommerceOrdersService {
    private static final String FETCH_ENDPOINT = "orders";
    private static final String SHOPIFY_GET_ORDERS_UNFORMATTED = "https://%s/admin/api/%s/%s.json";

    @Autowired
    private IEcommerceOrderParser eCommerceOrderParser;

    //required fields for sending Shopify API requests.
    private String apiKey;
    private String password;
    private String store;
    private String version;

    public ShopifyOrdersService(String apiKey, String password, String store, String version) {
        this.apiKey = apiKey;
        this.password = password;
        this.store = store;
        this.version = version;
    }

    /*
     * Fetches all customer orders from Shopify.
     *
     * see IEcommerceOrdersService::FetchOrders()
    */
    @Override
    public List<Order> FetchOrders() {
        ResponseEntity<String> ordersResponse = GetShopifyFetchOrdersResponse();

        //if there's an issue between us and shopify, do not "inconvenience" the user.
        if (!ResponseIsValid(ordersResponse)) {
            return new ArrayList<Order>();
        }

        return ParseOrders(ordersResponse.getBody());
    }

    private boolean ResponseIsValid(ResponseEntity<String> response) {
        return response != null && response.getStatusCode().is2xxSuccessful();
    }

    private ResponseEntity<String> GetShopifyFetchOrdersResponse() {
        String uri = GetApiUri();
        HttpEntity<String> request = GetHTTPEntity();

        ResponseEntity<String> response = null;
        try  {
            response = ExecuteApiExchange(uri, request);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private ResponseEntity<String> ExecuteApiExchange(String uri, HttpEntity<String> request) {
        HttpMethod httpMethod = HttpMethod.GET;
        Class<String> classInstance = String.class;

        return new RestTemplate().exchange(uri, httpMethod, request, classInstance);
    }

    private List<Order> ParseOrders(String ordersString) {
        return this.eCommerceOrderParser.ParseOrders(ResponseToJson(ordersString));
    }

    JSONObject ResponseToJson(String response) {
        JSONObject obj = new JSONObject();
        JSONParser parser = new JSONParser();
        try {
            obj = (JSONObject) parser.parse(response);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return obj;
    }

    private String GetApiUri() {
        return String.format(SHOPIFY_GET_ORDERS_UNFORMATTED,
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

        return new String(base64CredsBytes);
    }
}
