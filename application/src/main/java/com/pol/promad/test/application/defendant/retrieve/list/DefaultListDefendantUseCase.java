package com.pol.promad.test.application.defendant.retrieve.list;

import com.pol.promad.test.application.defendant.retrieve.get.DefendantOutput;
import com.pol.promad.test.application.legalprocess.retrieve.list.LegalProcessListOutput;
import com.pol.promad.test.domain.defendant.DefendantGateway;
import com.pol.promad.test.domain.pagination.Pagination;
import com.pol.promad.test.domain.pagination.SearchQuery;

import java.util.Objects;

public class DefaultListDefendantUseCase extends ListDefendantUseCase{

    private final DefendantGateway defendantGateway;

    public DefaultListDefendantUseCase(final DefendantGateway defendantGateway) {
        this.defendantGateway = Objects.requireNonNull(defendantGateway);
    }

    @Override
    public Pagination<DefendantListOutput> execute(final SearchQuery aQuery) {
        return this.defendantGateway.findAll(aQuery)
                .map(DefendantListOutput::from);
    }
}
