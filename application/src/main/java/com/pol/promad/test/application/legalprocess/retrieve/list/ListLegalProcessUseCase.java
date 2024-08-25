package com.pol.promad.test.application.legalprocess.retrieve.list;

import com.pol.promad.test.application.UseCase;
import com.pol.promad.test.domain.pagination.Pagination;
import com.pol.promad.test.domain.pagination.SearchQuery;

public abstract class ListLegalProcessUseCase extends UseCase<SearchQuery, Pagination<LegalProcessListOutput>> {
}
