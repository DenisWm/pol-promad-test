package com.pol.promad.test.application.legalprocess.retrieve.get;

import com.pol.promad.test.application.legalprocess.UseCaseTest;
import com.pol.promad.test.domain.exceptions.DomainException;
import com.pol.promad.test.domain.legalprocess.LegalProcess;
import com.pol.promad.test.domain.legalprocess.LegalProcessGateway;
import com.pol.promad.test.domain.legalprocess.LegalProcessID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GetLegalProcessByIdUseCaseTest extends UseCaseTest {
    @InjectMocks
    private DefaultGetLegalProcessByIdUseCase usecase;
    @Mock
    private LegalProcessGateway legalProcessGateway;
    @Override
    protected List<Object> getMocks() {
        return List.of(legalProcessGateway);
    }

    @Test
    public void givenValidParam_whenCallsExecute_thenShouldGetLegalProcess() {
        // given
        final var number = "1234567-89.2023.8.26.0100";
        final var status = "EM_ANDAMENTO";
        final var aLegalProcess = LegalProcess.newLegalProcess(number, status);

        final var id = aLegalProcess.getId();

        // when
        when(legalProcessGateway.findById(any())).thenReturn(Optional.of(aLegalProcess));

        final var actualOutput = usecase.execute(id.getValue());

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(id.getValue(), actualOutput.id());
        Assertions.assertEquals(number, actualOutput.number());
        Assertions.assertEquals(status, actualOutput.status());
        Assertions.assertTrue(actualOutput.defendants().isEmpty());

        verify(legalProcessGateway, times(1)).findById(id);
    }

    @Test
    public void givenNonExistentId_whenCallsExecute_thenShouldThrowDomainException() {
        // given
        final var id = LegalProcessID.from("invalid-id");

        // when
        when(legalProcessGateway.findById(any())).thenReturn(Optional.empty());

        // then
        assertThrows(DomainException.class, () -> usecase.execute(id.getValue()));
        verify(legalProcessGateway, times(1)).findById(id);
    }

    @Test
    public void givenInvalid_whenGatewayThrowsException_thenShouldReturnException() {
        // given
        final var id = LegalProcessID.from("invalid-id");
        when(legalProcessGateway.findById(any())).thenThrow(new IllegalStateException("Gateway Error"));

        // when & then
        final var exception = assertThrows(IllegalStateException.class, () -> usecase.execute(id.getValue()));

        Assertions.assertEquals("Gateway Error", exception.getMessage());

        verify(legalProcessGateway, times(1)).findById(id);
    }
}
