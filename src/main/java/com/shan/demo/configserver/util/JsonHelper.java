package com.shan.demo.configserver.util;


import org.json.JSONObject;

public class JsonHelper {

    public  static JSONObject createJsonObject(String jsonString)  {
        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject;
    }
}
