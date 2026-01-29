package com.example.weather_new.service.implement;

import com.example.weather_new.entity.LocationEntity;
import com.example.weather_new.entity.UserInfoEntity;
import com.example.weather_new.repository.LocationRepository;
import com.example.weather_new.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceDisplay {

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final WeatherRequestService weatherRequestService;

    public String weatherDisplay(Model model, HttpServletRequest request) {
        String username = (String) request.getAttribute("currentUser");
        if (username == null) {
            return "reg-exception";
        }
        UserInfoEntity byLogin = userRepository.findByLogin(username);
        List<LocationEntity> byUser = locationRepository.findByUser(byLogin);

        // Инициализация переменных
        String firstLocationName = "";
        String secondLocationName = "";
        String thirdLocationName = "";
        String fourthLocationName = "";
        String fifthLocationName = "";
        Double firstLocationTemp = null;
        Double secondLocationTemp = null;
        Double thirdLocationTemp = null;
        Double fourthLocationTemp = null;
        Double fifthLocationTemp = null;

        for (int i = 0; i < byUser.size(); i++) {
            LocationEntity locationEntityI = byUser.get(i);
            if (i == 0) {
                firstLocationName = locationEntityI.getLocationName();
                String latitude = locationEntityI.getLatitude();
                String longitude = locationEntityI.getLongitude();

                // Проверяем, что координаты не null и не пустые
                if (latitude != null && longitude != null &&
                        !latitude.isEmpty() && !longitude.isEmpty()) {
                    firstLocationTemp = weatherRequestService.requestByGeo(latitude, longitude);
                } else {
                    firstLocationTemp = weatherRequestService.requestMethodByName(locationEntityI.getCityName());
                }
            }
            if (i == 1) {
                secondLocationName = locationEntityI.getLocationName();
                String latitude = locationEntityI.getLatitude();
                String longitude = locationEntityI.getLongitude();

                if (latitude != null && longitude != null &&
                        !latitude.isEmpty() && !longitude.isEmpty()) {
                    secondLocationTemp = weatherRequestService.requestByGeo(latitude, longitude);
                } else {
                    secondLocationTemp = weatherRequestService.requestMethodByName(locationEntityI.getCityName());
                }
            }
            if (i == 2) {
                thirdLocationName = locationEntityI.getLocationName();
                String latitude = locationEntityI.getLatitude();
                String longitude = locationEntityI.getLongitude();

                if (latitude != null && longitude != null &&
                        !latitude.isEmpty() && !longitude.isEmpty()) {
                    thirdLocationTemp = weatherRequestService.requestByGeo(latitude, longitude);
                } else {
                    thirdLocationTemp = weatherRequestService.requestMethodByName(locationEntityI.getCityName());
                }
            }
            if (i == 3) {
                fourthLocationName = locationEntityI.getLocationName();
                String latitude = locationEntityI.getLatitude();
                String longitude = locationEntityI.getLongitude();

                if (latitude != null && longitude != null &&
                        !latitude.isEmpty() && !longitude.isEmpty()) {
                    fourthLocationTemp = weatherRequestService.requestByGeo(latitude, longitude);
                } else {
                    fourthLocationTemp = weatherRequestService.requestMethodByName(locationEntityI.getCityName());
                }
            }
            if (i == 4) {
                fifthLocationName = locationEntityI.getLocationName();
                String latitude = locationEntityI.getLatitude();
                String longitude = locationEntityI.getLongitude();

                if (latitude != null && longitude != null &&
                        !latitude.isEmpty() && !longitude.isEmpty()) {
                    fifthLocationTemp = weatherRequestService.requestByGeo(latitude, longitude);
                } else {
                    fifthLocationTemp = weatherRequestService.requestMethodByName(locationEntityI.getCityName());
                }
            }
        }

        model.addAttribute("firstLocationName", firstLocationName);
        model.addAttribute("secondLocationName", secondLocationName);
        model.addAttribute("thirdLocationName", thirdLocationName);
        model.addAttribute("fourthLocationName", fourthLocationName);
        model.addAttribute("fifthLocationName", fifthLocationName);
        model.addAttribute("firstLocationTemp", firstLocationTemp);
        model.addAttribute("secondLocationTemp", secondLocationTemp);
        model.addAttribute("thirdLocationTemp", thirdLocationTemp);
        model.addAttribute("fourthLocationTemp", fourthLocationTemp);
        model.addAttribute("fifthLocationTemp", fifthLocationTemp);

        return "weather-display";
    }

    private boolean checkDto() {
        return true;
    }

}
