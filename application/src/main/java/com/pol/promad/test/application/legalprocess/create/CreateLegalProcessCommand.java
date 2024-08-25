package com.pol.promad.test.application.legalprocess.create;

import java.util.List;

public record CreateLegalProcessCommand(String number, String status, List<String> defendants) {
    public static CreateLegalProcessCommand with(final String number, final String status, final List<String> defendants) {
        return new CreateLegalProcessCommand(number, status, defendants);
    }
}
