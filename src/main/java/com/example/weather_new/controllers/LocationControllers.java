package com.example.weather_new.controllers;//package com.weather.web.project.weather.controllers;

import com.example.weather_new.service.LocationService;
import com.example.weather_new.service.implement.LocationServiceDisplay;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationControllers {

    private final LocationService locationService;
    private final LocationServiceDisplay locationServiceDisplay;

    @PostMapping("/add")
    public String addLocation(String latitude, String longitude, String locationName, String cityName, Model model, HttpServletRequest request) {
        if (isNull(latitude) || isNull(longitude)) {
            locationService.addLocationByCityName(locationName, cityName, model, request);
            return "city-name";
        } else {
            locationService.addLocationByGeoData(latitude, longitude, locationName, cityName, model, request);
        }
        return "geo-data";
    }


    @GetMapping("/weather")
    public String weatherDisplay(Model model, HttpServletRequest request) {
        return locationServiceDisplay.weatherDisplay(model, request);
    }
}

