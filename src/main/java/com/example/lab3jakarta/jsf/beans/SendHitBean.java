package com.example.lab3jakarta.jsf.beans;

import com.example.lab3jakarta.application.dto.request.CheckerStage;
import com.example.lab3jakarta.application.dto.request.PointCheckerRequest;
import com.example.lab3jakarta.application.dto.request.ValManagerType;
import com.example.lab3jakarta.application.dto.response.PointCheckerResponse;
import com.example.lab3jakarta.application.service.checkout.PointCheckerService;
import com.example.lab3jakarta.domain.entities.Hit;
import com.example.lab3jakarta.domain.repository.HitRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Бин для записи значений с формы
 */
@Named("sendHitBean")
@RequestScoped
public class SendHitBean {
    @Getter @Setter
    private BigDecimal x;
    @Getter @Setter
    private BigDecimal y;
    @Getter @Setter
    private BigDecimal r;
    @Getter @Setter
    private BigDecimal hiddenX;
    @Getter @Setter
    private BigDecimal hiddenY;
    @Getter @Setter
    private String result;
    @Getter @Setter @Inject
    private @Named("hitRepository") HitRepository hitRepository;

    private PointCheckerService pointCheckerService;

    @PostConstruct
    public void init() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try (InputStream validation = cl.getResourceAsStream("validation.json");
             InputStream areas = cl.getResourceAsStream("areas.json")) {
            Path v = Files.createTempFile("validation", ".json");
            Path a = Files.createTempFile("areas", ".json");
            Files.copy(validation, v, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(areas, a, StandardCopyOption.REPLACE_EXISTING);

            pointCheckerService = new PointCheckerService(v.toString(), a.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void validate() {
        doLogic(new PointCheckerRequest(x, y, r, ValManagerType.Full));
    }

    public void addFromClick() {
        doLogic(new PointCheckerRequest(hiddenX, hiddenY, r, ValManagerType.OnlyR));
    }

    private void doLogic(PointCheckerRequest pointCheckerRequest) {
        try {
            PointCheckerResponse pointCheckerResponse = pointCheckerService.check(pointCheckerRequest);
            if (pointCheckerResponse.getCheckerStage().equals(CheckerStage.Validation)) {
                setResult("Валидация значений не пройдена.");
                return;
            }

            Hit hit = pointCheckerResponse.getHit();

            hitRepository.addHit(hit);

            if (pointCheckerResponse.getHit().isStatus()) {
                setResult("Точка попала.");
            } else {
                setResult("Точка не попала.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
