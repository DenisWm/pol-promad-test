package com.pol.promad.test.infrastructure.legalprocess.persistence;

import com.pol.promad.test.domain.defendant.DefendantID;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "legal_process_defendant")
public class LegalProcessDefendantJpaEntity {

    @EmbeddedId
    private LegalProcessDefendantID id;

    @ManyToOne
    @MapsId("legalProcessId")
    private LegalProcessJpaEntity legalProcess;

    public LegalProcessDefendantJpaEntity() {
    }

    public LegalProcessDefendantJpaEntity(final LegalProcessJpaEntity aLegalProcess, final DefendantID aDefendantId) {
        this.id = LegalProcessDefendantID.from(aLegalProcess.getId(), aDefendantId.getValue());
        this.legalProcess = aLegalProcess;
    }

    public static LegalProcessDefendantJpaEntity from(final LegalProcessJpaEntity aLegalProcess, final DefendantID aDefendantId) {
        return new LegalProcessDefendantJpaEntity(aLegalProcess, aDefendantId);
    }

    public LegalProcessDefendantID getId() {
        return id;
    }

    public void setId(LegalProcessDefendantID id) {
        this.id = id;
    }

    public LegalProcessJpaEntity getLegalProcess() {
        return legalProcess;
    }

    public void setLegalProcess(LegalProcessJpaEntity legalProcess) {
        this.legalProcess = legalProcess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LegalProcessDefendantJpaEntity that = (LegalProcessDefendantJpaEntity) o;

        if (!Objects.equals(id, that.id)) return false;
        return Objects.equals(legalProcess, that.legalProcess);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (legalProcess != null ? legalProcess.hashCode() : 0);
        return result;
    }
}
