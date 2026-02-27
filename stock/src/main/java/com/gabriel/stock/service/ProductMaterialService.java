/**
 * @author Gabri
 */

package com.gabriel.stock.service;

import com.gabriel.stock.entity.Product;
import com.gabriel.stock.entity.ProductMaterial;
import com.gabriel.stock.entity.RawMaterial;
import com.gabriel.stock.exception.ResourceNotFoundException;
import com.gabriel.stock.repository.ProductMaterialRepository;
import com.gabriel.stock.repository.ProductRepository;
import com.gabriel.stock.repository.RawMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductMaterialService {

    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;
    private final ProductMaterialRepository repository;

    public ProductMaterial addMaterial(Long productId, Long materialId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        RawMaterial rawMaterial = rawMaterialRepository.findById(materialId)
                .orElseThrow(() -> new ResourceNotFoundException("Raw material not found"));

        ProductMaterial relation = ProductMaterial.builder()
                .product(product)
                .rawMaterial(rawMaterial)
                .requiredQuantity(quantity)
                .build();

        return repository.save(relation);
    }

    public ProductMaterial updateQuantity(Long id, Integer quantity) {
        ProductMaterial relation = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Association not found"));

        relation.setRequiredQuantity(quantity);
        return repository.save(relation);
    }

    public void delete(Long productMaterialId) {
        ProductMaterial relation = repository.findById(productMaterialId)
                .orElseThrow(() -> new ResourceNotFoundException("ProductMaterial not found"));

        repository.delete(relation);
    }

    @Service
    @RequiredArgsConstructor
    public class ProductService {
        private final ProductRepository repository;
        private final RawMaterialRepository rawMaterialRepository;
        private final ProductMaterialRepository productMaterialRepository;

        public Product update(Long id, Product updatedProduct){
            Product existingProduct = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            existingProduct.setCode(updatedProduct.getCode());
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setPrice(updatedProduct.getPrice());

            // Remove materiais antigos
            existingProduct.getMaterials().clear();
            repository.save(existingProduct); // necessário para limpar FK antes de adicionar novos

            // Adiciona materiais enviados pelo frontend
            if (updatedProduct.getMaterials() != null) {
                for (ProductMaterial pm : updatedProduct.getMaterials()) {
                    RawMaterial rm = rawMaterialRepository.findById(pm.getRawMaterial().getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Raw material not found"));

                    ProductMaterial newPm = ProductMaterial.builder()
                            .product(existingProduct)
                            .rawMaterial(rm)
                            .requiredQuantity(pm.getRequiredQuantity())
                            .build();

                    productMaterialRepository.save(newPm);
                    existingProduct.getMaterials().add(newPm);
                }
            }

            return repository.save(existingProduct);
        }
    }

}
