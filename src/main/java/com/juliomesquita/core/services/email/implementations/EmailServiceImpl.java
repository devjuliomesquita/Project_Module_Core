package com.juliomesquita.core.services.email.implementations;

import com.juliomesquita.core.services.email.dtos.EmailCustomDto;
import com.juliomesquita.core.services.email.interfaces.EmailService;
import com.juliomesquita.core.shared.validations.Notification;
import io.vavr.API;
import io.vavr.control.Either;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Component
public class EmailServiceImpl implements EmailService {
   private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
   private final JavaMailSender mailSender;
   private final TemplateEngine templateEngine;

   public EmailServiceImpl(
           final JavaMailSender mailSender,
           final TemplateEngine templateEngine
   ) {
      this.mailSender = Objects.requireNonNull(mailSender);
      this.templateEngine = Objects.requireNonNull(templateEngine);
   }

   @Async
   public void sendBulkEmails(final List<EmailCustomDto> emails) {
      emails.forEach(e -> CompletableFuture.runAsync(() ->
              this.sendCustomEmail(e.to(), e.subject(), e.template(), e.context(), e.files())));
   }

   public Either<Notification, Void> sendCustomEmail(final String to, final String subject, final String template, final Context context, final List<File> attachments) {
      final String bodyCustom = this.templateEngine.process(template, context);
      return API.<Void>Try(() -> {
                 this.sendEmail(to, subject, bodyCustom, attachments);
                 return null;
              })
              .toEither()
              .bimap(Notification::create, Function.identity());
   }

   public Either<Notification, Void> sendEmail(final String to, final String subject, final String body, final List<File> attachments) {
      final MimeMessage message = this.mailSender.createMimeMessage();
      return API.<Void>Try(() -> {
                 final MimeMessageHelper helper = new MimeMessageHelper(message, true);
                 helper.setTo(to);
                 helper.setSubject(subject);
                 helper.setText(body, true);
                 if (attachments != null) {
                    for (File f : attachments) {
                       helper.addAttachment(f.getName(), f);
                    }
                 }
                 this.mailSender.send(message);
                 return null;
              })
              .toEither()
              .bimap(Notification::create, Function.identity());
   }
}
