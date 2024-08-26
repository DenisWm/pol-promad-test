package com.pol.promad.test.application.legalprocess.create;

import com.pol.promad.test.IntegrationTest;
import com.pol.promad.test.domain.exceptions.NotificationException;
import com.pol.promad.test.domain.legalprocess.LegalProcess;
import com.pol.promad.test.infrastructure.legalprocess.persistence.LegalProcessJpaEntity;
import com.pol.promad.test.infrastructure.legalprocess.persistence.LegalProcessRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@IntegrationTest
public class CreateLegalProcessUseCaseIT {

    @Autowired
    private CreateLegalProcessUseCase useCase;

    @Autowired
    private LegalProcessRepository legalProcessRepository;


    @Test
    public void givenValidParam_whenCallsExecute_thenShouldCreateLegalProcess() {
        // given
        final var number = "1234567-89.2023.8.26.0100";
        final var status = "EM_ANDAMENTO";
        final var defendants = List.<String>of();

        assertEquals(0, legalProcessRepository.count());

        final var aCommand = CreateLegalProcessCommand.with(number, status, defendants);
        // when

        final var actualOutput = useCase.execute(aCommand);

        // then
        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        assertEquals(1, legalProcessRepository.count());

        final var actualLegalProcess = legalProcessRepository.findById(actualOutput.id()).get();

        assertEquals(number, actualLegalProcess.getNumber());
        assertEquals(status, actualLegalProcess.getStatus());
    }
    @Test
    public void givenInvalidParam_whenCallsExecute_thenShouldThrowValidationException() {
        // given
        final var number = "invalid-number";
        final var defendants = List.<String>of();
        final var status = "EM_ANDAMENTO";
        final var expectedErrorMessage = "O número do processo não respeita o formato correto 'NNNNNNN-DD.AAAA.J.TR.OOOO'";

        assertEquals(0, legalProcessRepository.count());

        final var aCommand = CreateLegalProcessCommand.with(number, status, defendants);

        // when & then
        final var exception =  assertThrows(NotificationException.class, () -> useCase.execute(aCommand));

        assertEquals(0, legalProcessRepository.count());

        Assertions.assertEquals(expectedErrorMessage, exception.getErrors().getFirst().message());
    }

    @Test
    public void givenExistingLegalProcess_whenCallsExecute_thenShouldNotCreateAndReturnError() {
        // given
        final var number = "1234567-89.2023.8.26.0100";
        final var status = "EM_ANDAMENTO";
        final var defendants = List.<String>of();
        final var aLegalProcess = LegalProcess.newLegalProcess(number, status);
        final var expectedErrorMessageNotification = "O aggregado LegalProcess não pode ser criado";
        final var expectedErrorMessage = "O processo com número 1234567-89.2023.8.26.0100 já existe no sistema";

        legalProcessRepository.save(LegalProcessJpaEntity.from(aLegalProcess));

        assertEquals(1, legalProcessRepository.count());

        final var aCommand = CreateLegalProcessCommand.with(number, status, defendants);

        // when
        final var exception = assertThrows(NotificationException.class, () -> useCase.execute(aCommand));

        // then
        Assertions.assertEquals(expectedErrorMessageNotification, exception.getMessage());
        Assertions.assertEquals(expectedErrorMessage, exception.getErrors().getFirst().message());
        assertEquals(1, legalProcessRepository.count());
    }

    @Test
    public void givenValidParamWithDifferentStatus_whenCallsExecute_thenShouldCreateLegalProcessWithGivenStatus() {
        // given
        final var number = "1234567-89.2023.8.26.0100";
        final var status = "FINALIZADO";
        final var defendants = List.<String>of();

        assertEquals(0, legalProcessRepository.count());

        final var aCommand = CreateLegalProcessCommand.with(number, status, defendants);

        // when
        final var actualOutput = useCase.execute(aCommand);

        // then
        assertEquals(1, legalProcessRepository.count());
        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        final var actualLegalProcess = legalProcessRepository.findById(actualOutput.id()).get();

        assertEquals(number, actualLegalProcess.getNumber());
        assertEquals(status, actualLegalProcess.getStatus());
    }
}
