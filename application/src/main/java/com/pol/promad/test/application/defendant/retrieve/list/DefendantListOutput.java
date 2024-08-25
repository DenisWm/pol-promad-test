package com.pol.promad.test.application.defendant.retrieve.list;

import com.pol.promad.test.domain.defendant.Defendant;

public record DefendantListOutput(String id, String name) {
    public static DefendantListOutput from(final Defendant aDefendant) {
        return new DefendantListOutput(aDefendant.getId().getValue(), aDefendant.getName());
    }
}
