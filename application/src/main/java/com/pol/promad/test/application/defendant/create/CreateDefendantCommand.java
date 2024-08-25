package com.pol.promad.test.application.defendant.create;

public record CreateDefendantCommand(String name) {
    public static CreateDefendantCommand with(String name) {
        return new CreateDefendantCommand(name);
    }
}
