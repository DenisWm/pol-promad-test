package com.pol.promad.test.infrastructure.legalprocess.models;

import com.pol.promad.test.JacksonTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
@JacksonTest
public class LegalProcessResponseTest {

    @Autowired
    private JacksonTester<LegalProcessResponse> json;

    @Test
    public void testMarshall() throws Exception {
        final var id = "123";
        final var number = "1234567-89.2023.8.26.0100";
        final var status = "EM_ANDAMENTO";

        final var response = new LegalProcessResponse(
                id,
                number,
                status

        );
        final var actualJson = json.write(response);

        Assertions.assertThat(actualJson)
                .hasJsonPathValue("id", id)
                .hasJsonPathValue("number", number)
                .hasJsonPathValue("status", status);
        ;
    }

    @Test
    public void testUnmarshall() throws Exception {
        final var id = "123";
        final var number = "1234567-89.2023.8.26.0100";
        final var status = "EM_ANDAMENTO";

        final var json = """
                {
                    "id": "%s",
                    "number": "%s",
                    "status": "%s"
                }
                """.formatted(
                id,
                number,
                status
        );
        final var actualJson = this.json.parse(json);

        Assertions.assertThat(actualJson)
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("number", number)
                .hasFieldOrPropertyWithValue("status", status)
        ;
    }
}
