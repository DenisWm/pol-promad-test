package com.pol.promad.test.domain.defendant;

import com.pol.promad.test.domain.AggregateRoot;
import com.pol.promad.test.domain.exceptions.NotificationException;
import com.pol.promad.test.domain.validation.ValidationHandler;
import com.pol.promad.test.domain.validation.handler.Notification;

import java.util.Objects;

public class Defendant extends AggregateRoot<DefendantID> {

    private String name;

    private Defendant(final DefendantID id, final String name) {
        super(id);
        this.name = name;
        selfValidate();
    }

    public static Defendant newDefendant(final String name) {
        return new Defendant(DefendantID.unique(), name);
    }
    @Override
    public void validate(ValidationHandler handler) {
        new DefendantValidator(handler, this).validate();
    }

    private void selfValidate() {
        final var notification = Notification.create();

        validate(notification);

        if(notification.hasErrors()) {
            throw new NotificationException("", notification);
        }
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Defendant defendant = (Defendant) o;

        return Objects.equals(name, defendant.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
