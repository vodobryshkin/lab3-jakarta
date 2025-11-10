package com.example.lab3jakarta.jsf.beans;

import com.example.lab3jakarta.domain.entities.Hit;
import com.example.lab3jakarta.domain.repository.HitRepository;
import com.google.gson.Gson;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Бин для работы со всеми значениями таблицы в обход пажинирования.
 */

@Named("tableBean")
@ViewScoped
public class TableBean implements Serializable{
    @Getter @Setter @Inject
    private @Named("hitRepository") HitRepository hitRepository;

    /**
     * Метод для получения всех точек на стороне клиента.
     * Нужен потому что таблица работает по страничному принципу, и взять все точки просто по id не получится.
     *
     * @return представление всех точек в виде JSON-объекта
     */
    public String getJson() {
        List<Hit> hits = hitRepository.getAllHits();
        List<Map<String, ? extends Serializable>> data = (hits == null ? List.<Hit>of() : hits)
                .stream()
                .map(hit -> Map.of(
                        "x", hit.getPoint().getX(),
                        "y", hit.getPoint().getY(),
                        "r", hit.getRadius(),
                        "status", hit.isStatus()
                ))
                .collect(Collectors.toList());

        return new Gson().toJson(data);
    }
}
