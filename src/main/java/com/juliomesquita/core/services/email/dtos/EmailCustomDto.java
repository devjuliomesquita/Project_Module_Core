package com.juliomesquita.core.services.email.dtos;

import org.thymeleaf.context.Context;

import java.io.File;
import java.util.List;

public record EmailCustomDto(
        String to,
        String subject,
        String template,
        Context context,
        List<File> files
) {
}
