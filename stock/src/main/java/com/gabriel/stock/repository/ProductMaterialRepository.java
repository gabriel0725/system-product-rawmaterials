package com.gabriel.stock.repository;

import com.gabriel.stock.entity.ProductMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository responsável pela comunicação com o banco de dados
 * para a entidade ProductMaterial.
 *
 * JpaRepository<ProductMaterial, Long>:
 *
 * - ProductMaterial → entidade que será gerenciada
 * - Long → tipo da chave primária (ID) da entidade
 *
 * Ao estender JpaRepository, o Spring Data JPA automaticamente
 * fornece implementações prontas para operações CRUD, como:
 *
 * - save()
 * - findById()
 * - findAll()
 * - deleteById()
 * - existsById()
 * - count()
 *
 * Não é necessário implementar nada manualmente.
 */
public interface ProductMaterialRepository extends JpaRepository<ProductMaterial, Long> {

    /**
     * Aqui poderiam ser declarados métodos de consulta personalizados,
     * seguindo o padrão de nomenclatura do Spring Data JPA.
     *
     * Exemplo:
     *
     * List<ProductMaterial> findByProductId(Long productId);
     *
     * O Spring gera automaticamente a query com base no nome do método.
     */
}