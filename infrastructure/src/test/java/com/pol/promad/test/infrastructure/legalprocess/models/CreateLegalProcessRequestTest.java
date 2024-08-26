package com.pol.promad.test.infrastructure.legalprocess.models;

import com.pol.promad.test.JacksonTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import java.util.List;

@JacksonTest
public class CreateLegalProcessRequestTest {


    @Autowired
    private JacksonTester<CreateLegalProcessRequest> json;

    @Test
    public void testUnmarshall() throws Exception {
        final var number = "1234567-89.2023.8.26.0100";
        final var status = "EM_ANDAMENTO";
        final var defendant = List.<String>of();

        final var json = """
                {
                "number": "%s",
                "status": "%s",
                "defendant_id": []
                }
                """.formatted(
                number,
                status
        );
        final var actualJson = this.json.parse(json);

        Assertions.assertThat(actualJson)
                .hasFieldOrPropertyWithValue("number", number)
                .hasFieldOrPropertyWithValue("status", status);
    }

    @Test
    public void testMarshall() throws Exception {
        final var number = "1234567-89.2023.8.26.0100";
        final var status = "EM_ANDAMENTO";
        final var defendant = List.<String>of();

        final var request = new CreateLegalProcessRequest(number, status, defendant);
        final var actualJson = this.json.write(request);

        Assertions.assertThat(actualJson)
                .hasJsonPathValue("number", number)
                .hasJsonPathValue("status", status)
                .hasJsonPathValue("defendant_id", defendant)
        ;
    }
}
