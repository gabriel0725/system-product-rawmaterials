package com.gabriel.stock.port;

import com.gabriel.stock.entity.Product;

import java.util.List;

/**
 * Interface que define uma "porta" (port) da aplicação.
 *
 * ProductFinder representa um contrato para consultas
 * específicas relacionadas à entidade Product.
 *
 *
 * A regra de negócio depende da interface (contrato),
 * não da implementação concreta do JPA.
 */
public interface ProductFinder {

    /**
     * Retorna todos os produtos ordenados por preço
     * em ordem decrescente.
     *
     * findAllByOrderByPriceDesc:
     * - Metodo segue a convenção de nomenclatura do Spring Data JPA
     * - O nome já descreve a query:
     *      SELECT * FROM product ORDER BY price DESC
     *
     * Mesmo estando na "porta", a implementação real
     * será fornecida pelo ProductRepository.
     */
    List<Product> findAllByOrderByPriceDesc();
}