package com.gabriel.stock.dto.product;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO (Data Transfer Object) utilizado para representar
 * os dados retornados de um Product na API.
 *
 * Esse DTO é usado para:
 * - Evitar expor diretamente a entidade Product
 * - Controlar exatamente quais dados serão enviados ao cliente
 * - Separar a camada de domínio da camada de apresentação
 *
 * Como é um record:
 * - É imutável
 * - Possui construtor automático
 * - Gera automaticamente getters
 * - Implementa equals, hashCode e toString
 */
public record ProductResponseDTO(

        Long id,
        String code,
        String name,
        /**
         * Preço do produto.
         *
         * BigDecimal é utilizado para garantir precisão
         * em valores monetários.
         */
        BigDecimal price,

        /**
         * Lista de matérias-primas associadas ao produto.
         *
         * Utiliza ProductMaterialDTO para evitar expor
         * diretamente a entidade ProductMaterial.
         */
        List<ProductMaterialDTO> materials

) {
}