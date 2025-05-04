package com.Uvito.Uvito.service;

import com.Uvito.Uvito.models.Users;
import java.util.List;
import java.util.Optional;

public interface UsersService {
    List<Users> findAllUsers();
    Users saveUsers(Users users);
    Users findByEmail(String email);
    Users updateUsers(Users users);
    void deleteUsers(String email);
    Users register(Users users);  // для регистрации пользователя
    Optional<Users> findByUsername(String username);  // для поиска по имени
}
