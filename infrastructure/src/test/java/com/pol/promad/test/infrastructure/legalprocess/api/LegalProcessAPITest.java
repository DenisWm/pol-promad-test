package com.pol.promad.test.infrastructure.legalprocess.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pol.promad.test.application.legalprocess.create.CreateLegalProcessOutput;
import com.pol.promad.test.application.legalprocess.create.CreateLegalProcessUseCase;
import com.pol.promad.test.application.legalprocess.delete.DeleteLegalProcessUseCase;
import com.pol.promad.test.application.legalprocess.retrieve.get.GetLegalProcessByIdUseCase;
import com.pol.promad.test.application.legalprocess.retrieve.get.LegalProcessOutput;
import com.pol.promad.test.application.legalprocess.update.UpdateLegalProcessOutput;
import com.pol.promad.test.application.legalprocess.update.UpdateLegalProcessUseCase;
import com.pol.promad.test.domain.exceptions.NotFoundException;
import com.pol.promad.test.domain.exceptions.NotificationException;
import com.pol.promad.test.domain.legalprocess.LegalProcess;
import com.pol.promad.test.domain.legalprocess.LegalProcessID;
import com.pol.promad.test.domain.validation.handler.Notification;
import com.pol.promad.test.infrastructure.api.LegalProcessAPI;
import com.pol.promad.test.infrastructure.legalprocess.ControllerTest;
import com.pol.promad.test.infrastructure.legalprocess.models.CreateLegalProcessRequest;
import com.pol.promad.test.infrastructure.legalprocess.models.UpdateLegalProcessRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Objects;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest(controllers = LegalProcessAPI.class)
public class LegalProcessAPITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateLegalProcessUseCase createLegalProcessUseCase;
    @MockBean
    private GetLegalProcessByIdUseCase getLegalProcessByIdUseCase;
    @MockBean
    private UpdateLegalProcessUseCase updateLegalProcessUseCase;
    @MockBean
    private DeleteLegalProcessUseCase deleteLegalProcessUseCase;

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

    @Test
    public void givenAValidNullNumber_whenCallCreateLegalProcess_shouldReturnNotification() throws Exception {
        final var number = "1234567-89.2023.8.26.0100";
        final var status = "EM_ANDAMENTO";
        final var expectedErrorMessage = "O número do processo não pode ser nulo";
        final var expectedErrorCount = 1;

        final var aInput = new CreateLegalProcessRequest(number, status);

        when(createLegalProcessUseCase.execute(any()))
                .thenThrow(new NotificationException("Error", Notification.create(new Error(expectedErrorMessage))));

        final var request = post("/legal-processes")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(aInput));

        final var aResponse = this.mvc.perform(request)
                .andDo(print());

        aResponse.andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Location", nullValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.errors", hasSize(expectedErrorCount)))
                .andExpect(jsonPath("$.errors[0].message", equalTo(expectedErrorMessage)));

        verify(createLegalProcessUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(number, cmd.number())
                        && Objects.equals(status, cmd.status())
        ));
    }
    @Test
    public void givenAValidId_whenCallsGetLegalProcessById_shouldReturnLegalProcess() throws Exception {
        final var number = "1234567-89.2023.8.26.0100";
        final var status = "EM_ANDAMENTO";

        final var aLegalProcess = LegalProcess.newLegalProcess(number, status);

        final var expectedId = aLegalProcess.getId().getValue();
        when(getLegalProcessByIdUseCase.execute(any())).thenReturn(LegalProcessOutput.from(aLegalProcess));

        final var aRequest = get("/legal-processes/{id}", expectedId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(aRequest).andDo(print());

        response.andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", equalTo(expectedId)))
                .andExpect(jsonPath("$.number", equalTo(number)))
                .andExpect(jsonPath("$.status", equalTo(status)));

        verify(getLegalProcessByIdUseCase, times(1)).execute(eq(expectedId));
    }

    @Test
    public void givenAnInvalidId_whenCallsGetLegalProcessById_shouldReturnNotFound() throws Exception {
        final var expectedId = "123";
        final var expectedErrorMessage = "LegalProcess com ID 123 não foi encontrado";
        when(getLegalProcessByIdUseCase.execute(any())).thenThrow(NotFoundException.with(LegalProcess.class, LegalProcessID.from(expectedId)));

        final var aRequest = get("/legal-processes/{id}", expectedId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)     ;

        final var aResponse = this.mvc.perform(aRequest)
                .andDo(print());

        aResponse.andExpect(status().isNotFound())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message", equalTo(expectedErrorMessage)));

        verify(getLegalProcessByIdUseCase, times(1)).execute(eq(expectedId));
    }

    @Test
    public void givenAValidCommand_whenCallUpdateLegalProcess_shouldReturnLegalProcessId() throws Exception {
        final var number = "1234567-89.2023.8.26.0100";
        final var status = "EM_ANDAMENTO";

        final var aLegalProcess = LegalProcess.newLegalProcess(number, "SUSPENSO");

        final var expectedId = aLegalProcess.getId().getValue();
        final var aInput = new UpdateLegalProcessRequest(status);

        when(updateLegalProcessUseCase.execute(any()))
                .thenReturn(UpdateLegalProcessOutput.from(aLegalProcess));

        final var aRequest = put("/legal-processes/{id}", expectedId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(aInput));

        final var aResponse = this.mvc.perform(aRequest)
                .andDo(print());

        aResponse.andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", equalTo(expectedId)));

        verify(updateLegalProcessUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedId, cmd.id())
                        && Objects.equals(status, cmd.status())
        ));
    }

    @Test
    public void givenACommandWithInvalidID_whenCallUpdateLegalProcess_shouldReturnNotFound() throws Exception {
        final var expectedId = LegalProcessID.from("123");
        final var status = "EM_ANDAMENTO";
        final var expectedErrorMessage = "LegalProcess com ID 123 não foi encontrado";

        final var aInput = new UpdateLegalProcessRequest(status);

        when(updateLegalProcessUseCase.execute(any()))
                .thenThrow(NotFoundException.with(LegalProcess.class, expectedId));

        final var aRequest = put("/legal-processes/{id}", expectedId.getValue())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(aInput));

        final var aResponse = this.mvc.perform(aRequest)
                .andDo(print());

        aResponse.andExpect(status().isNotFound())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message", equalTo(expectedErrorMessage)));

        verify(updateLegalProcessUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedId.getValue(), cmd.id())
                        && Objects.equals(status, cmd.status())
        ));
    }

    @Test
    public void givenACommandWithInvalidStatus_whenCallUpdateLegalProcess_shouldReturnException() throws Exception {
        final String status = null;
        final var expectedErrorMessage = "O status do processo não pode ser nulo";
        final var number = "1234567-89.2023.8.26.0100";

        final var aLegalProcess = LegalProcess.newLegalProcess(number, "SUSPENSO");
        final var id = aLegalProcess.getId().getValue();

        final var aInput = new UpdateLegalProcessRequest(status);

        when(updateLegalProcessUseCase.execute(any()))
                .thenThrow(new NotificationException(expectedErrorMessage, Notification.create(new Error(expectedErrorMessage))));

        final var aRequest = put("/legal-processes/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(aInput));

        final var aResponse = this.mvc.perform(aRequest)
                .andDo(print());

        aResponse.andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message", equalTo(expectedErrorMessage)));

        verify(updateLegalProcessUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(id, cmd.id())
                        && Objects.equals(status, cmd.status())
        ));
    }


    @Test
    public void givenAValidId_whenCallDeleteLegalProcess_shouldReturnNoContent() throws Exception {

        final var expectedId = "123";
        doNothing().when(deleteLegalProcessUseCase).execute(any());


        final var request = MockMvcRequestBuilders.delete("/legal-processes/{id}", expectedId)
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(deleteLegalProcessUseCase, times(1)).execute(eq(expectedId));
    }
}