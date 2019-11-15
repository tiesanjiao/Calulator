package com.yan.newcalulator.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;

public class GsonParse implements IParse {

    public static final String TAG = "GsonParse";
    private static JsonParser parser = new JsonParser();
    private Gson gson;

    public GsonParse(Gson gson) {
        this.gson = gson;
    }

    public static JsonParser getParser() {
        return parser;
    }

    @Override
    public String toJson(Object bean) {
        if (bean != null) {
            try {
                return gson.toJson(bean);
            } catch (Exception e) {
            } catch (Error error) {
            }
        }
        return null;
    }

    @Override
    public <T> T fromJson(String json, Class<T> clazz) {
        if (json != null) {
            try {
                return gson.fromJson(json, clazz);
            } catch (Exception e) {
            }
        }
        return null;
    }

    @Override
    public <T> T fromJson(String json, Type type) {
        if (json != null) {
            try {
                return gson.fromJson(json, type);
            } catch (Exception e) {
            } catch (Error error) {
            }
        }
        return null;
    }

    public <T> T fromJson(JsonElement jsonElement, Type type) {
        if (jsonElement != null) {
            try {
                return gson.fromJson(jsonElement, type);
            } catch (Exception e) {
            }
        }
        return null;
    }
}