package com.juliomesquita.core.shared.exception;

public class NoStackTraceException extends RuntimeException {
   public NoStackTraceException(String message) {
      super(message, null);
   }

   public NoStackTraceException(final String message, final Throwable cause) {
      super(message, cause, true, false);
   }
}
