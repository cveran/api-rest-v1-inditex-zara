package com.inditex.zara.api.infrastructure.adapters.inbound.rest.controller.price.dtos;

import java.time.LocalDateTime;

public record ErrorResponseDTO(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {}
