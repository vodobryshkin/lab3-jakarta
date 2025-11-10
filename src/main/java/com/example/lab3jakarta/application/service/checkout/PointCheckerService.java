package com.example.lab3jakarta.application.service.checkout;

import com.example.lab3jakarta.application.dto.request.CheckerStage;
import com.example.lab3jakarta.application.dto.request.CheckoutRequest;
import com.example.lab3jakarta.application.dto.request.PointCheckerRequest;
import com.example.lab3jakarta.application.dto.request.ValManagerType;
import com.example.lab3jakarta.application.dto.response.PointCheckerResponse;
import com.example.lab3jakarta.application.service.checkout.managers.CheckoutManager;
import com.example.lab3jakarta.domain.entities.Hit;
import lombok.Setter;
import ru.ifmo.se.validation.managers.ValidationCompleteManager;
import ru.ifmo.se.validation.managers.ValidationRManager;
import ru.ifmo.se.validation.managers.interfaces.ValidationManager;
import ru.ifmo.se.validation.request.ValidationRequest;

import java.io.IOException;

/**
 * Класс, реализующий сервис по проверке попадания точки в выбранную область.
 */
public class PointCheckerService {
    @Setter
    private ValidationManager validationManager;
    private final String valConfigPath;
    private final CheckoutManager checkoutManager;

    /**
     * Конструктор класса PointCheckerService, который принимает пути к файлу.
     * Внутри него создаются экземпляры менеджера для валидации и проверки попадания.
     *
     * @param valConfigPath путь к конфигурации для валидации.
     * @param areaConfigPath путь к конфигурации для создания областей для проверки.
     * @throws IOException при вводе некорректных данных.
     */
    public PointCheckerService(String valConfigPath, String areaConfigPath) throws IOException {
        validationManager = new ValidationCompleteManager(valConfigPath);
        checkoutManager = new CheckoutManager(areaConfigPath);

        this.valConfigPath = valConfigPath;
    }

    /**
     * Метод для проверки бизнес-логикой.
     * 1. Сначала происходит проверка на то, какой ValidationManager использовать. Если пришел запрос на полную проверку,
     * а текущий manager проверяет только R, то нужно его поменять и наоборот.
     * 2. После этого формируется запрос на валидацию и сама валидация. Если валидация прошла неудачно, то
     * возвращается экземпляр класса PointCheckerResponse с status=false и checkerStage = Validation.
     * 3. Если валидация прошла успешно, то создаётся
     *
     * @param request запрос на проверку бизнес логики.
     */
    public PointCheckerResponse check(PointCheckerRequest request) throws IOException {

        if (request.getValManagerType() == ValManagerType.Full && validationManager instanceof ValidationRManager
                || request.getValManagerType() == ValManagerType.OnlyR && validationManager instanceof ValidationCompleteManager) {
            resetValidationManager();
        }

        ValidationRequest validationRequest = new ValidationRequest(request.getX(), request.getY(), request.getR());

        if (!validationManager.validate(validationRequest)) {
            return new PointCheckerResponse(new Hit(request.getX(), request.getY(), request.getR(), false), CheckerStage.Validation);
        }

        CheckoutRequest checkoutRequest = new CheckoutRequest(request.getX(), request.getY(), request.getR());

        return new PointCheckerResponse(new Hit(request.getX(), request.getY(), request.getR(), checkoutManager.checkRequest(checkoutRequest)), CheckerStage.Checkout);
    }

    // Переключение менеджера валидации
    private void resetValidationManager() throws IOException {
        if (validationManager instanceof ValidationCompleteManager) {
            setValidationManager(new ValidationRManager(valConfigPath));
            return;
        }
        setValidationManager(new ValidationCompleteManager(valConfigPath));
    }
}
