package com.pol.promad.test.infrastructure.api.controller;

import com.pol.promad.test.application.legalprocess.create.CreateLegalProcessCommand;
import com.pol.promad.test.application.legalprocess.create.CreateLegalProcessUseCase;
import com.pol.promad.test.domain.pagination.Pagination;
import com.pol.promad.test.infrastructure.api.LegalProcessAPI;
import com.pol.promad.test.infrastructure.legalprocess.models.CreateLegalProcessRequest;
import com.pol.promad.test.infrastructure.legalprocess.models.LegalProcessListResponse;
import com.pol.promad.test.infrastructure.legalprocess.models.LegalProcessResponse;
import com.pol.promad.test.infrastructure.legalprocess.models.UpdateLegalProcessRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class LegalProcessController implements LegalProcessAPI {

    private final CreateLegalProcessUseCase createLegalProcessUseCase;

    public LegalProcessController(final CreateLegalProcessUseCase createLegalProcessUseCase) {
        this.createLegalProcessUseCase = Objects.requireNonNull(createLegalProcessUseCase);
    }
    @Override
    public ResponseEntity<?> createLegalProcess(final CreateLegalProcessRequest anInput) {
        final var aCommand = CreateLegalProcessCommand.with(
                anInput.number(),
                anInput.status()
        );
        final var output = createLegalProcessUseCase.execute(aCommand);
        return ResponseEntity.created(URI.create("/legal-processes/" + output.id())).body(output);
    }

    @Override
    public Pagination<LegalProcessListResponse> listLegalProcesses(String search, int page, int perPage, String sort, String direction) {
        return null;
    }

    @Override
    public LegalProcessResponse getById(String id) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateCategoryById(String id, UpdateLegalProcessRequest input) {
        return null;
    }

    @Override
    public void deleteLegalProcessById(String id) {

    }
}
