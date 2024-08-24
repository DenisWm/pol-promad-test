package com.pol.promad.test.infrastructure.legalprocess;

import com.pol.promad.test.domain.legalprocess.LegalProcess;
import com.pol.promad.test.domain.legalprocess.LegalProcessGateway;
import com.pol.promad.test.domain.legalprocess.LegalProcessID;
import com.pol.promad.test.domain.pagination.Pagination;
import com.pol.promad.test.domain.pagination.SearchQuery;
import com.pol.promad.test.infrastructure.legalprocess.persistence.LegalProcessJpaEntity;
import com.pol.promad.test.infrastructure.legalprocess.persistence.LegalProcessRepository;
import com.pol.promad.test.infrastructure.legalprocess.utils.SpecificationUtils;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
        final var aLegalProcessId = anId.getValue();
        if(this.repository.existsById(aLegalProcessId)) {
            repository.deleteById(aLegalProcessId);
        }
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
        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        final var where = Optional.ofNullable(aQuery.terms())
                .filter(str -> !str.isBlank())
                .map(this::assembleSpecification)
                .orElse(null);

        final var results = this.repository.findAll(Specification.where(where), page);
        return new Pagination<>(
                results.getNumber(),
                results.getSize(),
                results.getTotalElements(),
                results.map(LegalProcessJpaEntity::toAggregate).stream().toList()
        );
    }

    @Override
    public boolean existsByNumber(final String aNumber) {
        return repository.existsByNumber(aNumber);
    }

    private Specification<LegalProcessJpaEntity> assembleSpecification(final String terms) {
        return SpecificationUtils.like("status", terms);
    }
}
