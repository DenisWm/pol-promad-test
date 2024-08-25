package com.pol.promad.test.application.defendant.retrieve.get;

import com.pol.promad.test.domain.defendant.Defendant;

public record DefendantOutput(String id, String name) {
    public static DefendantOutput from(final Defendant aDefendant) {
        return new DefendantOutput(aDefendant.getId().getValue(), aDefendant.getName());
    }
}
