package com.pol.promad.test.domain.defendant;

import com.pol.promad.test.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class DefendantID extends Identifier {

    private String value;

    public DefendantID(final String value) {
        Objects.requireNonNull(value, "O ID do réu não pode ser nulo");
        this.value = value;
    }

    public static DefendantID unique() {
        return from(UUID.randomUUID());
    }
    public static DefendantID from(final String anId) {
        return new DefendantID(anId);
    }

    public static DefendantID from(final UUID anId) {
        return new DefendantID(anId.toString().toLowerCase());
    }
    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefendantID that = (DefendantID) o;

        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
