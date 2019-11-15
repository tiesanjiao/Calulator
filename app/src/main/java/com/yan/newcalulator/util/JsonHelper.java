package com.yan.newcalulator.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * author：yuxinfeng on 2019-11-15 14:27
 * email：yuxinfeng@corp.netease.com
 */
public class JsonHelper {

    public static final String TAG = "JsonHelper";

    private static IParse parse;
    private static ObjectMapper mapper;

    // Tips: 2018/10/29 加入Jackson 看下效果
    public static ObjectMapper getJackson() {
        if (mapper == null) {
            mapper = new ObjectMapper();
        }
        return mapper;
    }

    private static IParse getParse() {
        if (parse == null) {
            //单例Gson 实现 ，尽量较少 Gson 的 初始化操作  防止 OOM
            parse = new GsonParse(new Gson());
        }
        return parse;
    }

    public static String toJSONString(@NonNull Object bean) {
        return getParse().toJson(bean);
    }

    public static <T> T fromJson(@NonNull JSONObject jsonObject, @NonNull Type type) {
        String typeString = "";
        if (type instanceof Class) {
            typeString = ((Class) type).getSimpleName();
        }
        if (isString(typeString)) {
            try {
                return (T) jsonObject.toString();
            } catch (Exception e) {
                return null;
            }
        }
        return getParse().fromJson(jsonObject.toString(), type);
    }

    @Nullable
    public static <T> T fromJson(@NonNull String json, @NonNull Type type) {
        String typeString = "";
        if (type instanceof Class) {
            typeString = ((Class) type).getSimpleName();
        }
        if (isString(typeString)) {
            try {
                return (T) json;
            } catch (Exception e) {
                return null;
            }
        }
        return getParse().fromJson(json, type);
    }

    @Deprecated
    /**
     * Use JSONObject or Jackson or FastJson instead of traditional JsonObject/JsonElement
     */
    public static <T> T fromJson(@NonNull JsonElement jsonElement, @NonNull Type type) {
        String typeString = "";
        if (type instanceof Class) {
            typeString = ((Class) type).getSimpleName();
        }
        if (isString(typeString)) {
            try {
                return (T) jsonElement.getAsString();
            } catch (Exception e) {
                Log.e(TAG, "fromJson: "+e);
                return null;
            }
        }
        return ((GsonParse) getParse()).fromJson(jsonElement, type);
    }

    private static boolean isString(String typeString) {
        return typeString.startsWith("String");
    }

    public static int getInt(String json, String key, int defaultValue) {
        JsonObject jsonObject = getJsonObject(json);
        int value = defaultValue;
        if (jsonObject != null && jsonObject.has(key)) {
            JsonElement jsonElement = jsonObject.get(key);
            if (jsonElement != null && jsonElement.isJsonObject()) {
                try {
                    value = jsonElement.getAsInt();
                } catch (Throwable throwable) {
                    Log.e(TAG,"getInt" + throwable);
                }
            }
        }
        return value;
    }

    public static JsonObject getJsonObject(String json) {
        JsonObject jsonObject = null;
        if (!TextUtils.isEmpty(json)) {
            try {
                //进行json转实体，判断是否转换成功
                JsonElement jsonElement = GsonParse.getParser().parse(json);
                if (jsonElement.isJsonObject()) {
                    jsonObject = jsonElement.getAsJsonObject();
                }
            } catch (Throwable throwable) {
                Log.e(TAG,"getJsonObject" + throwable);
            }
        }
        return jsonObject;
    }
}
