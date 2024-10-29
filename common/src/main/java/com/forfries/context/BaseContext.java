package com.forfries.context;

import com.auth0.jwt.interfaces.Claim;

import java.util.Map;

public class BaseContext {

    public static ThreadLocal<Map<String, Claim>> threadLocal = new ThreadLocal<>();

    public static void setCurrentClaims(Map<String, Claim> claims) {
        threadLocal.set(claims);
    }

    public static Map<String, Claim> getCurrentClaims() {
        return threadLocal.get();
    }

    public static void removeCurrentClaims() {
        threadLocal.remove();
    }

}
