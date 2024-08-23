package com.pol.promad.test.domain.legalprocess;

import com.pol.promad.test.domain.pagination.Pagination;
import com.pol.promad.test.domain.pagination.SearchQuery;

import java.util.Optional;

public interface LegalProcessGateway {

    LegalProcess create(LegalProcess genre);
    void deleteById(LegalProcessID anId);
    Optional<LegalProcess> findById(LegalProcessID anId);
    LegalProcess update(LegalProcess genre);
    Pagination<LegalProcess> findAll(SearchQuery aQuery);
}
