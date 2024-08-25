package com.pol.promad.test.e2e.legalprocess;

import com.pol.promad.test.E2ETest;
import com.pol.promad.test.domain.legalprocess.LegalProcessID;
import com.pol.promad.test.infrastructure.configuration.Json;
import com.pol.promad.test.infrastructure.legalprocess.models.CreateLegalProcessRequest;
import com.pol.promad.test.infrastructure.legalprocess.models.LegalProcessResponse;
import com.pol.promad.test.infrastructure.legalprocess.models.LegalProcessResponseTest;
import com.pol.promad.test.infrastructure.legalprocess.models.UpdateLegalProcessRequest;
import com.pol.promad.test.infrastructure.legalprocess.persistence.LegalProcessRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@E2ETest
@Testcontainers
public class LegalProcessE2ETest {

    @Container
    private static final PostgreSQLContainer POSTGRESQL_CONTAINER = new PostgreSQLContainer("postgres:latest")
            .withPassword("postgres")
            .withUsername("postgres")
            .withDatabaseName("adm_processos");
    @Autowired
    private LegalProcessRepository legalProcessRepository;

    @Autowired
    private MockMvc mvc;
    @DynamicPropertySource
    public static void setDatasourceProperties(final DynamicPropertyRegistry registry) {
        registry.add("postgres.port", () -> POSTGRESQL_CONTAINER.getMappedPort(5432));
    }
    @Test
    public void asALegalProcessAdminIShouldBeAbleToCreateANewLegalProcessWithValidValues() throws Exception {
        Assertions.assertTrue(POSTGRESQL_CONTAINER.isRunning());

        assertEquals(0, legalProcessRepository.count());

        final var number = "1234567-89.2021.8.26.0001";
        final var status = "EM_ANDAMENTO";

        final var actualId = givenALegalProcess(number, status);

        assertEquals(1, legalProcessRepository.count());

        final var actualLegalProcess = retrieveALegalProcess(actualId.getValue());

        assertEquals(number, actualLegalProcess.number());
        assertEquals(status, actualLegalProcess.status());
    }

