package com.example.weather_new.repository;


import com.example.weather_new.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserInfoEntity,Long> {
    UserInfoEntity findByFirstnameAndLastname(String firstName,String lastName);
    UserInfoEntity findByLogin(String login);
    UserInfoEntity findByLoginAndPassword(String login,String password);
}
