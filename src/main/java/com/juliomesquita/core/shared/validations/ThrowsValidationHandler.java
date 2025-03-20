package com.juliomesquita.core.shared.validations;

import com.juliomesquita.core.shared.exception.GenericException;

import java.util.List;

public class ThrowsValidationHandler implements ValidationHandler {
   @Override
   public ValidationHandler append(final APIError APIError) {
      throw GenericException.with(APIError);
   }

   @Override
   public ValidationHandler append(ValidationHandler anHandler) {
      throw GenericException.with(anHandler.getErrors());
   }

   @Override
   public <T> T validate(Validation<T> aValidation) {
      try {
         return aValidation.validate();
      } catch (final Exception ex) {
         throw GenericException.with(new APIError(ex.getMessage()));
      }
   }

   @Override
   public List<APIError> getErrors() {
      return List.of();
   }
}
