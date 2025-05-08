package com.inditex.zara.api.domain.exceptions;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException(Long productId, Long brandId) {
        super("No price found for productId=" + productId + " and brandId=" + brandId);
    }
}

