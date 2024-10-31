package com.forfries.context;

import com.auth0.jwt.interfaces.Claim;

import java.util.Map;

public class BaseContext {

    public static ThreadLocal<Map<String, String>> threadLocal = new ThreadLocal<>();

    public static void setCurrentPayload(Map<String, String> map) {
        threadLocal.set(map);
    }

    public static Map<String, String> getCurrentPayload() {
        return threadLocal.get();
    }

    public static void removeCurrentPayload() {
        threadLocal.remove();
    }

}
