package com.pol.promad.test.application.defendant.create;

import com.pol.promad.test.domain.defendant.Defendant;
import com.pol.promad.test.domain.defendant.DefendantGateway;
import com.pol.promad.test.domain.exceptions.NotificationException;
import com.pol.promad.test.domain.validation.handler.Notification;

import java.util.Objects;

public class DefaultCreateDefendantUseCase extends CreateDefendantUseCase {

    private final DefendantGateway defendantGateway;

    public DefaultCreateDefendantUseCase(final DefendantGateway defendantGateway) {
        this.defendantGateway = Objects.requireNonNull(defendantGateway);
    }

    @Override
    public CreateDefendantOutput execute(CreateDefendantCommand command) {
        final var name = command.name();
        final var notification = Notification.create();
        final var aDefendant =  notification.validate(() -> Defendant.newDefendant(name));

        if(notification.hasErrors()) {
            throw new NotificationException("O aggregado Defendant não pode ser criado", notification);
        }
        return CreateDefendantOutput.from(defendantGateway.create(aDefendant));
    }
}
