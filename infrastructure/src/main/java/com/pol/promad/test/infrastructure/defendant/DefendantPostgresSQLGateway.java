package com.pol.promad.test.infrastructure.defendant;

import com.pol.promad.test.domain.defendant.Defendant;
import com.pol.promad.test.domain.defendant.DefendantGateway;
import com.pol.promad.test.domain.defendant.DefendantID;
import com.pol.promad.test.domain.pagination.Pagination;
import com.pol.promad.test.domain.pagination.SearchQuery;
import com.pol.promad.test.infrastructure.defendant.persistence.DefendantJpaEntity;
import com.pol.promad.test.infrastructure.defendant.persistence.DefendantRepository;
import com.pol.promad.test.infrastructure.legalprocess.utils.SpecificationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
@Component
public class DefendantPostgresSQLGateway implements DefendantGateway {

    private final DefendantRepository repository;

    public DefendantPostgresSQLGateway(final DefendantRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }
    @Override
    public Defendant create(final Defendant defendant) {
        return this.repository.save(DefendantJpaEntity.from(defendant)).toAggregate();
    }
    @Override
    public Optional<Defendant> findById(final DefendantID anId) {
        return this.repository.findById(anId.getValue()).map(DefendantJpaEntity::toAggregate);
    }
    @Override
    public Pagination<Defendant> findAll(final SearchQuery aQuery) {
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
                results.map(DefendantJpaEntity::toAggregate).stream().toList()
        );
    }
    private Specification<DefendantJpaEntity> assembleSpecification(final String terms) {
        return SpecificationUtils.like("name", terms);
    }
}