    @Test
    public void asALegalProcessAdminIShouldBeAbleToUpdateALegalprocessWithValidValuesByItsIdentifier() throws Exception {
        Assertions.assertTrue(POSTGRESQL_CONTAINER.isRunning());

        assertEquals(0, legalProcessRepository.count());

        final var number = "1234567-89.2021.8.26.0001";
        final var status = "EM_ANDAMENTO";
        final var actualId = givenALegalProcess(number, "SUSPENSO");


        assertEquals(1, legalProcessRepository.count());

        final var requestBody = new UpdateLegalProcessRequest(status);

        final var aMockMvcRequestBuilder = put("/legal-processes/{id}", actualId.getValue())
                .content(Json.writeValueAsString(requestBody))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(aMockMvcRequestBuilder)
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(actualId.getValue())));

        final var actualLegalProcess = legalProcessRepository.findById(actualId.getValue()).get();

        assertEquals(number, actualLegalProcess.getNumber());
        assertEquals(status, actualLegalProcess.getStatus());
    }

    @Test
    public void asALegalProcessAdminIShouldBeAbleToGetALegalProcessByItsIdentifier() throws Exception {
        Assertions.assertTrue(POSTGRESQL_CONTAINER.isRunning());

        assertEquals(0, legalProcessRepository.count());

        final var number = "1234567-89.2021.8.26.0001";
        final var status = "EM_ANDAMENTO";

        final var actualId = givenALegalProcess(number, status);


        assertEquals(1, legalProcessRepository.count());

        final var actualLegalProcess = legalProcessRepository.findById(actualId.getValue()).get();

        assertEquals(number, actualLegalProcess.getNumber());
        assertEquals(status, actualLegalProcess.getStatus());
    }

    @Test
    public void asALegalProcessAdminIShouldBeAbleToSeeATreatedErrorByGettingANotFoundLegalProcess() throws Exception {
        Assertions.assertTrue(POSTGRESQL_CONTAINER.isRunning());

        final var expectedErrorMessage = "LegalProcess com ID 123 não foi encontrado";

        assertEquals(0, legalProcessRepository.count());

        final var aMockMvcRequestBuilder = get("/legal-processes/123")
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(aMockMvcRequestBuilder)
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", equalTo(expectedErrorMessage)));
    }

    @Test
    public void asALegalProcessAdminIShouldBeAbleToNavigateToAllLegalProcesses() throws Exception {
        Assertions.assertTrue(POSTGRESQL_CONTAINER.isRunning());

        assertEquals(0, legalProcessRepository.count());

        givenALegalProcess("1234567-89.2021.8.26.0001", "SUSPENSO");
        givenALegalProcess("1234567-89.2021.8.26.0002", "EM_ANDAMENTO");
        givenALegalProcess("1234567-89.2021.8.26.0003", "ARQUIVADO");
        givenALegalProcess("1234567-89.2021.8.26.0004", "FINALIZADO");

        listLegalProcesses(0, 1)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.current_page", equalTo(0)))
                .andExpect(jsonPath("$.per_page", equalTo(1)))
                .andExpect(jsonPath("$.total", equalTo(4)))
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].number", equalTo("1234567-89.2021.8.26.0001")));

        listLegalProcesses(1, 1)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.current_page", equalTo(1)))
                .andExpect(jsonPath("$.per_page", equalTo(1)))
                .andExpect(jsonPath("$.total", equalTo(4)))
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].number", equalTo("1234567-89.2021.8.26.0002")));

        listLegalProcesses(2, 1)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.current_page", equalTo(2)))
                .andExpect(jsonPath("$.per_page", equalTo(1)))
                .andExpect(jsonPath("$.total", equalTo(4)))
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].number", equalTo("1234567-89.2021.8.26.0003")));
        listLegalProcesses(3, 1)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.current_page", equalTo(3)))
                .andExpect(jsonPath("$.per_page", equalTo(1)))
                .andExpect(jsonPath("$.total", equalTo(4)))
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].number", equalTo("1234567-89.2021.8.26.0004")));
        listLegalProcesses(4, 1)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.current_page", equalTo(4)))
                .andExpect(jsonPath("$.per_page", equalTo(1)))
                .andExpect(jsonPath("$.total", equalTo(4)))
                .andExpect(jsonPath("$.items", hasSize(0)));

    }

    @Test
    public void asALegalProcessAdminIShouldBeAbleToSortAllLegalProcessesByStatusDesc() throws Exception {
        Assertions.assertTrue(POSTGRESQL_CONTAINER.isRunning());

        assertEquals(0, legalProcessRepository.count());

        givenALegalProcess("1234567-89.2021.8.26.0001", "SUSPENSO");
        givenALegalProcess("1234567-89.2021.8.26.0002", "EM_ANDAMENTO");
        givenALegalProcess("1234567-89.2021.8.26.0003", "ARQUIVADO");
        givenALegalProcess("1234567-89.2021.8.26.0004", "FINALIZADO");

        listLegalProcesses("", 0, 4, "status", "desc")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.current_page", equalTo(0)))
                .andExpect(jsonPath("$.per_page", equalTo(4)))
                .andExpect(jsonPath("$.total", equalTo(4)))
                .andExpect(jsonPath("$.items", hasSize(4)))
                .andExpect(jsonPath("$.items[0].number", equalTo("1234567-89.2021.8.26.0001")))
                .andExpect(jsonPath("$.items[1].number", equalTo("1234567-89.2021.8.26.0004")))
                .andExpect(jsonPath("$.items[2].number", equalTo("1234567-89.2021.8.26.0002")))
                .andExpect(jsonPath("$.items[3].number", equalTo("1234567-89.2021.8.26.0003")))
        ;

    }

    private ResultActions listLegalProcesses(final int page, final int perPage, final String search) throws Exception {
        return listLegalProcesses(search, page, perPage, "", "");
    }

    private ResultActions listLegalProcesses(final int page, final int perPage) throws Exception {
        return listLegalProcesses("", page, perPage, "", "");
    }

    private ResultActions listLegalProcesses(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) throws Exception {

        final var aMockMvcRequestBuilder = get("/legal-processes")
                .queryParam("search", search)
                .queryParam("page", String.valueOf(page))
                .queryParam("perPage", String.valueOf(perPage))
                .queryParam("sort", sort)
                .queryParam("dir", direction)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(aMockMvcRequestBuilder)
                .andDo(print());


        return response;
    }

    private LegalProcessResponse retrieveALegalProcess(String anId) throws Exception {
        final var aMockMvcRequestBuilder = get("/legal-processes/{id}", anId)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(aMockMvcRequestBuilder)
                .andDo(print());

        final var jsonContent = response.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return Json.readValue(jsonContent, LegalProcessResponse.class);
    }

    private LegalProcessID givenALegalProcess(String number, String status) throws Exception {
        final var aRequestBody = new CreateLegalProcessRequest(number, status);

        final var aMockMvcRequestBuilder = post("/legal-processes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(aRequestBody));

        final var response = this.mvc.perform(aMockMvcRequestBuilder)
                .andDo(print());

        final var actualId = response.andExpect(status().isCreated())
                .andReturn()
                .getResponse().getHeader("Location")
                .replace("/legal-processes/", "");

        return LegalProcessID.from(actualId);
    }
}