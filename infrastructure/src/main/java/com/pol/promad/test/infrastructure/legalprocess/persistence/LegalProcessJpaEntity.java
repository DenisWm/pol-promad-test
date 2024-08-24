package com.pol.promad.test.infrastructure.legalprocess.persistence;

import com.pol.promad.test.domain.legalprocess.LegalProcess;
import com.pol.promad.test.domain.legalprocess.LegalProcessID;
import com.pol.promad.test.domain.legalprocess.StatusProcess;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "LegalProcess")
@Table(name = "legal_process")
public class LegalProcessJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "number", nullable = false)
    private String number;
    @Column(name = "status", nullable = false)
    private String status;

    public LegalProcessJpaEntity() {
    }

    private LegalProcessJpaEntity(String id, String number, String status) {
        this.id = id;
        this.number = number;
        this.status = status;
    }
    public static LegalProcessJpaEntity from(final LegalProcess aLegalProcess) {
        return new LegalProcessJpaEntity(
                aLegalProcess.getId().getValue(),
                aLegalProcess.getNumber(),
                aLegalProcess.getStatus().getValue()
        );
    }

    public LegalProcess toAggregate() {
        return LegalProcess.with(
                LegalProcessID.from(this.id),
                number,
                StatusProcess.of(this.status)
        );
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
