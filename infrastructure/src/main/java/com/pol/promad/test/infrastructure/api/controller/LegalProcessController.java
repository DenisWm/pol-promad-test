package com.pol.promad.test.infrastructure.api.controller;

import com.pol.promad.test.application.legalprocess.create.CreateLegalProcessCommand;
import com.pol.promad.test.application.legalprocess.create.CreateLegalProcessUseCase;
import com.pol.promad.test.application.legalprocess.delete.DeleteLegalProcessUseCase;
import com.pol.promad.test.application.legalprocess.retrieve.get.GetLegalProcessByIdUseCase;
import com.pol.promad.test.application.legalprocess.retrieve.list.ListLegalProcessUseCase;
import com.pol.promad.test.application.legalprocess.update.UpdateLegalProcessCommand;
import com.pol.promad.test.application.legalprocess.update.UpdateLegalProcessUseCase;
import com.pol.promad.test.domain.pagination.Pagination;
import com.pol.promad.test.domain.pagination.SearchQuery;
import com.pol.promad.test.infrastructure.api.LegalProcessAPI;
import com.pol.promad.test.infrastructure.legalprocess.models.CreateLegalProcessRequest;
import com.pol.promad.test.infrastructure.legalprocess.models.LegalProcessListResponse;
import com.pol.promad.test.infrastructure.legalprocess.models.LegalProcessResponse;
import com.pol.promad.test.infrastructure.legalprocess.models.UpdateLegalProcessRequest;
import com.pol.promad.test.infrastructure.legalprocess.presenters.LegalProcessApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class LegalProcessController implements LegalProcessAPI {

    private final CreateLegalProcessUseCase createLegalProcessUseCase;
    private final GetLegalProcessByIdUseCase getLegalProcessByIdUseCase;
    private final UpdateLegalProcessUseCase updateLegalProcessUseCase;
    private final DeleteLegalProcessUseCase deleteLegalProcessUseCase;
    private final ListLegalProcessUseCase listLegalProcessUseCase;

    public LegalProcessController(
            final CreateLegalProcessUseCase createLegalProcessUseCase,
            final GetLegalProcessByIdUseCase getLegalProcessByIdUseCase,
            final UpdateLegalProcessUseCase updateLegalProcessUseCase,
            final DeleteLegalProcessUseCase deleteLegalProcessUseCase,
            final ListLegalProcessUseCase listLegalProcessUseCase
    ) {
        this.createLegalProcessUseCase = Objects.requireNonNull(createLegalProcessUseCase);
        this.getLegalProcessByIdUseCase = Objects.requireNonNull(getLegalProcessByIdUseCase);
        this.updateLegalProcessUseCase = Objects.requireNonNull(updateLegalProcessUseCase);
        this.deleteLegalProcessUseCase = Objects.requireNonNull(deleteLegalProcessUseCase);
        this.listLegalProcessUseCase = Objects.requireNonNull(listLegalProcessUseCase);
    }
    @Override
    public ResponseEntity<?> createLegalProcess(final CreateLegalProcessRequest anInput) {
        final var aCommand = CreateLegalProcessCommand.with(
                anInput.number(),
                anInput.status(),
                anInput.defendants()
        );
        final var output = createLegalProcessUseCase.execute(aCommand);
        return ResponseEntity.created(URI.create("/legal-processes/" + output.id())).body(output);
    }

    @Override
    public Pagination<LegalProcessListResponse> listLegalProcesses(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        final var aQuery = new SearchQuery(page, perPage, search, sort, direction);
        return listLegalProcessUseCase.execute(aQuery).map(LegalProcessApiPresenter::present);
    }

    @Override
    public LegalProcessResponse getById(final String id) {
        return LegalProcessApiPresenter.present(this.getLegalProcessByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateLegalProcessById(final String id, final UpdateLegalProcessRequest input) {
        final var aCommand = UpdateLegalProcessCommand.with(
                id,
                input.status(),
                input.defendants()
        );
        final var output = updateLegalProcessUseCase.execute(aCommand);
        return ResponseEntity.ok(output);
    }

    @Override
    public void deleteLegalProcessById(final String id) {
        this.deleteLegalProcessUseCase.execute(id);
    }
}
