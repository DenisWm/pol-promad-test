package com.pol.promad.test.infrastructure.defendant.models;

public record CreateDefendantRequest(String name) {
    public static CreateDefendantRequest from(final String name) {
        return new CreateDefendantRequest(name);
    }
}
