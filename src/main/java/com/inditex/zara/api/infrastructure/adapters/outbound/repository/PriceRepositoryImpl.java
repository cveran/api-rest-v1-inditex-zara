package com.inditex.zara.api.infrastructure.adapters.outbound.repository;

import com.inditex.zara.api.domain.entities.Price;
import com.inditex.zara.api.application.ports.outbound.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PriceRepositoryImpl implements PriceRepository {

    private final JpaPriceRepository jpaPriceRepository;

    @Override
    public List<Price> findApplicablePrices(LocalDateTime applicationDate, Long productId, Long brandId) {
        return jpaPriceRepository.findApplicablePrices(applicationDate, productId, brandId);
    }
}

