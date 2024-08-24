package com.pol.promad.test.application.legalprocess.delete;

import com.pol.promad.test.application.legalprocess.UseCaseTest;
import com.pol.promad.test.domain.legalprocess.LegalProcess;
import com.pol.promad.test.domain.legalprocess.LegalProcessGateway;
import com.pol.promad.test.domain.legalprocess.LegalProcessID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeleteLegalProcessUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultDeleteLegalProcessUseCase useCase;
    @Mock
    private LegalProcessGateway legalProcessGateway;
    @Override
    protected List<Object> getMocks() {
        return List.of(legalProcessGateway);
    }

    @Test
    public void givenAValidId_whenCallDeleteLegalProcess_shouldBeOk() {
        final var aLegalProcess = LegalProcess.newLegalProcess("1234567-89.2023.8.26.0100", "EM_ANDAMENTO");
        final var id = aLegalProcess.getId();


        doNothing()
                .when(legalProcessGateway).deleteById(id);
        assertDoesNotThrow(() -> useCase.execute(id.getValue()));

        verify(legalProcessGateway, times(1)).deleteById(id);
    }

    @Test
    public void givenAnInvalidId_whenCallDeleteLegalProcess_shouldBeOk() {
        final var id = LegalProcessID.from("123");


        doNothing()
                .when(legalProcessGateway).deleteById(id);
        assertDoesNotThrow(() -> useCase.execute(id.getValue()));

        verify(legalProcessGateway, times(1)).deleteById(id);
    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_shouldReturnException() {
        final var id = LegalProcessID.from("123");


        doThrow(new IllegalStateException("Gateway error"))
                .when(legalProcessGateway).deleteById(id);

        final var actualException = assertThrows(IllegalStateException.class, () -> useCase.execute(id.getValue()));

        assertEquals("Gateway error", actualException.getMessage());

        verify(legalProcessGateway, times(1)).deleteById(id);
    }
}
