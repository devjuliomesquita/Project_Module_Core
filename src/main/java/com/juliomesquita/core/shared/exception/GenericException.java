package com.juliomesquita.core.shared.exception;

import com.juliomesquita.core.shared.validations.APIError;

import java.util.List;

public class GenericException extends NoStackTraceException {
   private final List<APIError> APIErrors;

   protected GenericException(final String message, final List<APIError> errors) {
      super(message);
      this.APIErrors = errors;
   }

   public static GenericException with(final APIError anError){
      return  new GenericException("", List.of(anError));
   }

   public static GenericException with(final List<APIError> anErrors){
      return  new GenericException("", anErrors);
   }

   public List<APIError> getErrors() {
      return APIErrors;
   }
}
