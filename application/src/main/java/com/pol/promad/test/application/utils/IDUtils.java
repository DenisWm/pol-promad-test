package com.pol.promad.test.application.utils;


import com.pol.promad.test.domain.Identifier;

import java.util.List;

public final class IDUtils {

    private IDUtils(){
    }
    public static <T extends Identifier> List<String> asString(final List<T> ids) {
        return ids.stream().map(Identifier::getValue).toList();
    }
}
