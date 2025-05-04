package com.Uvito.Uvito.Impl;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import com.Uvito.Uvito.models.UselessItems;
import com.Uvito.Uvito.repository.UselessItemsRepository;
import com.Uvito.Uvito.service.UselessItemsService;
import org.springframework.web.multipart.MultipartFile; // Добавлен импорт
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Primary
public class UselessItemsImpl implements UselessItemsService {

    private final UselessItemsRepository repository;

    public UselessItemsImpl(UselessItemsRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UselessItems> findAllUselessItems() {
        return repository.findAll();
    }

    @Override
    public UselessItems saveItem(UselessItems uselessItems) {
        return repository.save(uselessItems);
    }

    @Override
    public UselessItems findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Optional<UselessItems> findById(Long id) {
        return repository.findById(id); // Вызов метода из JpaRepository
    }

    @Override
    public UselessItems updateUselessItem(UselessItems uselessItems) {
        return repository.save(uselessItems);
    }

    @Override
    public void deleteUselessItems(String name) {
        repository.deleteByName(name);
    }

    @Override
    public String saveImage(MultipartFile file) {
        try {
            // Получаем имя файла
            String fileName = file.getOriginalFilename();

            // Определяем путь к папке внутри проекта (относительно корня проекта)
            Path uploadPath = Paths.get(System.getProperty("user.dir"), "uploads", "images");

            // Создаем папки, если они не существуют
            File directory = uploadPath.toFile();
            if (!directory.exists()) {
                directory.mkdirs();  // Создаем папки, если их нет
            }

            // Путь для сохранения файла
            File destinationFile = new File(uploadPath.toFile(), fileName);

            // Сохраняем файл на диск
            file.transferTo(destinationFile);

            // Возвращаем путь к файлу (можно также возвращать URL, если нужно)
            return "/uploads/images/" + fileName;  // Возвращаем абсолютный путь или относительный

        } catch (IOException e) {
            e.printStackTrace();
            return null; // Возвращаем null в случае ошибки
        }
    }

    @Override
    public void deleteItemById(Long id) {
        repository.deleteById(id);  // Используем метод deleteById репозитория
    }




}


