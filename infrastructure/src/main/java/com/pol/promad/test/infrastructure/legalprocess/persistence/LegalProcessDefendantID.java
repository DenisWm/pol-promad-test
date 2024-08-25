package com.pol.promad.test.infrastructure.legalprocess.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LegalProcessDefendantID implements Serializable {

    @Column(name = "legal_process_id", nullable = false)
    private String legalProcessId;

    @Column(name = "defendant_id", nullable = false)
    private String defendantId;

    public LegalProcessDefendantID() {
    }

    private LegalProcessDefendantID(final String aLegalProcessId, final String aDefendantId) {
        this.legalProcessId = aLegalProcessId;
        this.defendantId = aDefendantId;
    }

    public static LegalProcessDefendantID from(final String aLegalProcessId, final String aDefendantId) {
        return new LegalProcessDefendantID(aLegalProcessId, aDefendantId);
    }

    public String getLegalProcessId() {
        return legalProcessId;
    }

    public void setLegalProcessId(String legalProcessId) {
        this.legalProcessId = legalProcessId;
    }

    public String getDefendantId() {
        return defendantId;
    }

    public void setDefendantId(String defendantId) {
        this.defendantId = defendantId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final LegalProcessDefendantID that = (LegalProcessDefendantID) o;

        if (!Objects.equals(legalProcessId, that.legalProcessId))
            return false;
        return Objects.equals(defendantId, that.defendantId);
    }

    @Override
    public int hashCode() {
        int result = legalProcessId != null ? legalProcessId.hashCode() : 0;
        result = 31 * result + (defendantId != null ? defendantId.hashCode() : 0);
        return result;
    }
}
