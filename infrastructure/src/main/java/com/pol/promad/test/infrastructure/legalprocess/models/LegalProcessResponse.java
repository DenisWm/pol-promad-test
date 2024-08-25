package com.pol.promad.test.infrastructure.legalprocess.models;

public record LegalProcessResponse(
        String id,
        String number,
        String status
) {

    public static LegalProcessResponse from(final String id, final String number, final String status) {
        return new LegalProcessResponse(
                id,
                number,
                status
        );
    }
}
