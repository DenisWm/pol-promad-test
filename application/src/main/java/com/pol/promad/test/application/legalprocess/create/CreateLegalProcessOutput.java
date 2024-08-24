package com.pol.promad.test.application.legalprocess.create;

import com.pol.promad.test.domain.legalprocess.LegalProcess;

public record CreateLegalProcessOutput(String id) {
    public static CreateLegalProcessOutput from(final LegalProcess aLegalProcess) {
        return new CreateLegalProcessOutput(aLegalProcess.getId().getValue());
    }
}
