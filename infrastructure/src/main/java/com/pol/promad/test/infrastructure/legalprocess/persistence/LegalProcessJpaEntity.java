package com.pol.promad.test.infrastructure.legalprocess.persistence;

import com.pol.promad.test.domain.defendant.DefendantID;
import com.pol.promad.test.domain.legalprocess.LegalProcess;
import com.pol.promad.test.domain.legalprocess.LegalProcessID;
import com.pol.promad.test.domain.legalprocess.StatusProcess;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @OneToMany(mappedBy = "legalProcess", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<LegalProcessDefendantJpaEntity> defendants;

    public LegalProcessJpaEntity() {
    }

    private LegalProcessJpaEntity(String id, String number, String status) {
        this.id = id;
        this.number = number;
        this.status = status;
        this.defendants = new HashSet<>();
    }
    public static LegalProcessJpaEntity from(final LegalProcess aLegalProcess) {
        final var anEntity = new LegalProcessJpaEntity(
                aLegalProcess.getId().getValue(),
                aLegalProcess.getNumber(),
                aLegalProcess.getStatus().getValue()
        );
        aLegalProcess.getDefendants().forEach(anEntity::addDefendant);
        return anEntity;
    }
    private void addDefendant(final DefendantID anId) {
        this.defendants.add(LegalProcessDefendantJpaEntity.from(this, anId));
    }
    private void removeDefendant(final DefendantID anId) {
        this.defendants.remove(LegalProcessDefendantJpaEntity.from(this, anId));
    }

    public LegalProcess toAggregate() {
        return LegalProcess.with(
                LegalProcessID.from(this.id),
                number,
                StatusProcess.of(this.status),
                getDefendantsIDs()

        );
    }
    public List<DefendantID> getDefendantsIDs() {
        return getDefendants().stream().map(it -> DefendantID.from(it.getId().getDefendantId())).toList();
    }

    public Set<LegalProcessDefendantJpaEntity> getDefendants() {
        return defendants;
    }

    public void setDefendants(Set<LegalProcessDefendantJpaEntity> defendants) {
        this.defendants = defendants;
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
