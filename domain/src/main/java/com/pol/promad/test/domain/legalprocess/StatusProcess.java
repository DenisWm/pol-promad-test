package com.pol.promad.test.domain.legalprocess;

import com.pol.promad.test.domain.ValueObject;

import java.util.Objects;

public class StatusProcess extends ValueObject {
    private String value;

    private StatusProcess(String value) {
        this.value = value;
    }

    public static StatusProcess of(String value) {
        return new StatusProcess(value != null ? value.toUpperCase() : "error");
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusProcess that = (StatusProcess) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }


}