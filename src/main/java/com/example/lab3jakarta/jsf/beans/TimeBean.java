package com.example.lab3jakarta.jsf.beans;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Бин для получения текущего времени
 */
@Named("timeBean")
@ViewScoped
public class TimeBean implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public String getNow() {
        ZoneId zoneId = ZoneId.of("Europe/Moscow");
        LocalDateTime now = LocalDateTime.now(zoneId);

        return now.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
    }
}
