package com.pol.promad.test.infrastructure.defendant.models;

import com.pol.promad.test.domain.defendant.Defendant;

public record DefendantResponse(
        String id,
        String name
) {
    public static DefendantResponse from(final Defendant aDefendant) {
        return new DefendantResponse(aDefendant.getId().getValue(), aDefendant.getName()) ;
    }
}
