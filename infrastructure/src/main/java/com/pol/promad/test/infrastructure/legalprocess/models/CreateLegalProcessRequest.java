package com.pol.promad.test.infrastructure.legalprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record CreateLegalProcessRequest(
        @Schema(description = "Número no formato 1234567-89.2023.8.26.0100", pattern = "^\\d{7}-\\d{2}\\.\\d{4}\\.\\d\\.\\d{2}\\.\\d{4}$")
        @Pattern(regexp = "^\\d{7}-\\d{2}\\.\\d{4}\\.\\d\\.\\d{2}\\.\\d{4}$", message = "O número deve seguir o formato 1234567-89.2023.8.26.0100")
        @JsonProperty("number") String number,
        @Schema(description = "Status do processo legal", example = "EM_ANDAMENTO", allowableValues = {"ARQUIVADO", "EM_ANDAMENTO", "FINALIZADO", "SUSPENSO"})
        @JsonProperty("status") String status,
        @Schema(description = "IDs dos réus")
        @JsonProperty("defendant_id") List<String> defendants

        ) {
    public static CreateLegalProcessRequest with(final String number, final String status, final List<String> defendants) {
        return new CreateLegalProcessRequest(
                number,
                status,
                defendants
        );
    }
}
