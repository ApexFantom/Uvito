package com.Uvito.Uvito;

import java.util.regex.Pattern;

public class ValidationUtils {

    // Регулярное выражение для email
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    // Регулярное выражение для пароля
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,20}$";
    // Регулярное выражение для никнейма
    private static final String NICKNAME_REGEX = "^[a-zA-Z0-9_-]{3,15}$";

    // Метод для валидации email
    public static boolean isValidEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

    // Метод для валидации пароля
    public static boolean isValidPassword(String password) {
        return Pattern.matches(PASSWORD_REGEX, password);
    }

    // Метод для валидации никнейма
    public static boolean isValidNickname(String nickname) {
        return Pattern.matches(NICKNAME_REGEX, nickname);
    }
}

