/**
 * @author Gabri
 */

package com.gabriel.stock.controller;

import com.gabriel.stock.dto.product.ProductMaterialDTO;
import com.gabriel.stock.dto.product.ProductResponseDTO;
import com.gabriel.stock.entity.Product;
import com.gabriel.stock.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDTO create(@RequestBody Product product) {
        Product created = service.create(product);
        return service.toDTO(created);
    }

    @GetMapping
    public List<ProductResponseDTO> findAll() {
        return service.findAll()
                .stream()
                .map(service::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ProductResponseDTO findById(@PathVariable Long id) {
        return service.toDTO(service.findById(id));
    }

    @PutMapping("/{id}")
    public ProductResponseDTO update(@PathVariable Long id, @RequestBody Product product) {
        Product updated = service.update(id, product);
        return service.toDTO(updated);
    }

    @PutMapping("/{id}/materials")
    public ProductResponseDTO updateMaterials(
            @PathVariable Long id,
            @RequestBody List<ProductMaterialDTO> materials
    ) {
        Product updated = service.updateMaterials(id, materials);
        return service.toDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}