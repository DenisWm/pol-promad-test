package com.pol.promad.test.application.legalprocess.delete;

import com.pol.promad.test.domain.legalprocess.LegalProcessGateway;
import com.pol.promad.test.domain.legalprocess.LegalProcessID;

import java.util.Objects;

public class DefaultDeleteLegalProcessUseCase extends DeleteLegalProcessUseCase {

    private final LegalProcessGateway legalProcessGateway;

    public DefaultDeleteLegalProcessUseCase(final LegalProcessGateway legalProcessGateway) {
        this.legalProcessGateway = Objects.requireNonNull(legalProcessGateway);
    }
    @Override
    public void execute(final String anId) {
        this.legalProcessGateway.deleteById(LegalProcessID.from(anId));
    }
}
