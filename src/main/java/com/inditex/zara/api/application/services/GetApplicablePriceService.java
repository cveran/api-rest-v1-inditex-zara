package com.inditex.zara.api.application.services;

import com.inditex.zara.api.domain.entities.Price;
import com.inditex.zara.api.application.ports.outbound.PriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetApplicablePriceService {

    private final PriceRepository priceRepository;

    /**
     * Devuelve el precio aplicable con mayor prioridad para una fecha, producto y marca dados.
     *
     * @param applicationDate Fecha para la que se consulta el precio.
     * @param productId       Identificador del producto.
     * @param brandId         Identificador de la marca.
     * @return El precio aplicable (si existe).
     */
    public Optional<Price> getApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        log.debug("Fetching applicable price for date={}, productId={}, brandId={}",
                applicationDate, productId, brandId);

        return priceRepository.findApplicablePrices(applicationDate, productId, brandId).stream()
                .peek(p -> log.trace("Candidate price found: {}", p))
                .max(Comparator.comparingInt(Price::getPriority));
    }
}
