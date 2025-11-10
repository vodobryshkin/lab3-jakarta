package com.example.lab3jakarta.infrastructure.entities;

import com.example.lab3jakarta.domain.entities.Hit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Класс для реализации табличной сущности "Попадние".
 */
@Entity
@Table(name = "hit")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DbHit {
    @Id
    @Column(name="uuid")
    private UUID id;

    @Column(name = "x", precision = 38, scale = 2)
    private BigDecimal x;

    @Column(name = "y", precision = 38, scale = 2)
    private BigDecimal y;

    @Column(name = "r", precision = 38, scale = 2)
    private BigDecimal radius;

    @Column(name = "status")
    private boolean status;

    /**
     * Конструктор класса табличной сущности, который выступает конвертером между логической сущностью "Выстрел точки
     * на координатную плоскость" и данной табличной сущностью.
     *
     * @param hit объект логической сущности "Выстрел точки на координатную плоскость".
     */
    public DbHit(Hit hit) {
        this(
                hit.getId(),
                hit.getPoint().getX(),
                hit.getPoint().getY(),
                hit.getRadius(),
                hit.isStatus()
        );
    }
}
