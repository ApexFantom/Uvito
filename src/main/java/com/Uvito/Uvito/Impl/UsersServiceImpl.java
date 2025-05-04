package com.Uvito.Uvito.Impl;

import com.Uvito.Uvito.models.Users;
import com.Uvito.Uvito.repository.UsersRepository;
import com.Uvito.Uvito.service.UsersService;  // Убедись, что импорт правильный
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(UsersRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Users> findAllUsers() {
        return repository.findAll();
    }

    @Override
    public Users saveUsers(Users users) {
        return repository.save(users);
    }

    @Override
    public Users findByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }

    @Override
    public Users updateUsers(Users users) {
        return repository.save(users);
    }

    @Override
    public void deleteUsers(String email) {
        Users user = repository.findByEmail(email).orElse(null);
        if (user != null) {
            repository.delete(user);
        }
    }
    @Transactional
    @Override
    public Users register(Users users) {
        Optional<Users> existingUser = repository.findByUsername(users.getUsername());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Username is already taken!");
        }
        return repository.save(users);
    }

    @Override
    public Optional<Users> findByUsername(String username) {
        return repository.findByUsername(username);
    }
}
