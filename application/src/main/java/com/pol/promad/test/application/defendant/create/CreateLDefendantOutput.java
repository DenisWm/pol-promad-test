package com.pol.promad.test.application.defendant.create;

import com.pol.promad.test.domain.defendant.Defendant;
import com.pol.promad.test.domain.legalprocess.LegalProcess;

public record CreateLDefendantOutput(String id) {
    public static CreateLDefendantOutput from(final Defendant defendant) {
        return new CreateLDefendantOutput(defendant.getId().getValue());
    }
    public static CreateLDefendantOutput from(final String anId) {
        return new CreateLDefendantOutput(anId);
    }
}
