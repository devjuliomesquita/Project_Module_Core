package com.juliomesquita.core.services.email;

import com.juliomesquita.core.services.email.interfaces.EmailService;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SendEmailFacadeImpl implements SendEmailFacade {
   private final EmailService emailService;

   public SendEmailFacadeImpl(final EmailService emailService) {
      this.emailService = Objects.requireNonNull(emailService);
   }

   @Override
   public EmailService getEmailService() {
      return this.emailService;
   }
}
