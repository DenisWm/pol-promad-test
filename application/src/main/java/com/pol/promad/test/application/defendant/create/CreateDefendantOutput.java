package com.pol.promad.test.application.defendant.create;

import com.pol.promad.test.domain.defendant.Defendant;

public record CreateDefendantOutput(String id) {
    public static CreateDefendantOutput from(final Defendant defendant) {
        return new CreateDefendantOutput(defendant.getId().getValue());
    }
    public static CreateDefendantOutput from(final String anId) {
        return new CreateDefendantOutput(anId);
    }
}
