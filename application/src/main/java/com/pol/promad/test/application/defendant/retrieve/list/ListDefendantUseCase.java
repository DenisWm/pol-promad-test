package com.pol.promad.test.application.defendant.retrieve.list;

import com.pol.promad.test.application.UseCase;
import com.pol.promad.test.application.defendant.retrieve.get.DefendantOutput;
import com.pol.promad.test.domain.pagination.Pagination;
import com.pol.promad.test.domain.pagination.SearchQuery;

public abstract class ListDefendantUseCase extends UseCase<SearchQuery, Pagination<DefendantListOutput>> {
}
