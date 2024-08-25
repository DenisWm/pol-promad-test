package com.pol.promad.test.infrastructure.defendant.persistence;

import com.pol.promad.test.domain.defendant.Defendant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "Defendant")
@Table(name = "defendant")
public class DefendantJpaEntity {

    @Id
    private String id;
    @Column(name = "name", nullable = false)
    private String name;

    public DefendantJpaEntity() {
    }

    private DefendantJpaEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static DefendantJpaEntity from(final Defendant aDefendant) {
        return new DefendantJpaEntity(aDefendant.getName(), aDefendant.getName());
    }

    public Defendant toAggregate() {
        return Defendant.with
                (this.id, this.name);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefendantJpaEntity that = (DefendantJpaEntity) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
