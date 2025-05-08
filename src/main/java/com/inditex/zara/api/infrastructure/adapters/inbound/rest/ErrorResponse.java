package com.inditex.zara.api.infrastructure.adapters.inbound.rest;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {}
