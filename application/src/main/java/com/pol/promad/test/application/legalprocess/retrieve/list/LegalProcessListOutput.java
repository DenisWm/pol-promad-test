package com.pol.promad.test.application.legalprocess.retrieve.list;

import com.pol.promad.test.domain.legalprocess.LegalProcess;

public record LegalProcessListOutput(String id, String number, String status) {
    public static LegalProcessListOutput from(final LegalProcess legalProcess) {
        return new LegalProcessListOutput(
                legalProcess.getId().getValue(),
                legalProcess.getNumber(),
                legalProcess.getStatus().getValue())
        ;
    }
}
