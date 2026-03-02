package com.gabriel.stock.dto.product;

/**
 * DTO (Data Transfer Object) utilizado para representar
 * a associação entre um produto e uma matéria-prima.
 *
 * Esse record é usado principalmente para:
 * - Receber dados da requisição
 * - Enviar dados na resposta
 * - Evitar expor diretamente a entidade ProductMaterial
 *
 * Como é um record, ele:
 * - É imutável
 * - Possui construtor automático
 * - Gera automaticamente getters
 * - Implementa equals, hashCode e toString
 */
public record ProductMaterialDTO(

        /**
         * ID da matéria-prima associada ao produto.
         * Usado para identificar qual RawMaterial está sendo referenciado.
         */
        Long rawMaterialId,

        /**
         * Nome da matéria-prima.
         * Geralmente utilizado para exibição no frontend.
         */
        String rawMaterialName,

        /**
         * Quantidade necessária dessa matéria-prima
         * para produzir o produto.
         */
        Integer requiredQuantity

) {
}