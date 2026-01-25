package com.example.weather_new.controllers;


import com.example.weather_new.service.UserAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class AuthController {

    private final UserAuthService userAuthService;


    @PostMapping("/registration")
    public String registrationUser(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String login, @RequestParam String password){
        String resultReg = userAuthService.registrationUser(firstName, lastName, login, password);
        if ("auth".equals(resultReg)) {
            return "redirect:/autentificate";
        }

        return "reg-exception";
    }

    @PostMapping("/auth")
    public String authUser(@RequestParam String login, @RequestParam String password, HttpServletResponse response){

        String resultAuth = userAuthService.authUser(login, password,response);
        if ("choose".equals(resultAuth)) {
            return "redirect:/choose";
        }
        return "reg-exception";
    }

}
