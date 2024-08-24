package com.pol.promad.test.infrastructure.legalprocess.models;

public record UpdateLegalProcessRequest(
        String id,
        String number,
        String status
) {
    public static UpdateLegalProcessRequest with(final String id, final String number, final String status) {
        return new UpdateLegalProcessRequest(
                id,
                number,
                status
        );
    }
}
