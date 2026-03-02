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

/**
 * Service responsável por gerenciar a associação
 * entre Product e RawMaterial através da entidade
 * intermediária ProductMaterial.
 *
 * Essa entidade intermediária é necessária porque
 * existe um campo adicional na relação:
 * requiredQuantity.
 *
 * @Service
 * Indica que esta classe é um componente de regra
 * de negócio gerenciado pelo Spring.
 */
@Service
@RequiredArgsConstructor
public class ProductMaterialService {

    /**
     * Repository responsável pelo acesso à entidade Product.
     */
    private final ProductRepository productRepository;

    /**
     * Repository responsável pelo acesso à entidade RawMaterial.
     */
    private final RawMaterialRepository rawMaterialRepository;

    /**
     * Repository responsável pela persistência
     * da entidade intermediária ProductMaterial.
     */
    private final ProductMaterialRepository repository;

    /**
     * Cria uma nova associação entre um produto
     * e uma matéria-prima.
     *
     * Processo:
     * 1. Valida se o produto existe.
     * 2. Valida se a matéria-prima existe.
     * 3. Cria a entidade intermediária.
     * 4. Persiste no banco.
     *
     * @param productId ID do produto.
     * @param materialId ID da matéria-prima.
     * @param quantity Quantidade necessária dessa matéria-prima.
     * @return Associação criada e persistida.
     */
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

    /**
     * Atualiza apenas a quantidade necessária
     * de uma associação existente.
     *
     * @param id ID da associação ProductMaterial.
     * @param quantity Nova quantidade necessária.
     * @return Associação atualizada.
     */
    public ProductMaterial updateQuantity(Long id, Integer quantity) {

        ProductMaterial relation = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Association not found"));

        relation.setRequiredQuantity(quantity);

        return repository.save(relation);
    }

    /**
     * Remove uma associação entre produto
     * e matéria-prima.
     *
     * @param productMaterialId ID da associação.
     */
    public void delete(Long productMaterialId) {

        ProductMaterial relation = repository.findById(productMaterialId)
                .orElseThrow(() -> new ResourceNotFoundException("ProductMaterial not found"));

        repository.delete(relation);
    }
}