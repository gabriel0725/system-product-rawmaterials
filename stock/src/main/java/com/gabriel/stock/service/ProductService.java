/**
 * @author Gabri
 */

package com.gabriel.stock.service;

import com.gabriel.stock.dto.product.ProductMaterialDTO;
import com.gabriel.stock.dto.product.ProductResponseDTO;
import com.gabriel.stock.entity.Product;
import com.gabriel.stock.entity.ProductMaterial;
import com.gabriel.stock.entity.RawMaterial;
import com.gabriel.stock.exception.ResourceNotFoundException;
import com.gabriel.stock.repository.ProductRepository;
import com.gabriel.stock.repository.RawMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Camada de serviço responsável pelas regras de negócio
 * relacionadas à entidade Product.
 *
 * @Service:
 * Indica que essa classe é um componente gerenciado pelo Spring.
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    /**
     * Repository principal de Product.
     * Injetado via construtor (boa prática).
     */
    private final ProductRepository repository;

    /**
     * Repository de RawMaterial.
     * Está sendo injetado via @Autowired em campo.
     */
    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    /**
     * Salva um novo produto no banco.
     */
    public Product create(Product product){
        return repository.save(product);
    }

    /**
     * Retorna todos os produtos cadastrados.
     */
    public List<Product> findAll(){
        List<Product> all = repository.findAll();

        // Log simples para debug
        System.out.println("Produtos no banco: " + all);

        return all;
    }

    /**
     * Busca produto por ID.
     * Caso não exista, lança exceção personalizada.
     */
    public Product findById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    /**
     * Atualiza dados básicos do produto.
     */
    public Product update(Long id, Product updatedProduct){

        // Garante que o produto existe
        Product existingProduct = findById(id);

        // Atualiza apenas campos permitidos
        existingProduct.setCode(updatedProduct.getCode());
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setPrice(updatedProduct.getPrice());

        return repository.save(existingProduct);
    }

    /**
     * Remove produto pelo ID.
     */
    public void deleteById(Long id){
        repository.deleteById(id);
    }

    /**
     * Converte entidade Product para DTO.
     *
     * Evita expor diretamente a entidade JPA ao cliente.
     */
    public ProductResponseDTO toDTO(Product product) {

        return new ProductResponseDTO(
                product.getId(),
                product.getCode(),
                product.getName(),
                product.getPrice(),
                product.getMaterials().stream()
                        .map(pm -> new ProductMaterialDTO(
                                pm.getRawMaterial().getId(),
                                pm.getRawMaterial().getName(),
                                pm.getRequiredQuantity()
                        ))
                        .collect(Collectors.toList())
        );
    }

    /**
     * Atualiza as matérias-primas associadas a um produto.
     *
     * Processo:
     * 1. Busca o produto
     * 2. Remove relações antigas
     * 3. Cria novas relações
     * 4. Salva novamente
     */
    public Product updateMaterials(Long id, List<ProductMaterialDTO> materialsDTO){

        Product product = findById(id);

        // Remove relações anteriores
        product.getMaterials().clear();

        for (ProductMaterialDTO dto : materialsDTO) {

            // Busca matéria-prima pelo ID
            RawMaterial rawMaterial = rawMaterialRepository.findById(dto.rawMaterialId())
                    .orElseThrow(() -> new ResourceNotFoundException("Raw material not found"));

            // Cria nova relação Produto ↔ Matéria-prima
            ProductMaterial relation = ProductMaterial.builder()
                    .product(product)
                    .rawMaterial(rawMaterial)
                    .requiredQuantity(dto.requiredQuantity())
                    .build();

            product.getMaterials().add(relation);
        }

        return repository.save(product);
    }
}