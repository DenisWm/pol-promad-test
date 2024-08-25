package com.pol.promad.test.domain.legalprocess;

import com.pol.promad.test.domain.AggregateRoot;
import com.pol.promad.test.domain.defendant.DefendantID;
import com.pol.promad.test.domain.exceptions.NotificationException;
import com.pol.promad.test.domain.validation.ValidationHandler;
import com.pol.promad.test.domain.validation.handler.Notification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LegalProcess extends AggregateRoot<LegalProcessID> {

    private String number;
    private StatusProcess status;
    private StatusProcess pendingStatus;
    private List<DefendantID> defendants;


    private LegalProcess(final LegalProcessID anId, final String number, final StatusProcess status, final List<DefendantID> defendants) {
        super(anId);
        this.number = number;
        this.status = status;
        this.pendingStatus = null;
        this.defendants = defendants;
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
        return new LegalProcess(LegalProcessID.unique(), aNumber, StatusProcess.of(aStatus), new ArrayList<>());
    }
    public LegalProcess update(final String aStatus, final List<DefendantID> defendants) {
        StatusProcess status = StatusProcess.of(aStatus);
        this.pendingStatus = status;
        selfValidate();
        this.status = status;
        this.pendingStatus = null;
        this.defendants = new ArrayList<>(defendants != null ? defendants : Collections.emptyList());
        return this;
    }


    public static LegalProcess with(final LegalProcessID anId, final String number, final StatusProcess status, final List<DefendantID> defendants) {
        return new LegalProcess(anId, number, status, defendants);
    }

    public static LegalProcess with(final LegalProcess aLegalProcess) {
        return new LegalProcess(aLegalProcess.getId(), aLegalProcess.getNumber(), aLegalProcess.getStatus(), aLegalProcess.getDefendants());
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

    public List<DefendantID> getDefendants() {
        return Collections.unmodifiableList(defendants);
    }

    public void setDefendants(List<DefendantID> defendants) {
        this.defendants = defendants;
    }

    public LegalProcess addDefendant(final DefendantID defendantID) {
        if(defendantID == null) {
            return this;
        }
        this.defendants.add(defendantID);
        return this;
    }

    public LegalProcess addDefendants(final List<DefendantID> defendants) {
        if (defendants == null || defendants.isEmpty()) {
            return this;
        }
        this.defendants.addAll(defendants);
        return this;
    }

    public LegalProcess removeDefendant(final DefendantID defendantID) {
        if(defendantID == null) {
            return this;
        }
        this.defendants.remove(defendantID);
        return this;
    }
}
