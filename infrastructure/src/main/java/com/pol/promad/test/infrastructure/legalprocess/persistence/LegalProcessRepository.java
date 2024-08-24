package com.pol.promad.test.infrastructure.legalprocess.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface LegalProcessRepository extends JpaRepository<LegalProcessJpaEntity, String> {

    Page<LegalProcessJpaEntity> findAll(Specification<LegalProcessJpaEntity> whereClause, Pageable page);

}
