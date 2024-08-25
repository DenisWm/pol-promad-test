package com.pol.promad.test.infrastructure.defendant.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefendantRepository extends JpaRepository<DefendantJpaEntity, String> {

    Page<DefendantJpaEntity> findAll(Specification<DefendantJpaEntity> whereClause, Pageable page);
}
