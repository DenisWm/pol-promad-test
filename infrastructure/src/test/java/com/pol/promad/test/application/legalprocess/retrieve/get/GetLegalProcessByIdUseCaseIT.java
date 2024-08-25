package com.pol.promad.test.application.legalprocess.retrieve.get;

import com.pol.promad.test.IntegrationTest;
import com.pol.promad.test.domain.exceptions.DomainException;
import com.pol.promad.test.domain.legalprocess.LegalProcess;
import com.pol.promad.test.domain.legalprocess.LegalProcessID;
import com.pol.promad.test.infrastructure.legalprocess.persistence.LegalProcessJpaEntity;
import com.pol.promad.test.infrastructure.legalprocess.persistence.LegalProcessRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@IntegrationTest
public class GetLegalProcessByIdUseCaseIT {

    @Autowired
    private GetLegalProcessByIdUseCase useCase;

    @Autowired
    private LegalProcessRepository legalProcessRepository;

    @Test
    public void givenValidParam_whenCallsExecute_thenShouldGetLegalProcess() {
        // given
        final var number = "1234567-89.2023.8.26.0100";
        final var status = "EM_ANDAMENTO";
        final var aLegalProcess = LegalProcess.newLegalProcess(number, status);
        save(aLegalProcess);
        final var id = aLegalProcess.getId();
        // when

        final var actualOutput = useCase.execute(id.getValue());

        // then
        assertEquals(1, legalProcessRepository.count());
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(id.getValue(), actualOutput.id());
        Assertions.assertEquals(number, actualOutput.number());
        Assertions.assertEquals(status, actualOutput.status());
    }



    @Test
    public void givenNonExistentId_whenCallsExecute_thenShouldThrowDomainException() {
        // given
        final var id = LegalProcessID.from("invalid-id");
        final var expected = "LegalProcess com ID invalid-id não foi encontrado";

        // when & then
        final var actualException = assertThrows(DomainException.class, () -> useCase.execute(id.getValue()));

        Assertions.assertEquals(expected, actualException.getMessage());
    }


    private void save(LegalProcess aLegalProcess) {
        legalProcessRepository.save(LegalProcessJpaEntity.from(aLegalProcess));
    }
}
