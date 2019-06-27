package com.example.logo.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marco on 20/10/2016.
 */

public class TrimMessage {

    public static String trimMessage(String json, String key){
            String trimmedString = null;

            try{
                JSONObject obj = new JSONObject(json);
                trimmedString = obj.getString(key);
            } catch(JSONException e){
                e.printStackTrace();
                return null;
            }

            return trimmedString;
    }
}

