package com.example.weather_new.service.AuthServiceFilter;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j; // Добавьте это

import java.io.IOException;
import java.util.Base64;

@Slf4j // Добавьте аннотацию для логирования
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String username = extractUsernameFromCookie(httpRequest);

        // Добавьте логирование для отладки
        if (username != null) {
            log.debug("AuthFilter: Найден пользователь {}", username);
            request.setAttribute("currentUser", username);
        } else {
            log.debug("AuthFilter: Пользователь не найден в cookies");
        }

        // ВАЖНО: ВСЕГДА вызывайте chain.doFilter()!
        // Даже если пользователь не аутентифицирован
        chain.doFilter(request, response);
    }

    private String extractUsernameFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if("AUTH_TOKEN".equals(cookie.getName())) {
                    log.debug("AuthFilter: Найден AUTH_TOKEN cookie");
                    return validateAndGetUsername(cookie.getValue());
                }
            }
        }
        log.debug("AuthFilter: AUTH_TOKEN cookie не найден");
        return null;
    }

    private String validateAndGetUsername(String token) {
        try {
            String decoded = new String(Base64.getDecoder().decode(token));
            String username = decoded.split(":")[0];
            log.debug("AuthFilter: Декодирован username: {}", username);
            return username;
        } catch(Exception e) {
            log.warn("AuthFilter: Ошибка декодирования токена", e);
            return null;
        }
    }
}