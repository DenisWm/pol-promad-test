package com.pol.promad.test.application.legalprocess.retrieve.get;

import com.pol.promad.test.application.utils.IDNotFoundUtils;
import com.pol.promad.test.domain.legalprocess.LegalProcess;
import com.pol.promad.test.domain.legalprocess.LegalProcessGateway;
import com.pol.promad.test.domain.legalprocess.LegalProcessID;

import java.util.Objects;

public class DefaultGetLegalProcessByIdUseCase extends GetLegalProcessByIdUseCase {
    private final LegalProcessGateway legalProcessGateway;

    public DefaultGetLegalProcessByIdUseCase(final LegalProcessGateway legalProcessGateway) {
        this.legalProcessGateway = Objects.requireNonNull(legalProcessGateway);
    }

    @Override
    public LegalProcessOutput execute(final String anId) {
        return legalProcessGateway.findById(LegalProcessID.from(anId))
                .map(LegalProcessOutput::from)
                .orElseThrow(IDNotFoundUtils.notFound(LegalProcessID.from(anId), LegalProcess.class));
    }
}
