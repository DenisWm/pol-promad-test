package com.pol.promad.test.domain.defendant;

import com.pol.promad.test.domain.validation.Error;
import com.pol.promad.test.domain.validation.ValidationHandler;
import com.pol.promad.test.domain.validation.Validator;

public class DefendantValidator extends Validator {

    private final Defendant defendant;

    public static final int NAME_MAX_LENGTH = 255;
    public static final int NAME_MIN_LENGTH = 3;
    public static final String NAME_SHOULD_NOT_BE_NULL = "O nome do réu não pode ser nulo";
    public static final String NAME_SHOULD_NOT_BE_EMPTY = "O nome do réu não pode ser vazio";
    public static final String NAME_MUST_BE_BETWEEN_3_AND_255_CHARACTERS = "O nome do réu precisa ter entre 3 e 255 caracteres";

    public DefendantValidator(final ValidationHandler aHandler, Defendant defendant) {
        super(aHandler);
        this.defendant = defendant;
    }

    @Override
    public void validate() {
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        final var name = this.defendant.getName();
        if(name == null) {
            getHandler().append(new Error(NAME_SHOULD_NOT_BE_NULL));
            return;
        }
        if(name.isBlank()) {
            getHandler().append(new Error(NAME_SHOULD_NOT_BE_EMPTY));
            return;
        }
        final int nameLength = name.trim().length();
        if(nameLength < NAME_MIN_LENGTH || nameLength > NAME_MAX_LENGTH) {
            getHandler().append(new Error(NAME_MUST_BE_BETWEEN_3_AND_255_CHARACTERS));
        }
    }
}
