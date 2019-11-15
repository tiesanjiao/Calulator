package com.yan.newcalulator.util;

import java.lang.reflect.Type;

public interface IParse {

    String toJson(Object bean);

    <T> T fromJson(String json, Class<T> clazz);

    <T> T fromJson(String json, Type type);
}

