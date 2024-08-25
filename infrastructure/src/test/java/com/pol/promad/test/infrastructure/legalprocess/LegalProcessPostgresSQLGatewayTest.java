package com.pol.promad.test.infrastructure.legalprocess;

import com.pol.promad.test.PostgresSQLGatewayTest;
import com.pol.promad.test.domain.legalprocess.LegalProcess;
import com.pol.promad.test.domain.legalprocess.LegalProcessID;
import com.pol.promad.test.domain.pagination.SearchQuery;
import com.pol.promad.test.infrastructure.legalprocess.persistence.LegalProcessJpaEntity;
import com.pol.promad.test.infrastructure.legalprocess.persistence.LegalProcessRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

@PostgresSQLGatewayTest
public class LegalProcessPostgresSQLGatewayTest {

    @Autowired
    private LegalProcessPostgresSQLGateway legalProcessGateway;

    @Autowired
    private LegalProcessRepository legalProcessRepository;

    @Test
    public void givenAValidLegalProcess_whenCallsCreate_shouldReturnANewLegalProcess() {
        final var number = "1234567-89.2023.8.26.0100";
        final var status = "EM_ANDAMENTO";
        final var aLegalProcess = LegalProcess.newLegalProcess(
                number,
                status
        );
        assertEquals(0, legalProcessRepository.count());
        final var actualLegalProcess = legalProcessGateway.create(aLegalProcess);

        assertEquals(1, legalProcessRepository.count());

        assertEquals(aLegalProcess.getId().getValue(), actualLegalProcess.getId().getValue());
        assertEquals(aLegalProcess.getNumber(), actualLegalProcess.getNumber());
        assertEquals(aLegalProcess.getStatus().getValue(), actualLegalProcess.getStatus().getValue());

        final var expectedLegalProcess = legalProcessRepository.findById(actualLegalProcess.getId().getValue()).get();

        assertEquals(expectedLegalProcess.getId(), actualLegalProcess.getId().getValue());
        assertEquals(expectedLegalProcess.getNumber(), actualLegalProcess.getNumber());
        assertEquals(expectedLegalProcess.getStatus(), actualLegalProcess.getStatus().getValue());
    }

    @Test
    public void givenAValidLegalProcess_whenCallsUpdate_shouldReturnAUpdatedLegalProcess() {
        final var number = "1234567-89.2023.8.26.0100";
        final var status = "EM_ANDAMENTO";
        final var aLegalProcess = LegalProcess.newLegalProcess(
                number,
                "SUSPENSO"
        );
        assertEquals(0, legalProcessRepository.count());

        legalProcessRepository.saveAndFlush(LegalProcessJpaEntity.from(aLegalProcess));

        assertEquals(1, legalProcessRepository.count());

        final var actualInvalidEntity = legalProcessRepository.findById(aLegalProcess.getId().getValue()).get();

        assertEquals(actualInvalidEntity.getId(), aLegalProcess.getId().getValue());
        assertEquals(actualInvalidEntity.getNumber(), aLegalProcess.getNumber());
        assertEquals(actualInvalidEntity.getStatus(), "SUSPENSO");

        final var actualLegalProcess = legalProcessGateway.update(aLegalProcess);

        assertEquals(1, legalProcessRepository.count());

        assertEquals(aLegalProcess.getId().getValue(), actualLegalProcess.getId().getValue());
        assertEquals(aLegalProcess.getNumber(), actualLegalProcess.getNumber());
        assertEquals(aLegalProcess.getStatus().getValue(), actualLegalProcess.getStatus().getValue());

        final var expectedLegalProcess = legalProcessRepository.findById(actualLegalProcess.getId().getValue()).get();

        assertEquals(expectedLegalProcess.getId(), actualLegalProcess.getId().getValue());
        assertEquals(expectedLegalProcess.getNumber(), actualLegalProcess.getNumber());
        assertEquals(expectedLegalProcess.getStatus(), actualLegalProcess.getStatus().getValue());
    }

