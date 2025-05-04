package com.Uvito.Uvito.service;

import com.Uvito.Uvito.models.UselessItems;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Optional;

public interface UselessItemsService {

    List<UselessItems> findAllUselessItems();

    UselessItems saveItem(UselessItems uselessItems);

    UselessItems findByName(String name);

    Optional<UselessItems> findById(Long id);

    UselessItems updateUselessItem(UselessItems uselessItems);

    void deleteUselessItems(String name);

    void deleteItemById(Long id);
    // Новый метод для сохранения одного изображения
    String saveImage(MultipartFile file);
}
