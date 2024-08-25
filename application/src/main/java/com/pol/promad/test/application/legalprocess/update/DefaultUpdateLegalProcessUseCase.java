package com.pol.promad.test.application.legalprocess.update;

import com.pol.promad.test.domain.defendant.DefendantGateway;
import com.pol.promad.test.domain.defendant.DefendantID;
import com.pol.promad.test.domain.exceptions.NotificationException;
import com.pol.promad.test.domain.legalprocess.LegalProcess;
import com.pol.promad.test.domain.legalprocess.LegalProcessGateway;
import com.pol.promad.test.domain.legalprocess.LegalProcessID;
import com.pol.promad.test.domain.validation.Error;
import com.pol.promad.test.domain.validation.ValidationHandler;
import com.pol.promad.test.domain.validation.handler.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.pol.promad.test.application.utils.IDNotFoundUtils.notFound;

public class DefaultUpdateLegalProcessUseCase extends UpdateLegalProcessUseCase {

    private final LegalProcessGateway gateway;
    private final DefendantGateway defendantGateway;

    public DefaultUpdateLegalProcessUseCase(final LegalProcessGateway gateway, final DefendantGateway defendantGateway) {
        this.gateway = Objects.requireNonNull(gateway);
        this.defendantGateway = Objects.requireNonNull(defendantGateway);
    }


    @Override
    public UpdateLegalProcessOutput execute(UpdateLegalProcessCommand aCommand) {
        final var anId = LegalProcessID.from(aCommand.id());
        final var defendants = toDefendantID(aCommand.defendants());

        final var aLegalProcess = this.gateway.findById(anId)
                .orElseThrow(notFound(anId, LegalProcess.class));

        final var aStatus = hasText(aCommand.status()) ? aCommand.status() : aLegalProcess.getStatus().getValue();

        final var notification = Notification.create();
        notification.append(validateDefendants(defendants));
        final var updatedLegalProcess = notification.validate(() -> aLegalProcess.update(aStatus, defendants));

        if (notification.hasErrors()) {
            throw new NotificationException(
                    "O aggregado LegalProcess com ID %s não pode ser atualizado".formatted(aCommand.id()), notification);
        }

        return UpdateLegalProcessOutput.from(this.gateway.update(updatedLegalProcess));
    }

    private boolean hasText(final String status) {
        return status != null && !status.isBlank();
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
