package com.pol.promad.test.infrastructure.legalprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record LegalProcessResponse(
        @JsonProperty("id")String id,
        @JsonProperty("number")String number,
        @JsonProperty("status")String status,
        @JsonProperty("defendant_id") List<String> defendants

) {

    public static LegalProcessResponse from(final String id, final String number, final String status, List<String> defendants) {
        return new LegalProcessResponse(
                id,
                number,
                status,
                defendants
        );
    }
}
