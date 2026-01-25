package com.example.weather_new.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user_info")
@NoArgsConstructor
public class UserInfoEntity {
    @Id
    @SequenceGenerator(name = "user_infoSequence", sequenceName = "user_info_sequence", initialValue = 2, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_infoSequence")
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "Логин не может быть пустым")
    @Size(min = 3, max = 50, message = "Логин должен быть от 3 до 50 символов")
    @Column(unique = true)
    private String login;

    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
    private String firstname;

    @NotBlank(message = "Фамилия не может быть пустым")
    @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
    private String lastname;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 6, max = 100, message = "Пароль должен быть от 6 до 100 символов")
    private String password;

    @OneToMany(mappedBy="user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LocationEntity> locations;

}
