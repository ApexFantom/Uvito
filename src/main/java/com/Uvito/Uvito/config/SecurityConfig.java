package com.Uvito.Uvito.config;
import com.Uvito.Uvito.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;


    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/login", "/register", "/").permitAll()
                        .requestMatchers("/scripts/**", "/styles/**", "/images/**").permitAll()
                        .requestMatchers("/uploads/images/**").permitAll()  // это правило уже есть
                        .requestMatchers(HttpMethod.GET, "/uploads/images/**").permitAll()  // явно разрешаем GET запросы на изображения
                        .requestMatchers(HttpMethod.GET, "/api/useless-items/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/useless-items/**").authenticated() // POST только авторизованным
                        .requestMatchers(HttpMethod.PUT, "/api/useless-items/**").authenticated() // PUT только авторизованным
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin().disable();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*"); // Разрешаем все источники
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*"); // Разрешаем все методы
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
}





