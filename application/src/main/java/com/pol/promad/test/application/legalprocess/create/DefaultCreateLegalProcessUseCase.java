package com.pol.promad.test.application.legalprocess.create;

import com.pol.promad.test.domain.exceptions.NotificationException;
import com.pol.promad.test.domain.legalprocess.LegalProcess;
import com.pol.promad.test.domain.legalprocess.LegalProcessGateway;
import com.pol.promad.test.domain.validation.Error;
import com.pol.promad.test.domain.validation.ValidationHandler;
import com.pol.promad.test.domain.validation.handler.Notification;

import java.util.Objects;

public class DefaultCreateLegalProcessUseCase extends CreateLegalProcessUseCase {

    private final LegalProcessGateway gateway;

    public DefaultCreateLegalProcessUseCase(final LegalProcessGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    @Override
    public CreateLegalProcessOutput execute(final CreateLegalProcessCommand aCommand) {
        final var number = aCommand.number();
        final var status = aCommand.status();

        final var notification = Notification.create();

        notification.append(validateNumber(number));

        final var aLegalProcess = notification.validate(() -> LegalProcess.newLegalProcess(number, status));

        if (notification.hasErrors()) {
            throw new NotificationException("O aggregado LegalProcess não pode ser criado", notification);
        }

        return CreateLegalProcessOutput.from(this.gateway.create(aLegalProcess));
    }

    private ValidationHandler validateNumber(final String aNumber) {
        final var notification = Notification.create();
        if (aNumber == null || aNumber.isEmpty()) {
            return notification;
        }
        final var existsNumber = gateway.existsByNumber(aNumber);
        if (existsNumber) {
            notification.append(Error.with("O processo com número %s já existe no sistema".formatted(aNumber)));
        }
        return notification;
    }
}
