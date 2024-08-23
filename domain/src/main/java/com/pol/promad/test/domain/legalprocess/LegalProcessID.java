package com.pol.promad.test.domain.legalprocess;

import com.pol.promad.test.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class LegalProcessID extends Identifier {

    private String value;

    public LegalProcessID(final String value) {
        Objects.requireNonNull(value, "O ID do processo não pode ser nulo");
        this.value = value;
    }

    public static LegalProcessID unique() {
        return from(UUID.randomUUID());
    }
    public static LegalProcessID from(final String anId) {
        return new LegalProcessID(anId);
    }

    public static LegalProcessID from(final UUID anId) {
        return new LegalProcessID(anId.toString().toLowerCase());
    }
    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LegalProcessID that = (LegalProcessID) o;

        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
