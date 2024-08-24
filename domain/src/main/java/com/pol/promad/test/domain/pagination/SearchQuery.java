package com.pol.promad.test.domain.pagination;

import java.awt.print.Pageable;

public record SearchQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction
) {
}
