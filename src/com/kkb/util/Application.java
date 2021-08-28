package com.kkb.util;

import java.util.HashMap;

public class Application {
    private static HashMap<String, Object> data = new HashMap<>();

    public static Object put(String key, Object value) {
        return data.put(key, value);
    }

    public static Object remove(String key) {
        return data.remove(key);
    }

    public static Object get(String key) {
        return data.get(key);
    }
}
