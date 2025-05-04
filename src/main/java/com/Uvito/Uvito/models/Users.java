package com.Uvito.Uvito.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username; // Имя пользователя (уникальное)

    @Column(nullable = false)
    private String password; // Пароль

    @Column(nullable = false)
    private String email; // Электронная почта

    // Конструктор с тремя параметрами
    public Users(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
