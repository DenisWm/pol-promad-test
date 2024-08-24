package com.pol.promad.test.application.legalprocess.update;

import com.pol.promad.test.application.UseCase;
import com.pol.promad.test.application.legalprocess.UseCaseTest;
import com.pol.promad.test.domain.legalprocess.LegalProcess;
import com.pol.promad.test.domain.legalprocess.LegalProcessGateway;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UpdateLegalProcessUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultUpdateLegalProcessUseCase useCase;
    @Mock
    private LegalProcessGateway gateway;
    @Override
    protected List<Object> getMocks() {
        return List.of(gateway);
    }

    @Test
    public void givenAValidLegalProcess_whenCallsUpdateLegalProcess_shouldReturnLegalProcess() {
        //given
        final var aLegalProcess = LegalProcess.newLegalProcess(
                "14231-89.2023.8.26.0100",
                "SUSPENSO"
        );
        final var number = "1234567-89.2023.8.26.0100";
        final var status = "EM_ANDAMENTO";
        final var id = aLegalProcess.getId();

        final var aCommand = UpdateLegalProcessCommand.with(
                id.getValue(),
                number,
                status
        );

        when(gateway.findById(eq(id))).thenReturn(Optional.of(LegalProcess.with(aLegalProcess)));
        when(gateway.update(any())).thenAnswer(returnsFirstArg());

        //when
        final var actualOutput = useCase.execute(aCommand);

        //then

        assertNotNull(actualOutput);
        assertEquals(aLegalProcess.getId(), actualOutput.id());

        verify(gateway,
                times(1)).findById(eq(id));

        verify(gateway, times(1)).update(argThat(aUpdatedLegalProcess ->
                Objects.equals(aCommand.id(), id)
                        && Objects.equals(aCommand.number(), aUpdatedLegalProcess.getNumber())
                        && Objects.equals(aCommand.status(), aUpdatedLegalProcess.getStatus())
        ));
    }
}
