package com.pol.promad.test.application.legalprocess.update;

import com.pol.promad.test.domain.legalprocess.LegalProcess;

public record UpdateLegalProcessOutput(
        String id
) {
    public static UpdateLegalProcessOutput from(final LegalProcess legalprocess) {
        return new UpdateLegalProcessOutput(legalprocess.getId().getValue());
    }
}
