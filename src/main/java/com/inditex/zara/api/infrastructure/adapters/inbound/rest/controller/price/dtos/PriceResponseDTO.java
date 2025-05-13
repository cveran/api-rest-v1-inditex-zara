package com.inditex.zara.api.infrastructure.adapters.inbound.rest.controller.price.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public record PriceResponseDTO(
        Long productId,
        Long brandId,
        Integer priceList,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal price,
        String currency
) {}


