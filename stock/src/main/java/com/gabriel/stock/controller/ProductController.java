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

/**
 * Indica que esta classe é um Controller REST.
 *
 * @RestController combina:
 * - @Controller
 * - @ResponseBody (retorna JSON automaticamente)
 */
@RestController

/**
 * Define o endpoint base para todos os métodos deste controller.
 * Todas as rotas começarão com /products.
 */
@RequestMapping("/products")

/**
 * Lombok:
 * Gera automaticamente um construtor com todos os atributos finais (final).
 * Isso permite injeção de dependência via construtor.
 */
@RequiredArgsConstructor
public class ProductController {

    /**
     * Service responsável pela regra de negócio relacionada a Product.
     *
     * "final" + @RequiredArgsConstructor
     * → Spring injeta automaticamente via construtor.
     */
    private final ProductService service;

    /**
     * Endpoint para criar um novo produto.
     *
     * POST /products
     */
    @PostMapping

    /**
     * Define explicitamente que o status HTTP retornado será 201 (CREATED).
     */
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDTO create(@RequestBody Product product) {

        /**
         * @RequestBody:
         * Converte automaticamente o JSON enviado no corpo da requisição
         * em um objeto Product.
         */
        Product created = service.create(product);

        /**
         * Converte a entidade para DTO antes de retornar,
         * evitando expor diretamente a entidade ao cliente.
         */
        return service.toDTO(created);
    }

    /**
     * Endpoint para listar todos os produtos.
     *
     * GET /products
     */
    @GetMapping
    public List<ProductResponseDTO> findAll() {

        /**
         * Busca todos os produtos no service.
         * Depois converte cada entidade em DTO usando Stream API.
         */
        return service.findAll()
                .stream()
                .map(service::toDTO)
                .toList();
    }

    /**
     * Endpoint para buscar um produto pelo ID.
     *
     * GET /products/{id}
     */
    @GetMapping("/{id}")
    public ProductResponseDTO findById(@PathVariable Long id) {

        /**
         * @PathVariable:
         * Captura o valor do {id} presente na URL.
         */
        return service.toDTO(service.findById(id));
    }

    /**
     * Endpoint para atualizar um produto existente.
     *
     * PUT /products/{id}
     */
    @PutMapping("/{id}")
    public ProductResponseDTO update(@PathVariable Long id, @RequestBody Product product) {

        /**
         * Envia o ID da URL e os dados atualizados para o service.
         */
        Product updated = service.update(id, product);

        /**
         * Retorna o produto atualizado convertido em DTO.
         */
        return service.toDTO(updated);
    }

    /**
     * Endpoint específico para atualizar apenas os materiais
     * associados a um produto.
     *
     * PUT /products/{id}/materials
     */
    @PutMapping("/{id}/materials")
    public ProductResponseDTO updateMaterials(
            @PathVariable Long id,

            /**
             * Recebe uma lista de DTOs representando os materiais
             * e suas respectivas quantidades necessárias.
             */
            @RequestBody List<ProductMaterialDTO> materials
    ) {

        /**
         * Delegação da regra de negócio para o service.
         */
        Product updated = service.updateMaterials(id, materials);

        /**
         * Retorna o produto atualizado convertido em DTO.
         */
        return service.toDTO(updated);
    }

    /**
     * Endpoint para remover um produto pelo ID.
     *
     * DELETE /products/{id}
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        /**
         * Chama o service para remover o produto.
         * Não retorna corpo na resposta (status padrão 200 ou 204).
         */
        service.deleteById(id);
    }
}