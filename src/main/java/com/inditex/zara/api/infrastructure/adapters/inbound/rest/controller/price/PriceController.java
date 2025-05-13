package com.inditex.zara.api.infrastructure.adapters.inbound.rest.controller.price;

import com.inditex.zara.api.application.services.GetApplicablePriceService;
import com.inditex.zara.api.domain.exceptions.PriceNotFoundException;
import com.inditex.zara.api.domain.entities.Price;
import com.inditex.zara.api.infrastructure.adapters.inbound.rest.controller.price.dtos.PriceResponseDTO;
import com.inditex.zara.api.infrastructure.adapters.inbound.rest.controller.price.mapper.PriceMapper;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Tag(name = "Prices", description = "Operaciones para consulta de precios")
@Slf4j
@RestController
@RequestMapping("/api/v1/prices")
@RequiredArgsConstructor
public class PriceController {

    private final GetApplicablePriceService priceService;
    private final PriceMapper priceMapper;

    @Operation(
            summary = "Obtener precio aplicable",
            description = "Retorna el precio aplicable según fecha, productId y brandId"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Precio encontrado"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos o mal formateados"),
            @ApiResponse(responseCode = "404", description = "No se encontró precio aplicable"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<PriceResponseDTO> getPrice(
            @RequestParam(name="applicationDate", required = true)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,
            @RequestParam(name= "productId", required = true) Long productId,
            @RequestParam(name="brandId", required = true) Long brandId) {

        log.info("Received request to get price for productId={}, brandId={}, date={}",
                productId, brandId, applicationDate);

        Price price = priceService.getApplicablePrice(applicationDate, productId, brandId)
                .orElseThrow(() -> new PriceNotFoundException(productId, brandId));

        return ResponseEntity.ok(priceMapper.toResponse(price));
    }
}
