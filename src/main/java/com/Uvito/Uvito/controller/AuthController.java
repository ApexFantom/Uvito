package com.Uvito.Uvito.controller;
import com.Uvito.Uvito.JwtUtil;
import com.Uvito.Uvito.ValidationUtils;
import com.Uvito.Uvito.models.Users;
import com.Uvito.Uvito.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UsersService usersService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usersService = usersService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // Регистрация
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Users user) {
        try {
            // Валидация email
            if (!ValidationUtils.isValidEmail(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Invalid email format", false));
            }

            // Валидация пароля
            if (!ValidationUtils.isValidPassword(user.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Invalid password format", false));
            }


            // Хэшируем пароль перед сохранением
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            System.out.println("Encoded password: " + user.getPassword());
            usersService.register(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Registration successful", true));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage(), false));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        // Вывод для проверки данных
        System.out.println("Login email: " + email);
        System.out.println("Raw password: " + password);

        // Валидация email
        if (!ValidationUtils.isValidEmail(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Invalid email format", false));
        }

        Users user = usersService.findByEmail(email);

        if (user == null) {
            System.out.println("User not found with email: " + email);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("Invalid credentials", false));
        }

        System.out.println("Encoded password from DB: " + user.getPassword());

        if (passwordEncoder.matches(password, user.getPassword())) {
            String token = jwtUtil.generateToken(user.getUsername());
            return ResponseEntity.ok(new AuthResponse(token));
        } else {
            System.out.println("Password does not match!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("Invalid credentials", false));
        }
    }



    // DTO-класс для входящих данных
    public static class LoginRequest {
        private String email;
        private String password;

        // Геттеры и сеттеры
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    // DTO-класс для ответа
    public static class ApiResponse {
        private String message;
        private boolean success;

        public ApiResponse(String message, boolean success) {
            this.message = message;
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public boolean isSuccess() {
            return success;
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        // Удаляем сессию
        request.getSession().invalidate();

        // Можно дополнительно очищать cookies
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok(new ApiResponse("Logout successful", true));
    }
    public class AuthResponse {
        private String token;

        public AuthResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}



