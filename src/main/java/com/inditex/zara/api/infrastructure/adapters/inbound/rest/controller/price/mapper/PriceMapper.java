package com.inditex.zara.api.infrastructure.adapters.inbound.rest.controller.price.mapper;

import com.inditex.zara.api.domain.entities.Price;
import com.inditex.zara.api.infrastructure.adapters.inbound.rest.controller.price.dtos.PriceResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    PriceResponseDTO toResponse(Price price);

}
