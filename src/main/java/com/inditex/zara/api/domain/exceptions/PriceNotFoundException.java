package com.inditex.zara.api.domain.exceptions;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException(Long productId, Long brandId) {
        super("No se encontró un precio para el productId " + productId + " y el brandId " + brandId);
    }
}

