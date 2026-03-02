/**
 * @author Gabri
 */

package com.gabriel.stock.controller;

import com.gabriel.stock.entity.ProductMaterial;
import com.gabriel.stock.service.ProductMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST responsável por gerenciar a associação
 * entre Product e RawMaterial (entidade ProductMaterial).
 *
 * Essa entidade representa a relação entre produto e matéria-prima,
 * incluindo a quantidade necessária.
 */
@RestController

/**
 * Define o endpoint base da classe.
 * Todas as rotas começam com /product-materials.
 */
@RequestMapping("/product-materials")
@RequiredArgsConstructor
public class ProductMaterialController {

    /**
     * Service responsável pela regra de negócio
     * da entidade ProductMaterial.
     */
    private final ProductMaterialService service;

    /**
     * Endpoint para associar uma matéria-prima a um produto,
     * informando a quantidade necessária.
     *
     * POST /product-materials
     *
     * Utiliza @RequestParam para receber parâmetros via query string.
     *
     * Exemplo de requisição:
     * POST /product-materials?productId=1&materialId=2&quantity=5
     */
    @PostMapping
    public ResponseEntity<ProductMaterial> addMaterial(

            /**
             * ID do produto ao qual a matéria-prima será associada.
             */
            @RequestParam Long productId,

            /**
             * ID da matéria-prima que será associada ao produto.
             */
            @RequestParam Long materialId,

            /**
             * Quantidade necessária dessa matéria-prima para o produto.
             */
            @RequestParam Integer quantity) {

        /**
         * ResponseEntity:
         * Permite controlar explicitamente o status HTTP da resposta.
         *
         * Aqui está retornando 200 OK com o objeto criado/atualizado.
         */
        return ResponseEntity.ok(
                service.addMaterial(productId, materialId, quantity)
        );
    }

    /**
     * Endpoint para atualizar apenas a quantidade
     * de um ProductMaterial existente.
     *
     * PUT /product-materials/{id}?quantity=10
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductMaterial> updateQuantity(

            /**
             * ID da associação ProductMaterial.
             */
            @PathVariable Long id,

            /**
             * Nova quantidade necessária.
             */
            @RequestParam Integer quantity) {

        return ResponseEntity.ok(
                service.updateQuantity(id, quantity)
        );
    }

    /**
     * Endpoint para remover uma associação entre
     * produto e matéria-prima.
     *
     * DELETE /product-materials/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        /**
         * Executa a exclusão via service.
         */
        service.delete(id);

        /**
         * Retorna 204 No Content indicando
         * que a operação foi realizada com sucesso
         * e não há corpo na resposta.
         */
        return ResponseEntity.noContent().build();
    }
}