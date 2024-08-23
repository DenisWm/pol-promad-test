package com.pol.promad.test.application.legalprocess.retrieve.get;

import com.pol.promad.test.application.legalprocess.UseCaseTest;
import com.pol.promad.test.domain.legalprocess.LegalProcess;
import com.pol.promad.test.domain.legalprocess.LegalProcessGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GetLegalProcessByIdUseCaseTest extends UseCaseTest {
    @InjectMocks
    private DefaultLegalProcessGetById usecase;
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


        final var aCommand = GetLegalProcessByIdCommand.with(id);
        // when
        when(legalProcessGateway.findById(any())).thenReturn(Optional.of(aLegalProcess));

        final var actualOutput = usecase.execute(aCommand).get();

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(number, actualOutput.number());
        Assertions.assertEquals(status, actualOutput.status());

        verify(legalProcessGateway, times(1)).findById(id);
    }
}
