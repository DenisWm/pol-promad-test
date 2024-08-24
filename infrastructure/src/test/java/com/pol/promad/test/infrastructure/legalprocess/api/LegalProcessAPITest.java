package com.pol.promad.test.infrastructure.legalprocess.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pol.promad.test.application.legalprocess.create.CreateLegalProcessOutput;
import com.pol.promad.test.application.legalprocess.create.CreateLegalProcessUseCase;
import com.pol.promad.test.infrastructure.api.LegalProcessAPI;
import com.pol.promad.test.infrastructure.legalprocess.ControllerTest;
import com.pol.promad.test.infrastructure.legalprocess.models.CreateLegalProcessRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Objects;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest(controllers = LegalProcessAPI.class)
public class LegalProcessAPITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateLegalProcessUseCase createLegalProcessUseCase;

    @Test
    public void givenAValidCommand_whenCallCreateLegalProcess_shouldReturnLegalProcessId() throws Exception {
        final var number = "1234567-89.2023.8.26.0100";
        final var status = "EM_ANDAMENTO";

        final var aInput = new CreateLegalProcessRequest(number, status);

        when(createLegalProcessUseCase.execute(any()))
                .thenReturn(CreateLegalProcessOutput.from("123"));

        final var request = post("/legal-processes")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(aInput));

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/legal-processes/123"))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", equalTo("123")));

        verify(createLegalProcessUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(number, cmd.number())
                        && Objects.equals(status, cmd.status())
        ));
    }


}
