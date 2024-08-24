package com.pol.promad.test.application.legalprocess.retrieve.list;

import com.pol.promad.test.application.legalprocess.UseCaseTest;
import com.pol.promad.test.domain.legalprocess.LegalProcess;
import com.pol.promad.test.domain.legalprocess.LegalProcessGateway;
import com.pol.promad.test.domain.legalprocess.LegalProcessID;
import com.pol.promad.test.domain.pagination.Pagination;
import com.pol.promad.test.domain.pagination.SearchQuery;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class ListLegalProcessUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultListLegalProcessUseCase useCase;
    @Mock
    private LegalProcessGateway gateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(gateway);
    }

    @Test
    public void givenAValidQuery_whenCallsListLegalProcess_shouldReturnListOfLegalProcess() {
        final var legalProcesses = List.of(
                LegalProcess.newLegalProcess(
                        "1234567-89.2023.8.26.0100",
                        "EM_ANDAMENTO"
                ),
                LegalProcess.newLegalProcess(
                        "1234567-89.2024.8.26.0100",
                        "FINALIZADO"
                )
        );
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "number";
        final var expectedDirection = "asc";

        final var aQuery = new SearchQuery(
                expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection
        );

        final var expectedPagination = new Pagination<>(expectedPage, expectedPerPage, legalProcesses.size(), legalProcesses);
        final var expectedItemsCount = 2;

        final var expectedResult = expectedPagination.map(LegalProcessListOutput::from);

        when(gateway.findAll(any())).thenReturn(expectedPagination);

        final var actualResult = useCase.execute(aQuery);

        assertEquals(expectedItemsCount, actualResult.items().size());
        assertEquals(expectedResult, actualResult);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(legalProcesses.size(), actualResult.total());
    }
    @Test
    public void givenAValidQueryWithNoResult_whenCallsListLegalProcess_shouldReturnAEmptyList() {
        final var legalProcesses = List.<LegalProcess>of(
        );
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "number";
        final var expectedDirection = "asc";

        final var aQuery = new SearchQuery(
                expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection
        );

        final var expectedItemsCount = 0;
        final var expectedPagination = new Pagination<>(expectedPage, expectedPerPage, legalProcesses.size(), legalProcesses);

        final var expectedResult = expectedPagination.map(LegalProcessListOutput::from);

        when(gateway.findAll(eq(aQuery))).thenReturn(expectedPagination);

        final var actualResult = useCase.execute(aQuery);

        assertEquals(expectedItemsCount, actualResult.items().size());
        assertEquals(expectedResult, actualResult);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(legalProcesses.size(), actualResult.total());
    }

    @Test
    public void givenAValidQuery_whenGatewayThrowsException_shouldReturnException() {
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "number";
        final var expectedDirection = "asc";
        final var gatewayError = "Gateway error";

        final var aQuery = new SearchQuery(
                expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection
        );


        when(gateway.findAll(eq(aQuery))).thenThrow(new IllegalStateException(gatewayError));

        final var actualException = assertThrows(
                IllegalStateException.class,
                () -> useCase.execute(aQuery)
        );

        assertEquals(gatewayError, actualException.getMessage() );
    }
}
