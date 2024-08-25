package com.pol.promad.test.infrastructure.defendant.models;

import com.pol.promad.test.domain.defendant.Defendant;

public record DefendantListResponse(
        String id,
        String name
) {
    public static DefendantListResponse from(final Defendant aDefendant) {
        return new DefendantListResponse(aDefendant.getId().getValue(), aDefendant.getName()) ;
    }
}
