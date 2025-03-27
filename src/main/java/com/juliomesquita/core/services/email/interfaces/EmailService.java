package com.juliomesquita.core.services.email.interfaces;

import com.juliomesquita.core.services.email.dtos.EmailCustomDto;
import com.juliomesquita.core.shared.validations.Notification;
import io.vavr.control.Either;
import org.thymeleaf.context.Context;

import java.io.File;
import java.util.List;

public interface EmailService {
   void sendBulkEmails(List<EmailCustomDto> emails);

   Either<Notification, Void> sendCustomEmail(String to, String subject, String template, Context context, List<File> attachments);

   Either<Notification, Void> sendEmail(String to, String subject, String body, List<File> attachments);
}
