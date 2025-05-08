package com.inditex.zara.api.application.ports.outbound;

import com.inditex.zara.api.domain.entities.Price;

import java.time.LocalDateTime;
import java.util.List;


public interface PriceRepository {

    /**
     * Busca todos los precios aplicables a un producto de una marca en una fecha dada.
     * 
     * @param applicationDate Fecha para la que se quiere obtener el precio.
     * @param productId       Identificador del producto.
     * @param brandId         Identificador de la cadena/marca.
     * @return Lista de precios aplicables (posiblemente más de uno, se seleccionará el de mayor prioridad en la capa de aplicación).
     */
    List<Price> findApplicablePrices(LocalDateTime applicationDate, Long productId, Long brandId);
}

