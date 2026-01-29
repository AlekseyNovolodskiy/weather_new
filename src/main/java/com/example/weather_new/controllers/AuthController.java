package com.example.weather_new.controllers;

import com.example.weather_new.service.UserAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class AuthController {

    private final UserAuthService userAuthService;

    @PostMapping("/registration")
    public String registrationUser(@RequestParam String firstName,
                                   @RequestParam String lastName,
                                   @RequestParam String login,
                                   @RequestParam String password) {

        // Простая валидация в контроллере
        List<String> errors = new ArrayList<>();

        // Валидация имени
        if (firstName == null || firstName.trim().isEmpty()) {
            errors.add("Имя не может быть пустым");
        } else if (firstName.length() < 2 || firstName.length() > 50) {
            errors.add("Имя должно быть от 2 до 50 символов");
        }

        // Валидация фамилии
        if (lastName == null || lastName.trim().isEmpty()) {
            errors.add("Фамилия не может быть пустой");
        } else if (lastName.length() < 2 || lastName.length() > 50) {
            errors.add("Фамилия должна быть от 2 до 50 символов");
        }

        // Валидация логина
        if (login == null || login.trim().isEmpty()) {
            errors.add("Логин не может быть пустым");
        } else if (login.length() < 3 || login.length() > 50) {
            errors.add("Логин должен быть от 3 до 50 символов");
        }

        // Валидация пароля
        if (password == null || password.trim().isEmpty()) {
            errors.add("Пароль не может быть пустым");
        } else if (password.length() < 6 || password.length() > 100) {
            errors.add("Пароль должен быть от 6 до 100 символов");
        }

        // Если есть ошибки валидации - перенаправляем на reg-exception
        if (!errors.isEmpty()) {
            log.warn("Ошибки валидации при регистрации: {}", errors);
            return "reg-exception";
        }

        try {
            String resultReg = userAuthService.registrationUser(firstName, lastName, login, password);
            if ("auth".equals(resultReg)) {
                return "redirect:/autentificate";
            } else {
                return "reg-exception";
            }
        } catch (Exception e) {
            log.error("Ошибка при регистрации", e);
            return "reg-exception";
        }
    }

    @PostMapping("/auth")
    public String authUser(@RequestParam String login,
                           @RequestParam String password,
                           HttpServletResponse response) {

        // Простая валидация логина и пароля
        if (login == null || login.trim().isEmpty()) {
            log.warn("Пустой логин при авторизации");
            return "reg-exception";
        }

        if (password == null || password.trim().isEmpty()) {
            log.warn("Пустой пароль при авторизации");
            return "reg-exception";
        }

        try {
            String resultAuth = userAuthService.authUser(login, password, response);
            if ("choose".equals(resultAuth)) {
                return "redirect:/choose";
            } else {
                return "reg-exception";
            }
        } catch (Exception e) {
            log.error("Ошибка при авторизации", e);
            return "reg-exception";
        }
    }
}