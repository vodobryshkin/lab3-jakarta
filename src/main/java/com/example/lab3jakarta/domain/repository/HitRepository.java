package com.example.lab3jakarta.domain.repository;

import com.example.lab3jakarta.domain.entities.Hit;

import java.util.List;
import java.util.UUID;

/**
 * Интерфейс для описания репозитория для попаданий.
 */
public interface HitRepository {
    /**
     * Метод для добавления попадания в репозиторий
     *
     * @param hit логическая модель попадания
     */
    void addHit(Hit hit);

    /**
     * Метод для получения результатов всех попаданий
     *
     * @return результаты всех попаданий
     */
    List<Hit> getAllHits();
}
