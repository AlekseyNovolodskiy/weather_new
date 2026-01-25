package com.example.weather_new.service.implement;


import com.example.weather_new.entity.LocationEntity;
import com.example.weather_new.entity.UserInfoEntity;
import com.example.weather_new.repository.LocationRepository;
import com.example.weather_new.repository.UserRepository;
import com.example.weather_new.service.LocationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    @Override
    public String addLocationByGeoData(String latitude, String longitude, String locationName, String cityName, Model model, HttpServletRequest request) {


        String username = (String) request.getAttribute("currentUser");
        if (username == null) {
            throw new RuntimeException("Пользователь не авторизован");
        }

        UserInfoEntity byLogin = userRepository.findByLogin(username);


        List<LocationEntity> byUser = locationRepository.findByUser(byLogin);
        if (byUser.size() >= 5) {
            return "weather-display";
        }
        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setLocationName(locationName);
        locationEntity.setCityName(cityName);
        locationEntity.setLatitude(latitude);
        locationEntity.setLongitude(longitude);
        locationEntity.setUser(byLogin);
        locationRepository.save(locationEntity);

        String firstLocationName = "";
        String secondLocationName = "";
        String thirdLocationName = "";
        String fourthLocationName = "";


        for (int i = 0; i < byUser.size(); i++) {
            LocationEntity locationEntityI = byUser.get(i);
            if (i == 0) {
                firstLocationName = locationEntityI.getLocationName();
            }
            if (i == 1) {
                secondLocationName = locationEntityI.getLocationName();
            }
            if (i == 2) {
                thirdLocationName = locationEntityI.getLocationName();
            }
            if (i == 3) {
                fourthLocationName = locationEntityI.getLocationName();
            }

        }

        model.addAttribute("locationCount", byUser.size() + 1);
        model.addAttribute("LocationName", locationName);
        model.addAttribute("firstLocationName", firstLocationName);
        model.addAttribute("secondLocationName", secondLocationName);
        model.addAttribute("thirdLocationName", thirdLocationName);
        model.addAttribute("fourthLocationName", fourthLocationName);

        return "geo-data";

    }

    @Override
    public String addLocationByCityName(String locationName, String cityName, Model model, HttpServletRequest request) {
        String username = (String) request.getAttribute("currentUser");
        if (username == null) {
            throw new RuntimeException("Пользователь не авторизован");
        }

        UserInfoEntity byLogin = userRepository.findByLogin(username);


        List<LocationEntity> byUser = locationRepository.findByUser(byLogin);
        if (byUser.size() >= 5) {
            return "weather-display";
        }
        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setLocationName(locationName);
        locationEntity.setCityName(cityName);
        locationEntity.setLatitude(null);
        locationEntity.setLongitude(null);
        locationEntity.setUser(byLogin);
        locationRepository.save(locationEntity);
        return "city-name";
    }

}
