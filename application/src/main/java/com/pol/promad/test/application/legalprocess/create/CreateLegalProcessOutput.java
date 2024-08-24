package com.pol.promad.test.application.legalprocess.create;

import com.pol.promad.test.domain.legalprocess.LegalProcess;
import com.pol.promad.test.domain.legalprocess.LegalProcessID;

public record CreateLegalProcessOutput(String id) {
    public static CreateLegalProcessOutput from(final LegalProcess aLegalProcess) {
        return new CreateLegalProcessOutput(aLegalProcess.getId().getValue());
    }
    public static CreateLegalProcessOutput from(final String anId) {
        return new CreateLegalProcessOutput(anId);
    }
}
