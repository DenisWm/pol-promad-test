package com.pol.promad.test.infrastructure.legalprocess;

import com.pol.promad.test.domain.legalprocess.LegalProcess;
import com.pol.promad.test.domain.legalprocess.LegalProcessGateway;
import com.pol.promad.test.domain.legalprocess.LegalProcessID;
import com.pol.promad.test.domain.pagination.Pagination;
import com.pol.promad.test.domain.pagination.SearchQuery;
import com.pol.promad.test.infrastructure.legalprocess.persistence.LegalProcessJpaEntity;
import com.pol.promad.test.infrastructure.legalprocess.persistence.LegalProcessRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
@Component
public class LegalProcessPostgresSQLGateway implements LegalProcessGateway {

    private final LegalProcessRepository repository;

    public LegalProcessPostgresSQLGateway(final LegalProcessRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public LegalProcess create(final LegalProcess aLegalProcess) {
        return save(aLegalProcess);
    }

    @Override
    public void deleteById(final LegalProcessID anId) {

    }
    @Override
    public Optional<LegalProcess> findById(final LegalProcessID anId) {
        return this.repository.findById(anId.getValue()).map(LegalProcessJpaEntity::toAggregate);
    }

    @Override
    public LegalProcess update(final LegalProcess aLegalProcess) {
        return save(aLegalProcess);
    }

    private LegalProcess save(LegalProcess aLegalProcess) {
        return repository.save(LegalProcessJpaEntity.from(aLegalProcess)).toAggregate();
    }

    @Override
    public Pagination<LegalProcess> findAll(final SearchQuery aQuery) {
        return null;
    }

    @Override
    public boolean existsByNumber(final String aNumber) {
        return repository.existsByNumber(aNumber);
    }
}
