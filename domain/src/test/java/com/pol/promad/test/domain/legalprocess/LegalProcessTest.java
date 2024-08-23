package com.pol.promad.test.domain.legalprocess;

import com.pol.promad.test.domain.exceptions.DomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LegalProcessTest {

    @Test
    public void givenAValidParams_whenCreateLegalProcess_thenShouldInstantiateLegalProcess() {
        // Given
        final var expectedNumber = "1234567-89.2023.8.26.0100";
        final var expectedStatus = "EM_ANDAMENTO";

        // When
        final var aLegalProcess = LegalProcess.newLegalProcess(expectedNumber, expectedStatus);

        // Then
        assertNotNull(aLegalProcess);
        assertEquals(expectedNumber, aLegalProcess.getNumber());
        assertEquals(expectedStatus, aLegalProcess.getStatus().getValue());
        assertNull(aLegalProcess.getPendingStatus());
    }

    @Test
    public void givenAnInvalidNullNumber_whenCreateLegalProcess_thenShouldIReceiveAnError() {
        // Given
        final String expectedNumber = null;
        final var expectedStatus = "EM_ANDAMENTO";
        final var exceptedErrorMessage = "O número do processo não pode ser nulo";
        final var expectedErrorCount = 1;

        // When
        final var anException = assertThrows(
                DomainException.class, () -> LegalProcess.newLegalProcess(expectedNumber, expectedStatus));

        // Then
        assertEquals(exceptedErrorMessage, anException.getErrors().get(0).message());
        assertEquals(expectedErrorCount, anException.getErrors().size());
    }

    @Test
    public void givenAnInvalidEmptyNumber_whenCreateLegalProcess_thenShouldIReceiveAnError() {
        // Given
        final String expectedNumber = "";
        final var expectedStatus = "EM_ANDAMENTO";
        final var exceptedErrorMessage = "O número do processo não pode ser vazio";
        final var expectedErrorCount = 1;

        // When
        final var anException = assertThrows(DomainException.class, () -> LegalProcess.newLegalProcess(expectedNumber, expectedStatus));

        // Then
        assertEquals(exceptedErrorMessage, anException.getErrors().get(0).message());
        assertEquals(expectedErrorCount, anException.getErrors().size());
    }
    @Test
    public void givenAnInvalidFormatNumber_whenCreateLegalProcess_thenShouldIReceiveAnError() {
        // Given
        final String expectedNumber = "123456";
        final var expectedStatus = "EM_ANDAMENTO";
        final var exceptedErrorMessage = "O número do processo não respeita o formato correto 'NNNNNNN-DD.AAAA.J.TR.OOOO'";
        final var expectedErrorCount = 1;

        // When
        final var anException = assertThrows(DomainException.class, () -> LegalProcess.newLegalProcess(expectedNumber, expectedStatus));

        // Then
        assertEquals(exceptedErrorMessage, anException.getErrors().get(0).message());
        assertEquals(expectedErrorCount, anException.getErrors().size());
    }

    @Test
    public void givenAnInvalidFormatWithMoreThan20Characters_whenCreateLegalProcess_thenShouldIReceiveAnError() {
        // Given
        final String expectedNumber = "1234567-89.2023.8.26.0100@#!@$!@#23123";
        final var expectedStatus = "EM_ANDAMENTO";
        final var exceptedErrorMessage = "O número do processo não pode ter mais de 25 caracteres";
        final var expectedErrorCount = 1;

        // When
        final var anException = assertThrows(DomainException.class, () -> LegalProcess.newLegalProcess(expectedNumber, expectedStatus));

        // Then
        assertEquals(exceptedErrorMessage, anException.getErrors().get(0).message());
        assertEquals(expectedErrorCount, anException.getErrors().size());
    }

    @Test
    public void givenAnInvalidNullStatus_whenCreateLegalProcess_thenShouldIReceiveAnError() {
        // Given
        final var expectedNumber = "1234567-89.2023.8.26.0100";
        final String expectedStatus = null;
        final var exceptedErrorMessage = "O status do processo não pode ser nulo";
        final var expectedErrorCount = 1;

        // When
        final var anException = assertThrows(
                DomainException.class, () -> LegalProcess.newLegalProcess(expectedNumber, expectedStatus));

        // Then
        assertEquals(exceptedErrorMessage, anException.getErrors().get(0).message());
        assertEquals(expectedErrorCount, anException.getErrors().size());
    }

    @Test
    public void givenAnInvalidEmptyStatus_whenCreateLegalProcess_thenShouldIReceiveAnError() {
        // Given
        final var expectedNumber = "1234567-89.2023.8.26.0100";
        final String expectedStatus = "";
        final var exceptedErrorMessage = "O status do processo não pode ser vazio";
        final var expectedErrorCount = 1;

        // When
        final var anException = assertThrows(
                DomainException.class, () -> LegalProcess.newLegalProcess(expectedNumber, expectedStatus));

        // Then
        assertEquals(exceptedErrorMessage, anException.getErrors().get(0).message());
        assertEquals(expectedErrorCount, anException.getErrors().size());
    }

    @Test
    public void givenAnInvalidStatus_whenCreateLegalProcess_thenShouldIReceiveAnError() {
        // Given
        final var expectedNumber = "1234567-89.2023.8.26.0100";
        final var expectedStatus = "INVALID_STATUS";
        final var exceptedErrorMessage = "O status do processo não é válido";
        final var expectedErrorCount = 1;

        // When
        final var anException = assertThrows(
                DomainException.class, () -> LegalProcess.newLegalProcess(expectedNumber, expectedStatus));

        // Then
        assertEquals(exceptedErrorMessage, anException.getErrors().get(0).message());
        assertEquals(expectedErrorCount, anException.getErrors().size());
    }

    @Test
    public void givenAnInvalidNullStatus_whenUpdateLegalProcess_thenShouldIReceiveAnError() {
        // Given
        final var legalProcess = LegalProcess.newLegalProcess("1234567-89.2023.8.26.0100", "EM_ANDAMENTO");
        final String newStatus = null;
        final var expectedErrorMessage = "O novo status do processo não pode ser nulo";
        final var expectedErrorCount = 1;

        // When
        final var anException = assertThrows(
                DomainException.class, () -> legalProcess.update(newStatus));

        // Then
        assertEquals(expectedErrorMessage, anException.getErrors().get(0).message());
        assertEquals(expectedErrorCount, anException.getErrors().size());
    }

    @Test
    public void givenAnInvalidEmptyStatus_whenUpdateLegalProcess_thenShouldIReceiveAnError() {
        // Given
        final var legalProcess = LegalProcess.newLegalProcess("1234567-89.2023.8.26.0100", "EM_ANDAMENTO");
        final String newStatus = "";
        final var expectedErrorMessage = "O novo status do processo não pode ser vazio";
        final var expectedErrorCount = 1;

        // When
        final var anException = assertThrows(
                DomainException.class, () -> legalProcess.update(newStatus));

        // Then
        assertEquals(expectedErrorMessage, anException.getErrors().get(0).message());
        assertEquals(expectedErrorCount, anException.getErrors().size());
    }

    @Test
    public void givenAnInvalidStatus_whenUpdateLegalProcess_thenShouldIReceiveAnError() {
        // Given
        final var legalProcess = LegalProcess.newLegalProcess("1234567-89.2023.8.26.0100", "EM_ANDAMENTO");
        final var newStatus = "INVALID_STATUS";
        final var expectedErrorMessage = "O novo status do processo não é válido";
        final var expectedErrorCount = 1;

        // When
        final var anException = assertThrows(
                DomainException.class, () -> legalProcess.update(newStatus));

        // Then
        assertEquals(expectedErrorMessage, anException.getErrors().get(0).message());
        assertEquals(expectedErrorCount, anException.getErrors().size());
    }

    @Test
    public void givenAValidStatus_whenUpdateLegalProcess_thenShouldUpdateStatus() {
        // Given
        final var legalProcess = LegalProcess.newLegalProcess("1234567-89.2023.8.26.0100", "EM_ANDAMENTO");
        final var newStatus = "FINALIZADO";

        // When
        legalProcess.update(newStatus);

        // Then
        assertEquals(newStatus, legalProcess.getStatus().getValue());
        assertNull(legalProcess.getPendingStatus());
    }

    @Test
    public void givenAValidNumber_whenCreateLegalProcess_thenShouldInstantiateLegalProcess() {
        // Given
        final var expectedNumber = "1234567-89.2023.8.26.0100";
        final var expectedStatus = "EM_ANDAMENTO";

        // When
        final var aLegalProcess = LegalProcess.newLegalProcess(expectedNumber, expectedStatus);

        // Then
        assertNotNull(aLegalProcess);
        assertEquals(expectedNumber, aLegalProcess.getNumber());
        assertEquals(expectedStatus, aLegalProcess.getStatus().getValue());
        assertNull(aLegalProcess.getPendingStatus());
    }

    @Test
    public void givenAnInvalidStatusTransition_whenUpdateLegalProcess_thenShouldIReceiveAnError() {
        // Given
        final var legalProcess = LegalProcess.newLegalProcess("1234567-89.2023.8.26.0100", "FINALIZADO");
        final var newStatus = "EM_ANDAMENTO";
        final var expectedErrorMessage = "Transição de status inválida de FINALIZADO para EM_ANDAMENTO";
        final var expectedErrorCount = 1;

        // When
        final var anException = assertThrows(
                DomainException.class, () -> legalProcess.update(newStatus));

        // Then
        assertEquals(expectedErrorMessage, anException.getErrors().get(0).message());
        assertEquals(expectedErrorCount, anException.getErrors().size());
    }
}
