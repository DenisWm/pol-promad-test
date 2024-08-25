package com.pol.promad.test.application.legalprocess.update;

import java.util.List;

public record UpdateLegalProcessCommand(
        String id,
        String status,
        List<String> defendants
) {

    public static UpdateLegalProcessCommand with(final String id, final String status, final List<String> defendants) {
        return new UpdateLegalProcessCommand(id, status, defendants);
    }
}
