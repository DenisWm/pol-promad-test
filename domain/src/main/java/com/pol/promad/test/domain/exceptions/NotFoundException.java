package com.pol.promad.test.domain.exceptions;

import com.pol.promad.test.domain.AggregateRoot;
import com.pol.promad.test.domain.Identifier;
import com.pol.promad.test.domain.validation.Error;

import java.util.Collections;
import java.util.List;

public class NotFoundException extends DomainException{

    protected NotFoundException(String aMessage, List<Error> anErrors) {
        super(aMessage, anErrors);
    }

    public static NotFoundException with(
           final Class<? extends AggregateRoot<?>> anAggregate,
           final Identifier id
    ) {
        final var anErrorMessage = "%s com ID %s não foi encontrado".formatted(anAggregate.getSimpleName(), id.getValue());
        return new NotFoundException(anErrorMessage, Collections.emptyList());
    }
}
