package com.pol.promad.test.infrastructure.defendant.presenters;

import com.pol.promad.test.application.defendant.retrieve.get.DefendantOutput;
import com.pol.promad.test.application.defendant.retrieve.list.DefendantListOutput;
import com.pol.promad.test.infrastructure.defendant.models.DefendantListResponse;
import com.pol.promad.test.infrastructure.defendant.models.DefendantResponse;

public interface DefendantApiPresenter {

    static DefendantResponse present(final DefendantOutput output) {
        return new DefendantResponse(
                output.id(),
                output.name()
        );
    }

    static DefendantListResponse present(final DefendantListOutput output) {
        return new DefendantListResponse(
                output.id(),
                output.name()
        );
    }

}
