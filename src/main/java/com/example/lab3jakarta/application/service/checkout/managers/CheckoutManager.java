package com.example.lab3jakarta.application.service.checkout.managers;

import com.example.lab3jakarta.application.dto.request.CheckoutRequest;
import ru.ifmo.se.gmt.geometry.areas.factory.AreaFactory;
import ru.ifmo.se.gmt.geometry.areas.interfaces.Area;
import ru.ifmo.se.gmt.parser.JsonAreasConfigParser;
import ru.ifmo.se.gmt.request.implementations.messages.AreasRequest;

import java.io.IOException;
import java.util.List;

/**
 * Класс для реализации бизнес-логики лабораторной работы.
 * Проверяет вхождение точки в области.
 */
public class CheckoutManager {
    private final AreaContext areaContext;
    private final AreasRequest areasRequest;

    /**
     * Конструктор класса CheckoutManager.
     * Данные обо всех областях сразу собираются при создании объекта, чтобы избежать накладок с постоянным обращением к
     * памяти.
     */
    public CheckoutManager(String configName) throws IOException {
        areaContext = new AreaContext();
        areasRequest = new JsonAreasConfigParser().parse(configName);
    }

    /**
     * Метод для запуска проверок на запрос
     * Каждый раз устанавливаем область в AreaContext и он её проверяет.
     * Если точка лежит хоть в одной из областей, то status=true и нет смысла смотреть вхождение дальше.
     * Сначала, используя фабрику областей, формируется список всех областей. Затем в цикле каждый раз в areaContext
     * устанавливается новая область и происходит проверка вхождения.
     *
     * @param request запрос для проверки.
     * @return информацию входит ли точка в область (true) или нет (false).
     */
    public boolean checkRequest(CheckoutRequest request) {
        List<Area> areaList = new AreaFactory().createAreas(areasRequest, request.getR());
        boolean status = false;

        for (Area area: areaList) {
            areaContext.setGeometryArea(area);

            if (areaContext.execute(request.getPoint())) {
                status = true;
                break;
            }
        }

        return status;
    }
}
