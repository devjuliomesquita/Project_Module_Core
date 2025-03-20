package com.juliomesquita.core.shared.exception;

import com.juliomesquita.core.shared.validations.Notification;

public class NotificationException extends GenericException {
   public NotificationException(final String message, final Notification notification) {
      super(message, notification.getErrors());
   }
}
