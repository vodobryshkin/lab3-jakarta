package com.example.lab3jakarta.domain.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Класс описывающий модель "Точка на координатной плоскости".
 */
@Getter @ToString @EqualsAndHashCode
public class Point {
    private final BigDecimal x;
    private final BigDecimal y;

    /**
     * Основной конструктор класса с использованием типа BigDecimal
     *
     * @param x координата x в формате BigDecimal
     * @param y координата y в формате BigDecimal
     */
    public Point(BigDecimal x, BigDecimal y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Конструктор класса с использованием любого числового
     *
     * @param x координата x с типом любого числового типа
     * @param y координата y с типом любого числового типа
     * @param <T> любой числовой тип
     */
    public <T extends Number> Point(T x, T y) {
        this(new BigDecimal(String.valueOf(x)), new BigDecimal(String.valueOf(y)));
    }

    /**
     * Конструктор класса с использованием строки
     *
     * @param x координата x с типом String
     * @param y координата y с типом String
     */
    public Point(String x, String y) {
        this(new BigDecimal(x), new BigDecimal(y));
    }
}