package com.pol.promad.test.infrastructure.legalprocess.models;

public record LegalProcessListResponse(
        String id,
        String number,
        String status
) {
    public static LegalProcessListResponse from(final String id, final String number, final String status) {
        return new LegalProcessListResponse(
                id,
                number,
                status
        );
    }
}
