/**
 * @author Gabri
 */

package com.gabriel.stock.controller;

import com.gabriel.stock.entity.ProductMaterial;
import com.gabriel.stock.service.ProductMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product-materials")
@RequiredArgsConstructor
public class ProductMaterialController {

    private final ProductMaterialService service;

    @PostMapping
    public ResponseEntity<ProductMaterial> addMaterial(
            @RequestParam Long productId,
            @RequestParam Long materialId,
            @RequestParam Integer quantity) {

        return ResponseEntity.ok(
                service.addMaterial(productId, materialId, quantity)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductMaterial> updateQuantity(
            @PathVariable Long id,
            @RequestParam Integer quantity) {

        return ResponseEntity.ok(
                service.updateQuantity(id, quantity)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}