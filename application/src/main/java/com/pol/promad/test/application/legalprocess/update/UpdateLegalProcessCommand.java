package com.pol.promad.test.application.legalprocess.update;

public record UpdateLegalProcessCommand(
        String id,
        String status
) {

    public static UpdateLegalProcessCommand with(final String id, final String status) {
        return new UpdateLegalProcessCommand(id, status);
    }
}
