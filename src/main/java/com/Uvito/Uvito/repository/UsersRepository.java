package com.Uvito.Uvito.repository;

import com.Uvito.Uvito.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);  // Метод для поиска по email
    Optional<Users> findByUsername(String username);  // Метод для поиска по username


}
