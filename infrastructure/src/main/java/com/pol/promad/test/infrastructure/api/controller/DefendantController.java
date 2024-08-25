package com.pol.promad.test.infrastructure.api.controller;

import com.pol.promad.test.application.defendant.create.CreateDefendantCommand;
import com.pol.promad.test.application.defendant.create.CreateDefendantUseCase;
import com.pol.promad.test.application.defendant.retrieve.get.GetDefendantByIdUseCase;
import com.pol.promad.test.application.defendant.retrieve.list.ListDefendantUseCase;
import com.pol.promad.test.domain.pagination.Pagination;
import com.pol.promad.test.domain.pagination.SearchQuery;
import com.pol.promad.test.infrastructure.api.DefendantAPI;
import com.pol.promad.test.infrastructure.defendant.models.CreateDefendantRequest;
import com.pol.promad.test.infrastructure.defendant.models.DefendantListResponse;
import com.pol.promad.test.infrastructure.defendant.models.DefendantResponse;
import com.pol.promad.test.infrastructure.defendant.presenters.DefendantApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class DefendantController implements DefendantAPI {

    private final CreateDefendantUseCase createDefendantUseCase;
    private final ListDefendantUseCase listDefendantUseCase;
    private final GetDefendantByIdUseCase getDefendantByIdUseCase;

    public DefendantController(
            final CreateDefendantUseCase createDefendantUseCase,
            final ListDefendantUseCase listDefendantUseCase,
            final GetDefendantByIdUseCase getDefendantByIdUseCase
    ) {
        this.createDefendantUseCase = Objects.requireNonNull(createDefendantUseCase);
        this.listDefendantUseCase = Objects.requireNonNull(listDefendantUseCase);
        this.getDefendantByIdUseCase = Objects.requireNonNull(getDefendantByIdUseCase);
    }

    @Override
    public ResponseEntity<?> createDefendant(final CreateDefendantRequest input) {
        final var aCommand = CreateDefendantCommand.with(input.name());
        final var output = this.createDefendantUseCase.execute(aCommand);
        return ResponseEntity.ok(URI.create("/defendants/" + output.id()));
    }

    @Override
    public Pagination<DefendantListResponse> listDefendant(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        final var aQuery = new SearchQuery(page, perPage, search, sort, direction);

        return this.listDefendantUseCase.execute(aQuery).
                map(DefendantApiPresenter::present);
    }

    @Override
    public DefendantResponse getById(final String id) {
        return DefendantApiPresenter.present(this.getDefendantByIdUseCase.execute(id));
    }
}
