/**
 * @author Gabri
 */

package com.gabriel.stock.service;

import com.gabriel.stock.dto.production.ProductProductionDTO;
import com.gabriel.stock.dto.production.ProductionResponse;
import com.gabriel.stock.entity.Product;
import com.gabriel.stock.entity.ProductMaterial;
import com.gabriel.stock.port.ProductFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductionService {

    private final ProductFinder productRepository;

    public ProductionResponse calculateProduction() {
        List<Product> products = productRepository.findAllByOrderByPriceDesc();
        Map<Long, Integer> stockMap = new HashMap<>();

        products.stream()
                .flatMap(p -> p.getMaterials().stream())
                .forEach(pm -> stockMap.put(
                        pm.getRawMaterial().getId(),
                        pm.getRawMaterial().getStockQuantity()
                ));

        List<ProductProductionDTO> result = new ArrayList<>();
        BigDecimal grandTotal = BigDecimal.ZERO;

        for (Product product : products) {
            int maxUnits = Integer.MAX_VALUE;

            for (ProductMaterial pm : product.getMaterials()) {
                int avaliable = stockMap.get(pm.getRawMaterial().getId());
                int possible = avaliable / pm.getRequiredQuantity();
                maxUnits = Math.min(maxUnits, possible);
            }

            if (maxUnits > 0 && maxUnits != Integer.MAX_VALUE) {

                for (ProductMaterial pm : product.getMaterials()) {
                    Long materialId = pm.getRawMaterial().getId();
                    stockMap.put(materialId,
                            stockMap.get(materialId) - (pm.getRequiredQuantity() * maxUnits));
                }

                BigDecimal total = product.getPrice()
                        .multiply(BigDecimal.valueOf(maxUnits));

                grandTotal =  grandTotal.add(total);

                result.add(new ProductProductionDTO(
                        product.getName(),
                        maxUnits,
                        product.getPrice(),
                        total
                ));
            }
        }
        return new ProductionResponse(result,grandTotal);
    }
}
