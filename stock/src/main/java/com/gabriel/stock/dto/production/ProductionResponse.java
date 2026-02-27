package com.gabriel.stock.dto.production;

import java.math.BigDecimal;
import java.util.List;

public record ProductionResponse(
        List<ProductProductionDTO> products,
        BigDecimal grandTotal
) {
}
