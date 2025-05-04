package com.Uvito.Uvito.models;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder(toBuilder = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UselessItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    private String description;

    @Column(name = "date_create")
    private LocalDateTime dateCreate;

    @Column(name = "date_update")
    private LocalDateTime dateUpdate;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    private Users author;

    // Ссылка на одно изображение
    @Column(name = "image_url")
    private String imageUrl;

    // Метод для установки изображения
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDateUpdate() {
        this.dateUpdate = LocalDateTime.now();
    }
}

