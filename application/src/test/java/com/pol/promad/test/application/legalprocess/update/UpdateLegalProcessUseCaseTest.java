package com.pol.promad.test.application.legalprocess.update;

import com.pol.promad.test.application.legalprocess.UseCaseTest;
import com.pol.promad.test.domain.defendant.DefendantGateway;
import com.pol.promad.test.domain.exceptions.DomainException;
import com.pol.promad.test.domain.exceptions.NotificationException;
import com.pol.promad.test.domain.legalprocess.LegalProcess;
import com.pol.promad.test.domain.legalprocess.LegalProcessGateway;
import com.pol.promad.test.domain.legalprocess.LegalProcessID;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UpdateLegalProcessUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultUpdateLegalProcessUseCase useCase;
    @Mock
    private LegalProcessGateway gateway;
    @Mock
    private DefendantGateway defendantGateway;
    @Override
    protected List<Object> getMocks() {
        return List.of(gateway);
    }

    @Test
    public void givenAValidLegalProcess_whenCallsUpdateLegalProcess_shouldReturnLegalProcess() {
        //given
        final var aLegalProcess = LegalProcess.newLegalProcess(
                "1234567-89.2023.8.26.0100",
                "SUSPENSO"
        );
        final var status = "EM_ANDAMENTO";
        final var defendants = List.<String>of();
        final var id = aLegalProcess.getId();

        final var aCommand = UpdateLegalProcessCommand.with(
                id.getValue(),
                status, defendants
        );
        when(gateway.findById(eq(id))).thenReturn(Optional.of(LegalProcess.with(aLegalProcess)));
        when(gateway.update(any())).thenAnswer(returnsFirstArg());

        //when
        final var actualOutput = useCase.execute(aCommand);

        //then

        assertNotNull(actualOutput);
        assertEquals(aLegalProcess.getId().getValue(), actualOutput.id());

        verify(gateway,
                times(1)).findById(eq(id));

        verify(gateway, times(1)).update(argThat(aUpdatedLegalProcess ->
                Objects.equals(aCommand.id(), id.getValue())
                        && Objects.equals(aCommand.status(), aUpdatedLegalProcess.getStatus().getValue())
        ));
    }

    @Test
    public void givenInvalidStatus_whenCallsUpdateLegalProcess_shouldThrowValidationException() {
        // given
        final var aLegalProcess = LegalProcess.newLegalProcess(
                "1234567-89.2023.8.26.0100",
                "SUSPENSO"
        );
        final var status = "INVALID_STATUS";
        final var id = aLegalProcess.getId();
        final var defendants = List.<String>of();
        final var aCommand = UpdateLegalProcessCommand.with(
                id.getValue(),
                status, defendants
        );

        when(gateway.findById(eq(id))).thenReturn(Optional.of(LegalProcess.with(aLegalProcess)));

        // when & then
        assertThrows(NotificationException.class, () -> useCase.execute(aCommand));
        verify(gateway, times(1)).findById(eq(id));
        verify(gateway, never()).update(any());
    }

    @Test
    public void givenNonExistentLegalProcess_whenCallsUpdateLegalProcess_shouldThrowDomainException() {
        // given
        final var id = LegalProcessID.from("non-existent-id");
        final var status = "EM_ANDAMENTO";
        final var defendants = List.<String>of();

        final var aCommand = UpdateLegalProcessCommand.with(
                id.getValue(),
                status, defendants
        );

        when(gateway.findById(eq(id))).thenReturn(Optional.empty());

        // when & then
        assertThrows(DomainException.class, () -> useCase.execute(aCommand));
        verify(gateway, times(1)).findById(eq(id));
        verify(gateway, never()).update(any());
    }

    @Test
    public void givenNullStatus_whenCallsUpdateLegalProcess_shouldThrowValidationException() {
        // given
        final var aLegalProcess = LegalProcess.newLegalProcess(
                "1234567-89.2023.8.26.0100",
                "SUSPENSO"
        );
        final String status = null;
        final var id = aLegalProcess.getId();
        final var defendants = List.<String>of();

        final var aCommand = UpdateLegalProcessCommand.with(
                id.getValue(),
                status, defendants
        );

        when(gateway.findById(eq(id))).thenReturn(Optional.of(LegalProcess.with(aLegalProcess)));

        // when & then
        assertThrows(NotificationException.class, () -> useCase.execute(aCommand));
        verify(gateway, times(1)).findById(eq(id));
        verify(gateway, never()).update(any());
    }
}