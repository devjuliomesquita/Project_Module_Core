package com.juliomesquita.core.shared.exception;

import com.juliomesquita.core.shared.validations.APIError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(value = GenericException.class)
   public ResponseEntity<?> handleGenericException(final GenericException exception) {
      return ResponseEntity.unprocessableEntity()
              .body(APIErrorResponse.from(exception));
   }

   private record APIErrorResponse(String message, List<APIError> errors) {
      public static APIErrorResponse from(final GenericException exception) {
         return new APIErrorResponse(
                 exception.getMessage(),
                 exception.getErrors()
         );
      }
   }
}
