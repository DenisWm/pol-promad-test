package com.pol.promad.test.application.legalprocess.retrieve.get;

import com.pol.promad.test.application.legalprocess.update.UpdateLegalProcessOutput;
import com.pol.promad.test.domain.defendant.DefendantID;
import com.pol.promad.test.domain.legalprocess.LegalProcess;

import java.util.List;

public record LegalProcessOutput(String id, String number, String status, List<String> defendants) {
    public static LegalProcessOutput from(final LegalProcess aLegalProcess) {
        return new LegalProcessOutput(
                aLegalProcess.getId().getValue(),
                aLegalProcess.getNumber(),
                aLegalProcess.getStatus().getValue(),
                aLegalProcess.getDefendants()
                        .stream()
                        .map(DefendantID::getValue)
                        .toList()
        );
    }
}
