package com.pol.promad.test.application.legalprocess.update;

import com.pol.promad.test.IntegrationTest;
import com.pol.promad.test.domain.exceptions.DomainException;
import com.pol.promad.test.domain.exceptions.NotificationException;
import com.pol.promad.test.domain.legalprocess.LegalProcess;
import com.pol.promad.test.domain.legalprocess.LegalProcessID;
import com.pol.promad.test.infrastructure.legalprocess.persistence.LegalProcessJpaEntity;
import com.pol.promad.test.infrastructure.legalprocess.persistence.LegalProcessRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        final var defendants = List.<String>of();

        final var aCommand = UpdateLegalProcessCommand.with(
                id.getValue(),
                status,
                defendants
        );

        Assert.assertEquals(1, legalProcessRepository.count());

        //when
        final var actualOutput = useCase.execute(aCommand);

        //then
        assertNotNull(actualOutput);
        assertEquals(aLegalProcess.getId().getValue(), actualOutput.id());

        final var actualLegalProcess = legalProcessRepository.findById(actualOutput.id()).get();

        assertEquals(aLegalProcess.getId().getValue(), actualLegalProcess.getId());
        assertEquals(aLegalProcess.getNumber(), actualLegalProcess.getNumber());
        assertEquals(status, actualLegalProcess.getStatus());
    }
    @Test
    public void givenInvalidStatus_whenCallsUpdateLegalProcess_shouldThrowValidationException() {
        // given
        final var aLegalProcess = LegalProcess.newLegalProcess(
                "1234567-89.2023.8.26.0100",
                "SUSPENSO"
        );
        save(aLegalProcess);

        final var status = "INVALID_STATUS";
        final var id = aLegalProcess.getId();
        final var defendants = List.<String>of();

        final var aCommand = UpdateLegalProcessCommand.with(
                id.getValue(),
                status,
                defendants
        );

        Assert.assertEquals(1, legalProcessRepository.count());
        // when & then
        assertThrows(NotificationException.class, () -> useCase.execute(aCommand));

        Assert.assertEquals(1, legalProcessRepository.count());
    }

    @Test
    public void givenNonExistentLegalProcess_whenCallsUpdateLegalProcess_shouldThrowDomainException() {
        // given
        final var id = LegalProcessID.from("non-existent-id");
        final var status = "EM_ANDAMENTO";
        final var defendants = List.<String>of();
        Assert.assertEquals(0, legalProcessRepository.count());

        final var aCommand = UpdateLegalProcessCommand.with(
                id.getValue(),
                status,
                defendants
        );


        // when & then
        assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        Assert.assertEquals(0, legalProcessRepository.count());
    }

    private void save(LegalProcess aLegalProcess) {
        legalProcessRepository.save(LegalProcessJpaEntity.from(aLegalProcess));
    }
}
