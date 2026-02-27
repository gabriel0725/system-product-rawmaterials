package com.gabriel.stock.dto.production;

import java.math.BigDecimal;

public record ProductProductionDTO(
        String productName,
        Integer quantityPossible,
        BigDecimal unitPrice,
        BigDecimal totalValue) {
}
