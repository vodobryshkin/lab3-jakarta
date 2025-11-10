package com.example.lab3jakarta.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * DTO для передачи данных в PointCheckerService.
 */
@Data
@AllArgsConstructor
public class PointCheckerRequest {
    private final String x;
    private final String y;
    private final String r;
    private final ValManagerType valManagerType;

    /**
     * Конструктор для упрощения создания DTO-запроса к бизнес-логике.
     * Принимает queryString и парсит её. На основе этого, формирует DTO-запрос.
     *
     * @param queryString строка с параметрами.
     */
    public PointCheckerRequest(String queryString) {
        Map<String, String> p = Arrays.stream(queryString.replaceFirst("^\\?","").split("&"))
                .map(s -> s.split("=", 2))
                .filter(a -> a.length == 2)
                .collect(Collectors.toMap(
                        a -> a[0].toLowerCase(Locale.ROOT),
                        a -> URLDecoder.decode(a[1], StandardCharsets.UTF_8))
                );

        this.x = p.get("x");
        this.y = p.get("y");
        this.r = p.get("r");
        this.valManagerType = "\"full\"".equalsIgnoreCase(p.get("type")) ? ValManagerType.Full : ValManagerType.OnlyR;
    }


    public PointCheckerRequest(BigDecimal x, BigDecimal y, BigDecimal r, ValManagerType valManagerType) {
        this(String.valueOf(x), String.valueOf(y), String.valueOf(r), valManagerType);
    }
}
