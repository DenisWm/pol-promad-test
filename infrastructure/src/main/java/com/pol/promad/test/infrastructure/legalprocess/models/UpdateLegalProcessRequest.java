package com.pol.promad.test.infrastructure.legalprocess.models;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateLegalProcessRequest(
        @Schema(description = "Status do processo legal", example = "EM_ANDAMENTO", allowableValues = {"ARQUIVADO", "EM_ANDAMENTO", "FINALIZADO", "SUSPENSSO"})
        String status
) {
    public static UpdateLegalProcessRequest with(final String status) {
        return new UpdateLegalProcessRequest(
                status
        );
    }
}
