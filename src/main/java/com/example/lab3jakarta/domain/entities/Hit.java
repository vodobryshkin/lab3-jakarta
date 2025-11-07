package com.example.lab3jakarta.domain.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Класс описывающий модель "Выстрел точки на координатную плоскость".
 */
@Getter @ToString @EqualsAndHashCode
public class Hit implements Serializable {
    private final UUID id;
    private final Point point;
    private final BigDecimal radius;
    private final boolean status;

    /**
     * Основной конструктор класса с использованием типа Point для точки и BigDecimal для радиуса
     *
     * @param point точка на координатной плоскости
     * @param radius текущий радиус координатной плоскости
     * @param status статус попадания (успех/неудача)
     */
    public Hit(Point point, BigDecimal radius, boolean status) {
        this.id = UUID.randomUUID();
        this.point = point;
        this.radius = radius;
        this.status = status;
    }

    /**
     * Конструктор класса с использованием разложения полей.
     *
     * @param uuid уникальный id объекта
     * @param x координата точки по оси абсцисс
     * @param y координата точки по оси ординат
     * @param radius радиус координатной плоскости
     * @param status статус попадания (успех/неудача)
     */
    public Hit(UUID uuid, BigDecimal x, BigDecimal y, BigDecimal radius, boolean status) {
        this.id = uuid;
        this.point = new Point(x.stripTrailingZeros(), y.stripTrailingZeros());
        this.radius = radius.stripTrailingZeros();
        this.status = status;
    }

    /**
     * Конструктор класса с передачей параметров координат точки и для радиуса
     * в любом числовом типе данных
     *
     * @param x координата точки по оси абсцисс
     * @param y координата точки по оси ординат
     * @param radius радиус координатной плоскости
     * @param status статус попадания (успех/неудача)
     * @param <T> любой числовой тип
     */
    public <T extends Number> Hit(T x, T y, T radius, boolean status) {
       this(new Point(x, y), new BigDecimal(String.valueOf(radius)), status);
    }

    /**
     * Конструктор класса с передачей параметров координат точки и для радиуса
     * в строковом формате
     *
     * @param x координата точки по оси абсцисс
     * @param y координата точки по оси ординат
     * @param radius радиус координатной плоскости
     * @param status статус попадания (успех/неудача)
     */
    public Hit(String x, String y, String radius, boolean status) {
        this(new Point(x, y), new BigDecimal(String.valueOf(radius)), status);
    }
}
