package com.pol.promad.test.application.legalprocess.create;

import com.pol.promad.test.application.legalprocess.UseCaseTest;
import com.pol.promad.test.domain.defendant.DefendantGateway;
import com.pol.promad.test.domain.defendant.DefendantID;
import com.pol.promad.test.domain.exceptions.NotificationException;
import com.pol.promad.test.domain.legalprocess.LegalProcessGateway;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateLegalProcessUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreateLegalProcessUseCase usecase;
    @Mock
    private LegalProcessGateway legalProcessGateway;
    @Mock
    private DefendantGateway defendantGateway;
    @Override
    protected List<Object> getMocks() {
        return List.of(legalProcessGateway);
    }

    @Test
    public void givenValidParam_whenCallsExecute_thenShouldCreateLegalProcess() {
        // given
        final var number = "1234567-89.2023.8.26.0100";
        final var status = "EM_ANDAMENTO";
        final var defendants = List.<String>of();

        final var aCommand = CreateLegalProcessCommand.with(number, status, defendants);
        // when
        when(legalProcessGateway.create(any())).thenAnswer(returnsFirstArg());
        when(legalProcessGateway.existsByNumber(any())).thenReturn(false);

        final var actualOutput = usecase.execute(aCommand);

        // then
        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());


        verify(legalProcessGateway, times(1)).existsByNumber(number);

        verify(legalProcessGateway, times(1)).create(argThat(aLegalProcess ->
                Objects.equals(number, aLegalProcess.getNumber()) &&
                        Objects.equals(status, aLegalProcess.getStatus().getValue())
        ));
    }

    @Test
    public void givenInvalidParam_whenCallsExecute_thenShouldThrowValidationException() {
        // given
        final var number = "invalid-number";
        final var status = "EM_ANDAMENTO";
        final var defendants = List.<String>of();
        final var expectedErrorMessage = "O número do processo não respeita o formato correto 'NNNNNNN-DD.AAAA.J.TR.OOOO'";

        final var aCommand = CreateLegalProcessCommand.with(number, status, defendants);

        // when & then
        final var exception =  assertThrows(NotificationException.class, () -> usecase.execute(aCommand));
        assertEquals(expectedErrorMessage, exception.getErrors().getFirst().message());
    }

    @Test
    public void givenExistingLegalProcess_whenCallsExecute_thenShouldNotCreateAndReturnError() {
        // given
        final var number = "1234567-89.2023.8.26.0100";
        final var status = "EM_ANDAMENTO";
        final var defendants = List.<String>of();
        final var expectedErrorMessageNotification = "O aggregado LegalProcess não pode ser criado";
        final var expectedErrorMessage = "O processo com número 1234567-89.2023.8.26.0100 já existe no sistema";


        final var aCommand = CreateLegalProcessCommand.with(number, status, defendants);

        when(legalProcessGateway.existsByNumber(number)).thenReturn(true);

        // when
        final var exception = assertThrows(NotificationException.class, () -> usecase.execute(aCommand));

        // then
        assertEquals(expectedErrorMessageNotification, exception.getMessage());
        assertEquals(expectedErrorMessage, exception.getErrors().getFirst().message());
        verify(legalProcessGateway, never()).create(any());
    }

    @Test
    public void givenValidParamWithDifferentStatus_whenCallsExecute_thenShouldCreateLegalProcessWithGivenStatus() {
        // given
        final var number = "1234567-89.2023.8.26.0100";
        final var status = "FINALIZADO";
        final var defendants = List.<String>of();

        final var aCommand = CreateLegalProcessCommand.with(number, status, defendants);

        when(legalProcessGateway.create(any())).thenAnswer(returnsFirstArg());
        when(legalProcessGateway.existsByNumber(any())).thenReturn(false);

        // when
        final var actualOutput = usecase.execute(aCommand);

        // then
        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        verify(legalProcessGateway, times(1)).existsByNumber(number);

        verify(legalProcessGateway, times(1)).create(argThat(aLegalProcess ->
                Objects.equals(number, aLegalProcess.getNumber()) &&
                        Objects.equals(status, aLegalProcess.getStatus().getValue())
        ));
    }

    @Test
    public void givenNullNumber_whenCallsExecute_thenShouldThrowValidationException() {
        // given
        final String number = null;
        final var status = "EM_ANDAMENTO";
        final var defendants = List.<String>of();
        final var expectedErrorMessageNotification = "O aggregado LegalProcess não pode ser criado";
        final var expectedErrorMessage = "O número do processo não pode ser nulo";

        final var aCommand = CreateLegalProcessCommand.with(number, status, defendants);

        // when & then
        final var exception = assertThrows(NotificationException.class, () -> usecase.execute(aCommand));
        assertEquals(expectedErrorMessageNotification, exception.getMessage());
        assertEquals(expectedErrorMessage, exception.getErrors().getFirst().message());

    }

    @Test
    public void givenEmptyStatus_whenCallsExecute_thenShouldThrowValidationException() {
        // given
        final var number = "1234567-89.2023.8.26.0100";
        final var status = "";
        final var defendants = List.<String>of();
        final var expectedErrorMessageNotification = "O aggregado LegalProcess não pode ser criado";
        final var expectedMessage = "O status do processo não pode ser vazio";


        final var aCommand = CreateLegalProcessCommand.with(number, status, defendants);

        // when & then
        final var exception = assertThrows(NotificationException.class, () -> usecase.execute(aCommand));
        assertEquals(expectedErrorMessageNotification, exception.getMessage());
        assertEquals(expectedMessage, exception.getErrors().getFirst().message());

    }
}
