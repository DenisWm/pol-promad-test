package com.pol.promad.test.application.legalprocess.update;

import com.pol.promad.test.domain.exceptions.NotificationException;
import com.pol.promad.test.domain.legalprocess.LegalProcess;
import com.pol.promad.test.domain.legalprocess.LegalProcessGateway;
import com.pol.promad.test.domain.legalprocess.LegalProcessID;
import com.pol.promad.test.domain.validation.handler.Notification;

import java.util.Objects;

import static com.pol.promad.test.application.utils.IDNotFoundUtils.notFound;

public class DefaultUpdateLegalProcessUseCase extends UpdateLegalProcessUseCase {

    private final LegalProcessGateway gateway;

    public DefaultUpdateLegalProcessUseCase(final LegalProcessGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }


    @Override
    public UpdateLegalProcessOutput execute(UpdateLegalProcessCommand aCommand) {
        final var anId = LegalProcessID.from(aCommand.id());
        final var aStatus = aCommand.status();

        final var aLegalProcess = this.gateway.findById(anId)
                .orElseThrow(notFound(anId, LegalProcess.class));

        final var notification = Notification.create();

        final var updatedLegalProcess = notification.validate(() -> aLegalProcess.update(aStatus));

        if (notification.hasErrors()) {
            throw new NotificationException(
                    "O aggregado LegalProcess com ID %s não pode ser atualizado".formatted(aCommand.id()), notification);
        }

        return UpdateLegalProcessOutput.from(this.gateway.update(updatedLegalProcess));
    }
}
