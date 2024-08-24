package com.pol.promad.test.application.legalprocess.delete;

import com.pol.promad.test.IntegrationTest;
import com.pol.promad.test.domain.legalprocess.LegalProcess;
import com.pol.promad.test.domain.legalprocess.LegalProcessID;
import com.pol.promad.test.infrastructure.legalprocess.persistence.LegalProcessJpaEntity;
import com.pol.promad.test.infrastructure.legalprocess.persistence.LegalProcessRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
public class DeleteLegalProcessUseCaseIT {

    @Autowired
    private DeleteLegalProcessUseCase useCase;

    @Autowired
    private LegalProcessRepository legalProcessRepository;

    @Test
    public void givenAValidId_whenCallDeleteLegalProcess_shouldBeOk() {
        final var aLegalProcess = LegalProcess.newLegalProcess("1234567-89.2023.8.26.0100", "EM_ANDAMENTO");
        final var id = aLegalProcess.getId();
        save(aLegalProcess);
        assertEquals(1, legalProcessRepository.count());
        assertDoesNotThrow(() -> useCase.execute(id.getValue()));
        assertEquals(0, legalProcessRepository.count());
    }

    @Test
    public void givenAnInvalidId_whenCallDeleteLegalProcess_shouldBeOk() {
        final var id = LegalProcessID.from("123");
        assertEquals(0, legalProcessRepository.count());
        assertDoesNotThrow(() -> useCase.execute(id.getValue()));
        assertEquals(0, legalProcessRepository.count());
    }

    private void save(LegalProcess aLegalProcess) {
        legalProcessRepository.save(LegalProcessJpaEntity.from(aLegalProcess));
    }
}
