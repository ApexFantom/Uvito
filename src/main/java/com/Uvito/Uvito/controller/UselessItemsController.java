package com.Uvito.Uvito.controller;

import com.Uvito.Uvito.service.UselessItemsService;
import com.Uvito.Uvito.service.UsersService;
import com.Uvito.Uvito.models.UselessItems;
import com.Uvito.Uvito.models.Users;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/useless-items")
public class UselessItemsController {

    private final UselessItemsService uselessItemsService;
    private final UsersService usersService; // Сервис для работы с пользователями

    @Autowired
    public UselessItemsController(UselessItemsService uselessItemsService, UsersService usersService) {
        this.uselessItemsService = uselessItemsService;
        this.usersService = usersService;
    }

    // Получить все предметы
    @GetMapping
    public List<UselessItems> getAllItems() {
        return uselessItemsService.findAllUselessItems();
    }

    // Получить один предмет по имени
    @GetMapping("/{name}")
    public ResponseEntity<UselessItems> getItemByName(@PathVariable String name) {
        UselessItems item = uselessItemsService.findByName(name);
        if (item != null) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Добавить новый предмет
    @PostMapping
    public ResponseEntity<UselessItems> createItem(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile image,
            @RequestParam("authorEmail") String authorEmail) {  // Параметр для email автора

        // Получаем пользователя по email
        Users author = usersService.findByEmail(authorEmail);
        if (author == null) {
            return ResponseEntity.badRequest().body(null); // Если автор не найден
        }

        String imageUrl = uselessItemsService.saveImage(image); // Сохраняем изображение

        // Создаем новый объект и сохраняем его в базе данных
        UselessItems newItem = UselessItems.builder()
                .name(name)
                .description(description)
                .imageUrl(imageUrl) // Присваиваем ссылку на изображение
                .author(author)  // Устанавливаем автора
                .dateCreate(LocalDateTime.now())
                .dateUpdate(LocalDateTime.now())
                .build();

        UselessItems savedItem = uselessItemsService.saveItem(newItem);

        return ResponseEntity.ok(savedItem);
    }

    // Обновить существующий предмет
    @PutMapping("/{id}")
    public ResponseEntity<UselessItems> updateItem(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam("authorEmail") String authorEmail) {

        // Получаем пользователя по email
        Users author = usersService.findByEmail(authorEmail);
        if (author == null) {
            return ResponseEntity.badRequest().body(null); // Если автор не найден
        }

        // Ищем существующий объект по ID
        UselessItems existingItem = uselessItemsService.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id)); // Если объект не найден

        // Обновляем поля объекта
        existingItem.setName(name);
        existingItem.setDescription(description);
        existingItem.setAuthor(author); // Обновляем автора, если требуется
        existingItem.setDateUpdate(LocalDateTime.now()); // Обновляем время изменения

        // Если передано новое изображение, сохраняем его и обновляем ссылку
        if (image != null && !image.isEmpty()) {
            String imageUrl = uselessItemsService.saveImage(image);
            existingItem.setImageUrl(imageUrl);
        }

        // Сохраняем изменения
        UselessItems updatedItem = uselessItemsService.saveItem(existingItem);

        return ResponseEntity.ok(updatedItem);
    }



    // Удалить предмет по имени
    @DeleteMapping("/name/{name}")
    public ResponseEntity<Void> deleteItemByName(@PathVariable String name) {
        uselessItemsService.deleteUselessItems(name);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        UselessItems existingItem = uselessItemsService.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id)); // Если объект не найден

        // Удаляем объект через сервис
        uselessItemsService.deleteItemById(id);

        return ResponseEntity.noContent().build();  // Возвращаем статус 204 (No Content)
    }
}


