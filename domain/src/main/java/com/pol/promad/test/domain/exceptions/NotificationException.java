package com.pol.promad.test.domain.exceptions;


import com.pol.promad.test.domain.validation.handler.Notification;

public class NotificationException extends DomainException {

    public NotificationException(final String message, final Notification notification) {
        super(message, notification.getErrors());
    }
}
