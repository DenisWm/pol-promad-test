package com.pol.promad.test.infrastructure.legalprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record UpdateLegalProcessRequest(
        @Schema(description = "Status do processo legal", example = "EM_ANDAMENTO", allowableValues = {"ARQUIVADO", "EM_ANDAMENTO", "FINALIZADO", "SUSPENSSO"})
        @JsonProperty("status") String status,
        @Schema(description = "IDs dos réus")
        @JsonProperty("defendant_id") List<String> defendants

) {
    public static UpdateLegalProcessRequest with(final String status, final List<String> defendants) {
        return new UpdateLegalProcessRequest(
                status,
                defendants
        );
    }
}
