package com.example.lab3jakarta.application.service.checkout.managers;

import lombok.Setter;
import ru.ifmo.se.gmt.geometry.areas.interfaces.Area;
import ru.ifmo.se.gmt.geometry.model.Point;

/**
 * Класс для упрощения процесса проверки на вхождение точки в область.
 */
public class AreaContext {
    @Setter
    private Area geometryArea;

    /**
     * Метод для запуска проверок на вхождение внутрь области.
     *
     * @param point точка для проверки.
     * @return информацию входит ли точка в область (true) или нет (false).
     */
    public boolean execute(Point point) {
        return geometryArea.checkPoint(point);
    }
}
