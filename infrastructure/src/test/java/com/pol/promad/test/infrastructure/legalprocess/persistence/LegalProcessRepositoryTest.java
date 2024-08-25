package com.pol.promad.test.infrastructure.legalprocess.persistence;

import com.pol.promad.test.PostgresSQLGatewayTest;
import com.pol.promad.test.domain.legalprocess.LegalProcess;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@PostgresSQLGatewayTest
public class LegalProcessRepositoryTest {

    @Autowired
    private LegalProcessRepository legalProcessRepository;

    @Test
    public void givenAnInvalidNullNumber_whenCallSave_shouldReturnError() {
        final var expectedPropertyName = "number";
        final var expectedMessage = "not-null property references a null or transient value : com.pol.promad.test." +
                "infrastructure.legalprocess.persistence.LegalProcessJpaEntity.number";
        final var aLegalProcess = LegalProcess.newLegalProcess(
                "1234567-89.2023.8.26.0100",
                "EM_ANDAMENTO"
        );
        final var anEntity = LegalProcessJpaEntity.from(aLegalProcess);
        anEntity.setNumber(null);

        final var actualException = Assertions.assertThrows(DataIntegrityViolationException.class, () -> legalProcessRepository.save(anEntity));
        final var actualCause = assertInstanceOf(PropertyValueException.class, actualException.getCause());

        assertEquals(expectedPropertyName, actualCause.getPropertyName());
        assertEquals(expectedMessage, actualCause.getMessage());
    }
    @Test
    public void givenAnInvalidNullStatus_whenCallSave_shouldReturnError() {
        final var expectedPropertyName = "status";
        final var expectedMessage = "not-null property references a null or transient value : com.pol.promad.test.infrastructure.legalprocess.persistence.LegalProcessJpaEntity.status";
        final var aLegalProcess = LegalProcess.newLegalProcess(
                "1234567-89.2023.8.26.0100",
                "EM_ANDAMENTO"
        );
        final var anEntity = LegalProcessJpaEntity.from(aLegalProcess);
        anEntity.setStatus(null);

        final var actualException = Assertions.assertThrows(DataIntegrityViolationException.class, () -> legalProcessRepository.save(anEntity));
        final var actualCause = assertInstanceOf(PropertyValueException.class, actualException.getCause());

        assertEquals(expectedPropertyName, actualCause.getPropertyName());
        assertEquals(expectedMessage, actualCause.getMessage());
    }

}
