package com.pol.promad.test.infrastructure.api;

import com.pol.promad.test.domain.pagination.Pagination;
import com.pol.promad.test.infrastructure.legalprocess.models.CreateLegalProcessRequest;
import com.pol.promad.test.infrastructure.legalprocess.models.LegalProcessListResponse;
import com.pol.promad.test.infrastructure.legalprocess.models.LegalProcessResponse;
import com.pol.promad.test.infrastructure.legalprocess.models.UpdateLegalProcessRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/legal-processes")
@Tag(name = "Legal Processes")
public interface LegalProcessAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new legal process")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
            @ApiResponse(responseCode = "500", description = "An internal error was thrown"),
    })
    ResponseEntity<?> createLegalProcess(@RequestBody CreateLegalProcessRequest input);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List all legal processes paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listed successfully"),
            @ApiResponse(responseCode = "422", description = "A invalid parameter was received"),
            @ApiResponse(responseCode = "500", description = "An internal error was thrown"),
    })
    Pagination<LegalProcessListResponse> listLegalProcesses(
            @RequestParam(name = "search", required = false, defaultValue = "") final String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "number") final String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "asc") final String direction
    );

    @GetMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get a legal process by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Legal Process retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "LegalProcess was not found"),
            @ApiResponse(responseCode = "500", description = "An internal error was thrown"),
    })
    LegalProcessResponse getById(@PathVariable(name = "id") String id);

    @PutMapping(
            value = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update a legal process by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Legal process was updated successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
            @ApiResponse(responseCode = "404", description = "Legal process was not found"),
            @ApiResponse(responseCode = "500", description = "An internal error was thrown"),
    })
    ResponseEntity<?> updateCategoryById(
            @PathVariable(name = "id") String id,
            @RequestBody UpdateLegalProcessRequest input
    );

    @DeleteMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Delete a legal process by it's identifier")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Legal process was deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Legal process was not found"),
            @ApiResponse(responseCode = "500", description = "An internal error was thrown"),
    })
    void deleteLegalProcessById(
            @PathVariable(name = "id") String id
    );
}
