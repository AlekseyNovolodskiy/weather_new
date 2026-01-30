package com.example.weather_new.service.implement;

import com.example.weather_new.entity.LocationEntity;
import com.example.weather_new.entity.UserInfoEntity;
import com.example.weather_new.repository.LocationRepository;
import com.example.weather_new.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationServiceDisplay {

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final WeatherRequestService weatherRequestService;

    public String weatherDisplay(Model model, HttpServletRequest request) {
        log.info("=== Начало weatherDisplay ===");

        // 1. Получаем username из атрибута (установленного фильтром)
        String username = (String) request.getAttribute("currentUser");
        log.info("Username из атрибута 'currentUser': {}", username);

        // 2. Если атрибут пустой, проверяем cookies напрямую (для отладки)
        if (username == null) {
            username = extractUsernameFromCookiesDirectly(request);
            log.info("Username из прямого чтения cookies: {}", username);
        }

        // 3. Если все еще null - пользователь не аутентифицирован
        if (username == null) {
            log.error("Пользователь не аутентифицирован! Redirecting to reg-exception");

            // Добавляем отладочную информацию в модель
            model.addAttribute("errorMessage", "Пользователь не аутентифицирован. Пожалуйста, войдите в систему.");
            return "reg-exception";
        }

        // 4. Ищем пользователя в БД
        UserInfoEntity byLogin = userRepository.findByLogin(username);
        if (byLogin == null) {
            log.error("Пользователь {} не найден в БД", username);
            model.addAttribute("errorMessage", "Пользователь не найден в системе.");
            return "reg-exception";
        }
        log.info("Найден пользователь в БД: {}", byLogin.getLogin());

        // 5. Получаем локации пользователя
        List<LocationEntity> byUser = locationRepository.findByUser(byLogin);
        log.info("Найдено {} локаций для пользователя {}", byUser.size(), username);

        // Добавляем количество локаций в модель
        model.addAttribute("locationCount", byUser.size());

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

        // Обрабатываем локации
        for (int i = 0; i < byUser.size() && i < 5; i++) {
            LocationEntity locationEntityI = byUser.get(i);
            log.info("Обработка локации {}: {}", i + 1, locationEntityI.getLocationName());

            if (i == 0) {
                firstLocationName = locationEntityI.getLocationName();
                String latitude = locationEntityI.getLatitude();
                String longitude = locationEntityI.getLongitude();

                // Проверяем, что координаты не null и не пустые
                if (latitude != null && longitude != null &&
                        !latitude.isEmpty() && !longitude.isEmpty()) {
                    log.info("Используем координаты для запроса погоды: {}, {}", latitude, longitude);
                    firstLocationTemp = weatherRequestService.requestByGeo(latitude, longitude);
                } else {
                    log.info("Используем название города для запроса погоды: {}", locationEntityI.getCityName());
                    firstLocationTemp = weatherRequestService.requestMethodByName(locationEntityI.getCityName());
                }
                log.info("Температура для первой локации: {}", firstLocationTemp);
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

        // Добавляем все атрибуты в модель
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

        log.info("=== Конец weatherDisplay ===");
        log.info("Данные для отображения:");
        log.info("Количество локаций: {}", byUser.size());
        log.info("Первая локация: {} - {}", firstLocationName, firstLocationTemp);
        log.info("Вторая локация: {} - {}", secondLocationName, secondLocationTemp);
        log.info("Третья локация: {} - {}", thirdLocationName, thirdLocationTemp);
        log.info("Четвертая локация: {} - {}", fourthLocationName, fourthLocationTemp);
        log.info("Пятая локация: {} - {}", fifthLocationName, fifthLocationTemp);

        return "weather-display";
    }

    /**
     * Метод для прямого чтения username из cookies (для отладки)
     */
    private String extractUsernameFromCookiesDirectly(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                log.info("Найден cookie: {} = {}", cookie.getName(), cookie.getValue());
                if ("AUTH_TOKEN".equals(cookie.getName())) {
                    try {
                        String token = cookie.getValue();
                        String decoded = new String(Base64.getDecoder().decode(token));
                        // Токен в формате "login:timestamp"
                        String[] parts = decoded.split(":");
                        if (parts.length > 0) {
                            String username = parts[0];
                            log.info("Декодирован username из cookie: {}", username);
                            return username;
                        }
                    } catch (Exception e) {
                        log.warn("Ошибка декодирования токена из cookie", e);
                    }
                }
            }
        } else {
            log.info("Cookies не найдены в запросе");
        }
        return null;
    }

    private boolean checkDto() {
        return true;
    }
}