package com.beerent.shopifyapi.ecommerce.fake;

import com.beerent.shopifyapi.ecommerce.EcommerceCommunicator;
import com.beerent.shopifyapi.model.orders.Orders;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/*
 * This implementation of an EcommerceCommunicator is used to
 * communicate specifically to an ecommerce fake.
 */
public class FakeCommunicator implements EcommerceCommunicator {
    FakeOrderParser orderParser;

    public FakeCommunicator() {
        this.orderParser = new FakeOrderParser();
    }
    /*
     * Fetches all customer orders from ecommerce fake.
     *
     * see EcommerceCommunicator::FetchOrders()
     */
    @Override
    public Orders FetchOrders() {
        String jsonString = LoadJsonFromDisk();
        JSONObject jsonObject = StringToJson(jsonString);

        return this.orderParser.ParseOrders(jsonObject);
    }

    /*
     * Returns the fake json data on disk as a String
    */
    private String LoadJsonFromDisk() {
        String filePath = System.getProperty("user.dir") + "/src/main/resources/static/fake_orders.json";
        List<String> lines = GetFileContents(filePath);
        String ordersString = LinesToString(lines);

        return ordersString;
    }

    private List<String> GetFileContents(String filePath) {
        List<String> lines = new ArrayList<String>();
        try {
            lines = Files.readAllLines(new File(filePath).toPath());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return lines;
    }

    private String LinesToString(List<String> lines) {
        String ordersString = "";
        for (String line : lines) {
            ordersString += line + "\n";
        }

        return ordersString;
    }

    private JSONObject StringToJson(String s) {
        JSONObject obj = new JSONObject();
        try {
            obj = (JSONObject) new JSONParser().parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return obj;
    }
}
