package com.beerent.shopifyapi.ecommerce.fake;

import com.beerent.shopifyapi.ecommerce.EcommerceCommunicator;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FakeCommunicator implements EcommerceCommunicator {

    @Override
    public JSONObject FetchOrders() {
        String jsonString = LoadJsonFromDisk();
        JSONObject jsonObject = StringToJson(jsonString);

        return jsonObject;
    }

    private String LoadJsonFromDisk() {
        String filePath = System.getProperty("user.dir") + "/src/main/resources/static/fake_orders.json";
        String ordersString = "";
        try {
            List<String> lines = Files.readAllLines(new File(filePath).toPath());
            for (String line : lines) {
                ordersString += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return ordersString;
    }

    private JSONObject StringToJson(String s) {
        JSONObject obj = new JSONObject();
        JSONParser parser = new JSONParser();
        try {
            obj = (JSONObject) parser.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return obj;
    }
}
