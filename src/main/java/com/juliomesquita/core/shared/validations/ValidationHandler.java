package com.juliomesquita.core.shared.validations;

import java.util.List;

public interface ValidationHandler {
   ValidationHandler append(APIError APIError);

   ValidationHandler append(ValidationHandler anHandler);

   <T> T validate(Validation<T> aValidation);

   List<APIError> getErrors();

   default boolean booleanHasError() {
      return getErrors() != null && !getErrors().isEmpty();
   }

   default APIError firstError() {
      if (booleanHasError()) {
         return getErrors().get(0);
      }
      return null;
   }

   interface Validation<T> {
      T validate();
   }
}
