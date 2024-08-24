package com.pol.promad.test.application.legalprocess.create;

public record CreateLegalProcessCommand(String number, String status) {
    public static CreateLegalProcessCommand with(final String number, final String status) {
        return new CreateLegalProcessCommand(number, status);
    }
}
