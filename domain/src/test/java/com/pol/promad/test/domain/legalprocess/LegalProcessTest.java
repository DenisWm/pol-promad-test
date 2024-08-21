package com.pol.promad.test.domain.legalprocess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LegalProcessTest {

    @Test
    public void givenAValidParams_whenCreateLegalProcess_thenShouldInstantiateLegalProcess() {
        // Given
        final var expectedNumber = "1234567-89.2023.8.26.0100";
        final var expectedStatus = "Em andamento";

        // When
        final var aLegalProcess = LegalProcess.newLegalProcess(expectedNumber, expectedStatus);

        // Then
        assert aLegalProcess != null;
        assert aLegalProcess.getNumber().equals(expectedNumber);
        assert aLegalProcess.getStatus().equals(expectedStatus);
    }

    @Test
    public void givenAnInvalidNullNumber_whenCreateLegalProcess_thenShouldIReceiveAnError() {
        // Given
        final String expectedNumber = null;
        final var expectedStatus = "Em andamento";
        final var exceptedErrorMessage = "Número do processo não pode ser nulo";
        final var expectedErrorCount = 1;

        // When
        final var anException = Assertions.assertThrows(DomainException.class, () -> LegalProcess.newLegalProcess(expectedNumber, expectedStatus));

        // Then
        Assertions.assertEquals(exceptedErrorMessage, anException.getErrors.get(0).message());
        Assertions.assertEquals(expectedErrorCount, anException.getErrors.size());
    }

    @Test
    public void givenAnInvalidEmptyNumber_whenCreateLegalProcess_thenShouldIReceiveAnError() {
        // Given
        final String expectedNumber = "";
        final var expectedStatus = "Em andamento";
        final var exceptedErrorMessage = "Número do processo não pode ser vazio";
        final var expectedErrorCount = 1;

        // When
        final var anException = Assertions.assertThrows(DomainException.class, () -> LegalProcess.newLegalProcess(expectedNumber, expectedStatus));

        // Then
        Assertions.assertEquals(exceptedErrorMessage, anException.getErrors.get(0).message());
        Assertions.assertEquals(expectedErrorCount, anException.getErrors.size());
    }
    @Test
    public void givenAnInvalidFormatNumber_whenCreateLegalProcess_thenShouldIReceiveAnError() {
        // Given
        final String expectedNumber = "123456";
        final var expectedStatus = "Em andamento";
        final var exceptedErrorMessage = "Número do processo não respeita o formato correto 'NNNNNNN-DD.AAAA.J.TR.OOOO'";
        final var expectedErrorCount = 1;

        // When
        final var anException = Assertions.assertThrows(DomainException.class, () -> LegalProcess.newLegalProcess(expectedNumber, expectedStatus));

        // Then
        Assertions.assertEquals(exceptedErrorMessage, anException.getErrors.get(0).message());
        Assertions.assertEquals(expectedErrorCount, anException.getErrors.size());
    }
}
