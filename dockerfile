# Используем официальный образ для Java
FROM openjdk:17-jdk-slim as builder

# Устанавливаем Maven
RUN apt-get update && apt-get install -y maven

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файл pom.xml и качаем зависимости
COPY pom.xml .
RUN mvn dependency:go-offline

# Копируем исходный код приложения
COPY src ./src

# Собираем приложение с помощью Maven
RUN mvn clean package -DskipTests

# Используем официальный образ для запуска Spring Boot приложения
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем jar-файл из предыдущего этапа
COPY --from=builder /app/target/Uvito-0.0.1-SNAPSHOT.jar Uvito.jar

# Открываем порт 8080
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "Uvito.jar"]
