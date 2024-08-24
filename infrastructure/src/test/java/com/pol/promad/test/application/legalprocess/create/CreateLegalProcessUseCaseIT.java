package com.pol.promad.test.application.legalprocess.create;

import com.pol.promad.test.IntegrationTest;
import com.pol.promad.test.domain.legalprocess.LegalProcessGateway;
import com.pol.promad.test.infrastructure.legalprocess.persistence.LegalProcessRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;


import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@IntegrationTest
public class CreateLegalProcessUseCaseIT {

    @Autowired
    private CreateLegalProcessUseCase useCase;

    @Autowired
    private LegalProcessRepository legalProcessRepository;

    @SpyBean
    private LegalProcessGateway legalProcessGateway;

    @Test
    public void givenValidParam_whenCallsExecute_thenShouldCreateLegalProcess() {
        // given
        final var number = "1234567-89.2023.8.26.0100";
        final var status = "EM_ANDAMENTO";

        assertEquals(0, legalProcessRepository.count());

        when(legalProcessGateway.create(any())).thenAnswer(returnsFirstArg());
        when(legalProcessGateway.existsByNumber(any())).thenReturn(false);
        final var aCommand = CreateLegalProcessCommand.with(number, status);
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
}
