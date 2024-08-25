package com.pol.promad.test.application.defendant.retrieve.get;

import com.pol.promad.test.application.legalprocess.retrieve.get.LegalProcessOutput;
import com.pol.promad.test.application.utils.IDNotFoundUtils;
import com.pol.promad.test.domain.defendant.Defendant;
import com.pol.promad.test.domain.defendant.DefendantGateway;
import com.pol.promad.test.domain.defendant.DefendantID;
import com.pol.promad.test.domain.legalprocess.LegalProcess;
import com.pol.promad.test.domain.legalprocess.LegalProcessID;

import java.util.Objects;

public class DefaultGetDefendantByIdUseCase extends GetDefendantByIdUseCase {
    private final DefendantGateway defendantGateway;

    public DefaultGetDefendantByIdUseCase(final DefendantGateway defendantGateway) {
        this.defendantGateway = Objects.requireNonNull(defendantGateway);
    }

    @Override
    public DefendantOutput execute(final String id) {
        return this.defendantGateway.findById(DefendantID.from(id))
                .map(DefendantOutput::from)
                .orElseThrow(IDNotFoundUtils.notFound(DefendantID.from(id), Defendant.class));
    }
}
