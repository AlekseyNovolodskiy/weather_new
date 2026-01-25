package com.example.weather_new.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;

public interface LocationService {
    String addLocationByGeoData(String latitude, String longitude, String locationName,String cityName, Model model, HttpServletRequest request);


    String addLocationByCityName(String locationName, String cityName, Model model, HttpServletRequest request);
}

