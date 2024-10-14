package com.nhnacademy.webserver.common.utils.loginUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {
    private final static String SESSION_KEY = "SESSION";

    public static void create(String sessionId, HttpServletResponse res){
        Cookie cookie = new Cookie(SESSION_KEY, sessionId);
        cookie.setMaxAge(259200);
        cookie.setPath("/");
        res.addCookie(cookie);
    }

    public static String get(HttpServletRequest req){
        Cookie[] cookies = req.getCookies();
        if(cookies == null) return null;
        for(Cookie c: cookies){
            if(c.getName().equals(SESSION_KEY)){
                return c.getValue();
            }
        }
        return null;
    }

    public static void remove(HttpServletResponse res){
        Cookie cookie = new Cookie(SESSION_KEY, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        res.addCookie(cookie);
    }
}
