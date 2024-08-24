package com.pol.promad.test.infrastructure.legalprocess.presenters;

import com.pol.promad.test.application.legalprocess.retrieve.get.LegalProcessOutput;
import com.pol.promad.test.application.legalprocess.retrieve.list.LegalProcessListOutput;
import com.pol.promad.test.infrastructure.legalprocess.models.LegalProcessListResponse;
import com.pol.promad.test.infrastructure.legalprocess.models.LegalProcessResponse;

public interface LegalProcessApiPresenter {

    static LegalProcessResponse present(final LegalProcessOutput output) {
        return new LegalProcessResponse(
                output.id(),
                output.number(),
                output.status()
        );
    }

    static LegalProcessListResponse present(final LegalProcessListOutput output) {
        return new LegalProcessListResponse(
                output.id(),
                output.number(),
                output.status()
        );
    }
}
