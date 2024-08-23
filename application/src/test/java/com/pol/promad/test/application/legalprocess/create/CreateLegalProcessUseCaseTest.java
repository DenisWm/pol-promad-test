package com.pol.promad.test.application.legalprocess.create;

import com.pol.promad.test.application.legalprocess.UseCaseTest;
import com.pol.promad.test.domain.legalprocess.LegalProcessGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;

public class CreateLegalProcessUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreateLegalProcess usecase;
    @Mock
    private LegalProcessGateway gateway;
    @Override
    protected List<Object> getMocks() {
        return null;
    }

    @Test
    public void givenValidParam_whenCallsExecute_thenShouldCreateLegalProcess() {
        // given
        final var number = "123";
        final var status = "EM_ANDAMENTO";

        final var aCommand = CreateLegalProcessCommmand.with(number, status);
        // when
        Mockito.when(gateway.create(any())).thenAnswer(returnsFirstArg());
        final var actualOutput = usecase.execute(aCommand);
        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(gateway, Mockito.times(1)).create(Mockito.argThat(aLegalProcess ->
            aLegalProcess.getNumber().equals(number) &&
            aLegalProcess.getStatus().equals(status)
        ));
    }
}
