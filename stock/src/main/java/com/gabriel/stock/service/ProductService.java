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

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    public Product create(Product product){
        return repository.save(product);
    }

    public List<Product> findAll(){
        List<Product> all = repository.findAll();
        System.out.println("Produtos no banco: " + all);
        return all;
    }

    public Product findById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public Product update(Long id, Product updatedProduct){

        Product existingProduct = findById(id);

        existingProduct.setCode(updatedProduct.getCode());
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setPrice(updatedProduct.getPrice());

        // NÃO mexer na lista de materials aqui

        return repository.save(existingProduct);
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }

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

    public Product updateMaterials(Long id, List<ProductMaterialDTO> materialsDTO){

        Product product = findById(id);

        product.getMaterials().clear(); // aqui faz sentido limpar

        for (ProductMaterialDTO dto : materialsDTO) {

            RawMaterial rawMaterial = rawMaterialRepository.findById(dto.rawMaterialId())
                    .orElseThrow(() -> new ResourceNotFoundException("Raw material not found"));

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
