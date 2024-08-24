package com.pol.promad.test.application.legalprocess.retrieve.list;

import com.pol.promad.test.IntegrationTest;
import com.pol.promad.test.domain.legalprocess.LegalProcess;
import com.pol.promad.test.domain.pagination.SearchQuery;
import com.pol.promad.test.infrastructure.legalprocess.persistence.LegalProcessJpaEntity;
import com.pol.promad.test.infrastructure.legalprocess.persistence.LegalProcessRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

@IntegrationTest
public class ListLegalProcessUseCaseIT {

    @Autowired
    private ListLegalProcessUseCase useCase;

    @Autowired
    private LegalProcessRepository legalProcessRepository;

    @BeforeEach
    void mockUp() {
        final var categories = Stream.of(
                        LegalProcess.newLegalProcess("1234567-89.2023.8.26.0100", "EM_ANDAMENTO"),
                        LegalProcess.newLegalProcess("1234567-89.2024.8.26.0101", "FINALIZADO"),
                        LegalProcess.newLegalProcess("1234567-89.2025.8.26.0102", "SUSPENSO"),
                        LegalProcess.newLegalProcess("1234567-89.2026.8.26.0103", "ARQUIVADO"),
                        LegalProcess.newLegalProcess("1234567-89.2027.8.26.0104", "FINALIZADO")
                )
                .map(LegalProcessJpaEntity::from)
                .toList();

        legalProcessRepository.saveAllAndFlush(categories);
    }
    @Test
    public void givenAValidTerm_whenTermDoesntMatchesPrePersisted_shouldReturnEmptyPage() {
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "ji1j3i 1j3i1oj";
        final var expectedSort = "status";
        final var expectedDirection = "asc";
        final var expectedItemsCount = 0;
        final var expectedTotal = 0;

        final var aQuery =
                new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
    }


}
