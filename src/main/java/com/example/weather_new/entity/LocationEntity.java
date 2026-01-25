package com.example.weather_new.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "location")
public class LocationEntity {
    @Id
    @SequenceGenerator(name = "locationSequence", sequenceName = "location_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locationSequence")
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "Название локации не может быть пустым")
    @Size(max = 100, message = "Название локации не может превышать 100 символов")
    private String locationName;

    @Size(max = 100, message = "Название города не может превышать 100 символов")
    private String cityName;

    @Pattern(regexp = "^$|^-?\\d{1,2}(\\.\\d{1,6})?$",
            message = "Широта должна быть в формате: -90.0 до 90.0")
    @Size(max = 20, message = "Широта не может превышать 20 символов")
    private String latitude ;

    @Pattern(regexp = "^$|^-?\\d{1,3}(\\.\\d{1,6})?$",
            message = "Долгота должна быть в формате: -180.0 до 180.0")
    @Size(max = 20, message = "Долгота не может превышать 20 символов")
    private String longitude;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfoEntity user;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        LocationEntity that = (LocationEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
