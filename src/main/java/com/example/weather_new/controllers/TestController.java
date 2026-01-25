package com.example.weather_new.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController{


    @GetMapping("/health")
    @ResponseBody
    public String health() {
        return "SERVER IS RUNNING - " + System.currentTimeMillis();
    }

    @GetMapping("/ping")
    @ResponseBody
    public String ping() {

        return "PONG";
    }
}