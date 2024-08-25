package com.pol.promad.test.application.legalprocess.retrieve.list;

import com.pol.promad.test.IntegrationTest;
import com.pol.promad.test.domain.legalprocess.LegalProcess;
import com.pol.promad.test.domain.pagination.SearchQuery;
import com.pol.promad.test.infrastructure.legalprocess.persistence.LegalProcessJpaEntity;
import com.pol.promad.test.infrastructure.legalprocess.persistence.LegalProcessRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
                        LegalProcess.newLegalProcess("1234567-89.2026.8.26.0103", "ARQUIVADO")
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
    @ParameterizedTest
    @CsvSource({
            "m_a,0,10,1,1,EM_ANDAMENTO",
            "NAL,0,10,1,1,FINALIZADO",
            "vad,0,10,1,1,ARQUIVADO",
            "pen,0,10,1,1,SUSPENSO"
    })
    public void givenAValidTerm_whenCallsListLegalProcesses_shouldReturnCategoriesFiltered(
            final String expectedTerms,
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedTotal,
            final String expectedStatus
    ) {
        final var expectedSort = "status";
        final var expectedDirection = "asc";

        final var aQuery =
                new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedStatus, actualResult.items().get(0).status());
    }

    @ParameterizedTest
    @CsvSource({
            "status,asc,0,10,4,4,ARQUIVADO",
            "status,desc,0,10,4,4,SUSPENSO",
            "number,asc,0,10,4,4,EM_ANDAMENTO",
            "number,desc,0,10,4,4,ARQUIVADO",
    })
    public void givenAValidSortAndDirection_whenCallsListLegalProcesses_thenShouldReturnCategoriesOrdered(
            final String expectedSort,
            final String expectedDirection,
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedTotal,
            final String expectedStatus
    ) {
        final var expectedTerms = "";

        final var aQuery =
                new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedStatus, actualResult.items().get(0).status());
    }

    @ParameterizedTest
    @CsvSource({
            "0,1,1,4,1234567-89.2023.8.26.0100",
            "1,1,1,4,1234567-89.2024.8.26.0101",
            "2,1,1,4,1234567-89.2025.8.26.0102",
            "3,1,1,4,1234567-89.2026.8.26.0103",
    })
    public void givenAValidPage_whenCallsListLegalProcesses_shouldReturnCategoriesPaginated(
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedTotal,
            final String expectedNumbers
    ) {
        final var expectedSort = "number";
        final var expectedDirection = "asc";
        final var expectedTerms = "";

        final var aQuery =
                new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());

        int index = 0;
        for (final String expectedNumber : expectedNumbers.split(";")) {
            final String actualNumber = actualResult.items().get(index).number();
            Assertions.assertEquals(expectedNumber, actualNumber);
            index++;
        }
    }

}
