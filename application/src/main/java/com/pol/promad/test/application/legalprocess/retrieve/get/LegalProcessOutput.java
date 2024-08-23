package com.pol.promad.test.application.legalprocess.retrieve.get;

import com.pol.promad.test.domain.legalprocess.LegalProcess;

public record LegalProcessOutput(String number, String status) {
    public static LegalProcessOutput from(final LegalProcess aLegalProcess) {
        return new LegalProcessOutput(aLegalProcess.getNumber(), aLegalProcess.getStatus().getValue());
    }
}