    @Test
    public void givenAPrePersistedLegalProcessAndValidLegalProcessId_whenTryToDeleteIt_shouldDeleteLegalProcess () {
        final var number = "1234567-89.2023.8.26.0100";
        final var status = "EM_ANDAMENTO";
        final var aLegalProcess = LegalProcess.newLegalProcess(
                number,
                status
        );
        assertEquals(0, legalProcessRepository.count());

        legalProcessRepository.saveAndFlush(LegalProcessJpaEntity.from(aLegalProcess));

        assertEquals(1, legalProcessRepository.count());

        final var actualLegalProcess = legalProcessRepository.findById(aLegalProcess.getId().getValue()).get();

        legalProcessGateway.deleteById(aLegalProcess.getId());

        assertEquals(0, legalProcessRepository.count());
    }

    @Test
    public void givenAPrePersistedLegalProcessAndValidLegalProcessId_whenCallsFindById_shouldReturnLegalProcess() {
        final var number = "1234567-89.2023.8.26.0100";
        final var status = "EM_ANDAMENTO";
        final var aLegalProcess = LegalProcess.newLegalProcess(
                number,
                status
        );
        assertEquals(0, legalProcessRepository.count());

        legalProcessRepository.saveAndFlush(LegalProcessJpaEntity.from(aLegalProcess));

        assertEquals(1, legalProcessRepository.count());

        final var actualLegalProcess = legalProcessRepository.findById(aLegalProcess.getId().getValue()).get();

        final var expectedLegalProcess = legalProcessGateway.findById(aLegalProcess.getId()).get();

        assertEquals(expectedLegalProcess.getId().getValue(), actualLegalProcess.getId());
        assertEquals(expectedLegalProcess.getNumber(), actualLegalProcess.getNumber());
        assertEquals(expectedLegalProcess.getStatus().getValue(), actualLegalProcess.getStatus());
    }
    @Test
    public void givenAValidLegalProcessIdNotStored_whenCallsFindById_shouldReturnEmpty() {
        Assertions.assertEquals(0, legalProcessRepository.count());

        final var actualCategory = legalProcessGateway.findById(LegalProcessID.from("empty"));

        Assertions.assertTrue(actualCategory.isEmpty());
    }

    @Test
    public void givenInvalidLegalProcessId_whenTryToDeleteIt_shouldDeleteLegalProcess () {
        Assertions.assertEquals(0, legalProcessRepository.count());

        legalProcessGateway.deleteById(LegalProcessID.from("invalid"));

        Assertions.assertEquals(0, legalProcessRepository.count());
    }

    @Test
    public void givenEmptyLegalProcessTable_whenCallsFindAll_shouldReturnEmptyPage() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 0;

        final var aQuery = new SearchQuery(0, 1, "", "number", "asc");

        final var actualResult = legalProcessGateway.findAll(aQuery);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(0, actualResult.items().size());
    }

    @Test
    public void givenPrePersistedLegalProcesses_whenCallsFindAll_shouldReturnPaginated() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 3;

        final var one = LegalProcess.newLegalProcess(
                "1234567-89.2023.8.26.0100", "EM_ANDAMENTO"
        );

        final var two = LegalProcess.newLegalProcess(
                "1234567-89.2023.8.26.0101", "EM_ANDAMENTO"
        );

        final var three = LegalProcess.newLegalProcess(
                "1234567-89.2023.8.26.0103", "EM_ANDAMENTO"
        );

        Assertions.assertEquals(0, legalProcessRepository.count());

        legalProcessRepository.saveAllAndFlush(List.of(
                LegalProcessJpaEntity.from(one),
                LegalProcessJpaEntity.from(two),
                LegalProcessJpaEntity.from(three)
        ));

        Assertions.assertEquals(3, legalProcessRepository.count());

        final var aQuery = new SearchQuery(0, 1, "", "number", "asc");

        final var actualResult = legalProcessGateway.findAll(aQuery);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(one.getId(), actualResult.items().get(0).getId());
    }
}
