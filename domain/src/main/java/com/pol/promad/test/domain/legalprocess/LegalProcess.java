package com.pol.promad.test.domain.legalprocess;

import com.pol.promad.test.domain.AggregateRoot;
import com.pol.promad.test.domain.exceptions.DomainException;
import com.pol.promad.test.domain.exceptions.NotificationException;
import com.pol.promad.test.domain.validation.Error;
import com.pol.promad.test.domain.validation.ValidationHandler;
import com.pol.promad.test.domain.validation.handler.Notification;
import com.pol.promad.test.domain.validation.handler.ThrowsValidationHandler;

public class LegalProcess extends AggregateRoot<LegalProcessID> {

    private String number;
    private StatusProcess status;
    private StatusProcess pendingStatus;

    private LegalProcess(final LegalProcessID anId, final String number, final StatusProcess status) {
        super(anId);
        this.number = number;
        this.status = status;
        this.pendingStatus = null;
        selfValidate();
    }

    private void selfValidate() {
        final var notification = Notification.create();

        validate(notification);

        if(notification.hasErrors()) {
            throw new NotificationException("", notification);
        }
    }

    public static LegalProcess newLegalProcess(final String aNumber, final String aStatus) {
        return new LegalProcess(LegalProcessID.unique(), aNumber, StatusProcess.of(aStatus));
    }
    public LegalProcess update(final String aStatus) {
        StatusProcess status = StatusProcess.of(aStatus);
        this.pendingStatus = status;
        selfValidate();
        this.status = status;
        this.pendingStatus = null;
        return this;
    }


    public static LegalProcess with(final LegalProcessID anId, final String number, final StatusProcess status) {
        return new LegalProcess(anId, number, status);
    }

    public static LegalProcess with(final LegalProcess aLegalProcess) {
        return new LegalProcess(aLegalProcess.getId(), aLegalProcess.getNumber(), aLegalProcess.getStatus());
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public StatusProcess getStatus() {
        return status;
    }

    public void setStatus(StatusProcess status) {
        this.status = status;
    }

    @Override
    public void validate(ValidationHandler handler) {
        new LegalProcessValidator(handler, this).validate();
    }

    public StatusProcess getPendingStatus() {
        return pendingStatus;
    }

    public void setPendingStatus(StatusProcess pendingStatus) {
        this.pendingStatus = pendingStatus;
    }
}
