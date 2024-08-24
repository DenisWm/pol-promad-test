package com.pol.promad.test.application.legalprocess.update;

import com.pol.promad.test.IntegrationTest;
import com.pol.promad.test.domain.legalprocess.LegalProcess;
import com.pol.promad.test.infrastructure.legalprocess.persistence.LegalProcessJpaEntity;
import com.pol.promad.test.infrastructure.legalprocess.persistence.LegalProcessRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@IntegrationTest
public class UpdateLegalProcessUseCaseIT {

    @Autowired
    private UpdateLegalProcessUseCase useCase;

    @Autowired
    private LegalProcessRepository legalProcessRepository;

    @Test
    public void givenAValidLegalProcess_whenCallsUpdateLegalProcess_shouldReturnLegalProcess() {
        //given
        final var aLegalProcess = LegalProcess.newLegalProcess(
                "1234567-89.2023.8.26.0100",
                "SUSPENSO"
        );

        save(aLegalProcess);

        final var status = "EM_ANDAMENTO";
        final var id = aLegalProcess.getId();

        final var aCommand = UpdateLegalProcessCommand.with(
                id.getValue(),
                status
        );

        Assert.assertEquals(1, legalProcessRepository.count());

        //when
        final var actualOutput = useCase.execute(aCommand);

        //then
        assertNotNull(actualOutput);
        assertEquals(aLegalProcess.getId().getValue(), actualOutput.id());

        final var actualLegalProcess = legalProcessRepository.findById(actualOutput.id()).get();

        assertEquals(aLegalProcess.getNumber(), actualLegalProcess.getNumber());
        assertEquals(status, actualLegalProcess.getStatus());
    }

    private void save(LegalProcess aLegalProcess) {
        legalProcessRepository.save(LegalProcessJpaEntity.from(aLegalProcess));
    }
}
