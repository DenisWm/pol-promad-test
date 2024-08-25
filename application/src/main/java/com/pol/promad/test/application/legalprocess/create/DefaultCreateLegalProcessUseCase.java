package com.pol.promad.test.application.legalprocess.create;

import com.pol.promad.test.domain.defendant.DefendantGateway;
import com.pol.promad.test.domain.defendant.DefendantID;
import com.pol.promad.test.domain.exceptions.NotificationException;
import com.pol.promad.test.domain.legalprocess.LegalProcess;
import com.pol.promad.test.domain.legalprocess.LegalProcessGateway;
import com.pol.promad.test.domain.validation.Error;
import com.pol.promad.test.domain.validation.ValidationHandler;
import com.pol.promad.test.domain.validation.handler.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DefaultCreateLegalProcessUseCase extends CreateLegalProcessUseCase {

    private final LegalProcessGateway gateway;
    private final DefendantGateway defendantGateway;

    public DefaultCreateLegalProcessUseCase(final LegalProcessGateway gateway, final DefendantGateway defendantGateway) {
        this.gateway = Objects.requireNonNull(gateway);
        this.defendantGateway = Objects.requireNonNull(defendantGateway);
    }

    @Override
    public CreateLegalProcessOutput execute(final CreateLegalProcessCommand aCommand) {
        final var number = aCommand.number();
        final var status = aCommand.status();
        final var defendants = toDefendantID(aCommand.defendants());

        final var notification = Notification.create();

        notification.append(validateNumber(number));

        notification.append(validateDefendants(defendants));

        final var aLegalProcess = notification.validate(() -> LegalProcess.newLegalProcess(number, status));

        if (notification.hasErrors()) {
            throw new NotificationException("O aggregado LegalProcess não pode ser criado", notification);
        }
        aLegalProcess.addDefendants(defendants);

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

    private ValidationHandler validateDefendants(final List<DefendantID> defendants) {
        final var notification = Notification.create();
        if (defendants == null || defendants.isEmpty()) {
            return notification;
        }

        final var retrievedIds = defendantGateway.existsByIds(defendants);

        if (defendants.size() != retrievedIds.size()) {
            final var missingIds = new ArrayList<>(defendants);
            missingIds.removeAll(retrievedIds);

            final var missingIdsMessage = missingIds.stream()
                    .map(DefendantID::getValue)
                    .collect(Collectors.joining(", "));

            notification.append(new Error("Alguns réus não foram encontrados: %s".formatted(missingIdsMessage)));
        }

        return notification;
    }

    private List<DefendantID> toDefendantID(List<String> defendants) {
        return defendants.stream()
                .map(DefendantID::from)
                .toList();
    }
}
