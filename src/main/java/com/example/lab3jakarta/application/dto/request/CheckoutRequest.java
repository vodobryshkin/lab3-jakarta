package com.example.lab3jakarta.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.ifmo.se.gmt.geometry.model.Point;

import java.math.BigDecimal;

/**
 * DTO для передачи данных в CheckoutManager.
 */
@Data
@AllArgsConstructor
public class CheckoutRequest {
    private final Point point;
    private final BigDecimal r;

    public CheckoutRequest(String x, String y, String r) {
        this(new Point(new BigDecimal(x), new BigDecimal(y)), new BigDecimal(r));
    }
}
