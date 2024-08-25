package com.pol.promad.test.infrastructure.legalprocess.models;

import com.pol.promad.test.JacksonTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

@JacksonTest
public class UpdateLegalProcessRequestTest {


    @Autowired
    private JacksonTester<UpdateLegalProcessRequest> json;

    @Test
    public void testUnmarshall() throws Exception {
        final var status = "EM_ANDAMENTO";

        final var json = """
                {
                "status": "%s"
                }
                """.formatted(
                status
        );
        final var actualJson = this.json.parse(json);

        Assertions.assertThat(actualJson)
                .hasFieldOrPropertyWithValue("status", status);
    }

    @Test
    public void testMarshall() throws Exception {
        final var status = "EM_ANDAMENTO";

        final var request = new UpdateLegalProcessRequest( status);
        final var actualJson = this.json.write(request);

        Assertions.assertThat(actualJson)
                .hasJsonPathValue("status", status);
    }
}
