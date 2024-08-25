package com.pol.promad.test.infrastructure.defendant.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DefendantRepository extends JpaRepository<DefendantJpaEntity, String> {

    Page<DefendantJpaEntity> findAll(Specification<DefendantJpaEntity> whereClause, Pageable page);

    @Query(value = "select c.id from Defendant c where c.id in :ids")
    List<String> existsByIds(@Param("ids") List<String> ids);
}
