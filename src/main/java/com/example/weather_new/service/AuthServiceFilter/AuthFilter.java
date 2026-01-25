package com.example.weather_new.service.AuthServiceFilter;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Base64;

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String username = extractUsernameFromCookie(httpRequest);
        if(username != null) {
            request.setAttribute("currentUser", username);
            chain.doFilter(request, response);
        }
    }

    private String extractUsernameFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if("AUTH_TOKEN".equals(cookie.getName())) {
                    return validateAndGetUsername(cookie.getValue());
                }
            }
        }
        return null;
    }

    private String validateAndGetUsername(String token) {
        try {
            String decoded = new String(Base64.getDecoder().decode(token));
            return decoded.split(":")[0];
        } catch(Exception e) {
            return null;
        }
    }
}