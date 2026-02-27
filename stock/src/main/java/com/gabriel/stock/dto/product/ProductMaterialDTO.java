package com.gabriel.stock.dto.product;


public record ProductMaterialDTO(
        Long rawMaterialId,
        String rawMaterialName,
        Integer requiredQuantity
) {
}
