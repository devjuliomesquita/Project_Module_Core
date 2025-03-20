package com.juliomesquita.core.shared.validations;

import com.juliomesquita.core.shared.exception.GenericException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Notification implements ValidationHandler {
   private final List<APIError> errors;

   private Notification(final List<APIError> errors) {
      this.errors = Objects.requireNonNull(errors);
   }

   public static Notification create() {
      return new Notification(new ArrayList<>());
   }

   public static Notification create(final APIError apiError) {
      return new Notification(new ArrayList<>()).append(apiError);
   }


   public static Notification create(final Throwable throwable) {
      return Notification.create(new APIError(throwable.getMessage()));
   }

   @Override
   public Notification append(final APIError apiError) {
      this.errors.add(apiError);
      return this;
   }

   @Override
   public Notification append(ValidationHandler anHandler) {
      this.errors.addAll(anHandler.getErrors());
      return this;
   }

   @Override
   public <T> T validate(Validation<T> aValidation) {
      try {
         return aValidation.validate();
      } catch (final GenericException ge) {
         this.errors.addAll(ge.getErrors());
      } catch (final Throwable e) {
         this.errors.add(new APIError(e.getMessage()));
      }
      return null;
   }

   @Override
   public List<APIError> getErrors() {
      return this.errors;
   }
}
