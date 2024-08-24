package com.pol.promad.test.infrastructure.legalprocess.models;

public record CreateLegalProcessRequest(
        String number,
        String status
) {
    public static CreateLegalProcessRequest with(final String number, final String status) {
        return new CreateLegalProcessRequest(
                number,
                status
        );
    }
}
