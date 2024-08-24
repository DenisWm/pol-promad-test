package com.pol.promad.test.infrastructure.legalprocess.models;

public record UpdateLegalProcessRequest(
        String status
) {
    public static UpdateLegalProcessRequest with(final String status) {
        return new UpdateLegalProcessRequest(
                status
        );
    }
}
