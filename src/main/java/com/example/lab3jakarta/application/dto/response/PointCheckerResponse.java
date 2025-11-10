package com.example.lab3jakarta.application.dto.response;

import com.example.lab3jakarta.application.dto.request.CheckerStage;
import com.example.lab3jakarta.domain.entities.Hit;
import lombok.Data;

/**
 * DTO для получения данных из CheckoutManager.
 */
@Data
public class PointCheckerResponse {
    private final Hit hit;
    private final CheckerStage checkerStage;
}
