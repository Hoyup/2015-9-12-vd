package com.vd.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MyJson {
    public static Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public static <T> T fromJson(String src, java.lang.reflect.Type type) {
        return GSON.fromJson(src, type);
    }

    public static String toJson(Object o) {
        return GSON.toJson(o);
    }
}
