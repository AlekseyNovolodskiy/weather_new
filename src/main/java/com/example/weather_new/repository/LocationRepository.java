package com.example.weather_new.repository;


import com.example.weather_new.entity.LocationEntity;
import com.example.weather_new.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<LocationEntity,Long> {
    List<LocationEntity> findByUser(UserInfoEntity userInfoEntity);
}
