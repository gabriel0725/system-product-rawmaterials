package com.gabriel.stock.dto.product;

import java.math.BigDecimal;
import java.util.List;

public record ProductResponseDTO(
        Long id,
        String code,
        String name,
        BigDecimal price,
        List<ProductMaterialDTO> materials
) {
}