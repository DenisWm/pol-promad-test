package com.pol.promad.test.domain.legalprocess;

import com.pol.promad.test.domain.pagination.Pagination;
import com.pol.promad.test.domain.pagination.SearchQuery;

import java.util.Optional;

public interface LegalProcessGateway {

    LegalProcess create(LegalProcess aLegalProcess);
    void deleteById(LegalProcessID anId);
    Optional<LegalProcess> findById(LegalProcessID anId);
    LegalProcess update(LegalProcess aLegalProcess);
    Pagination<LegalProcess> findAll(SearchQuery aQuery);
    boolean existsByNumber(String aNumber);
}
