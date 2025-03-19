package com.juliomesquita.core.controllers.shared;

import java.util.Set;

public record RestAPIErrorResponse(
        int status,
        String message,
        Set<String> errors
) {
}
