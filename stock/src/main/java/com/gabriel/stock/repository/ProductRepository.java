/**
 * @author Gabri
 */

package com.gabriel.stock.repository;

import com.gabriel.stock.entity.Product;
import com.gabriel.stock.port.ProductFinder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository responsável pela persistência da entidade Product.
 *
 * Extende:
 *
 * 1) JpaRepository<Product, Long>
 *    - Fornece automaticamente operações CRUD
 *    - Product → entidade gerenciada
 *    - Long → tipo da chave primária
 *
 * 2) ProductFinder
 *    - Interface personalizada (porta da aplicação)
 *    - Permite separar regras de consulta customizadas
 *      da implementação padrão do JPA
 *
 * Essa abordagem segue um princípio próximo de
 * Arquitetura Limpa / Ports and Adapters.
 */
public interface ProductRepository extends JpaRepository<Product, Long>, ProductFinder {

    /**
     * Não é necessário implementar nada aqui.
     *
     * O Spring Data JPA:
     * - Cria automaticamente a implementação do JpaRepository
     * - Procura uma implementação concreta de ProductFinder
     *   (ex: ProductRepositoryImpl)
     * - Combina tudo em um único bean gerenciado pelo Spring
     */
}