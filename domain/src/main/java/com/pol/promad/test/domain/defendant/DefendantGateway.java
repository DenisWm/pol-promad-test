package com.pol.promad.test.domain.defendant;

import com.pol.promad.test.domain.legalprocess.LegalProcess;
import com.pol.promad.test.domain.legalprocess.LegalProcessID;
import com.pol.promad.test.domain.pagination.Pagination;
import com.pol.promad.test.domain.pagination.SearchQuery;

import java.util.Optional;

public interface DefendantGateway {
    Defendant create(Defendant defendant);
    Optional<Defendant> findById(DefendantID anId);
    Pagination<Defendant> findAll(SearchQuery aQuery);

}
