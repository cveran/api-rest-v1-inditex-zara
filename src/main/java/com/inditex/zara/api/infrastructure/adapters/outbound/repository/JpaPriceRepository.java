package com.inditex.zara.api.infrastructure.adapters.outbound.repository;

import com.inditex.zara.api.domain.entities.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface JpaPriceRepository extends JpaRepository<Price, Long> {

    @Query("SELECT p FROM Price p WHERE p.productId = :productId AND p.brandId = :brandId " +
           "AND :applicationDate BETWEEN p.startDate AND p.endDate")
    List<Price> findApplicablePrices(LocalDateTime applicationDate, Long productId, Long brandId);
}

