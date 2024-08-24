package com.pol.promad.test.application.utils;

import com.pol.promad.test.domain.AggregateRoot;
import com.pol.promad.test.domain.Identifier;
import com.pol.promad.test.domain.exceptions.DomainException;
import com.pol.promad.test.domain.exceptions.NotFoundException;

import java.util.function.Supplier;

public final class IDNotFoundUtils {
    public static Supplier<DomainException> notFound(final Identifier anId, Class<? extends AggregateRoot<?>> anAggregate) {
        return () -> NotFoundException.with(anAggregate, anId);
    }
}
