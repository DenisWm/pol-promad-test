package com.pol.promad.test.application.legalprocess.retrieve.list;

import com.pol.promad.test.domain.legalprocess.LegalProcessGateway;
import com.pol.promad.test.domain.pagination.Pagination;
import com.pol.promad.test.domain.pagination.SearchQuery;

import java.util.Objects;

public class DefaultListLegalProcessUseCase extends ListLegaProcessUseCase {

    private final LegalProcessGateway legalProcessGateway;

    public DefaultListLegalProcessUseCase(final LegalProcessGateway legalProcessGateway) {
        this.legalProcessGateway = Objects.requireNonNull(legalProcessGateway);
    }
    @Override
    public Pagination<LegalProcessListOutput> execute(final SearchQuery aQuery) {
        return this.legalProcessGateway.findAll(aQuery)
                .map(LegalProcessListOutput::from);
    }
}
