package com.gabriel.stock.repository;

import com.gabriel.stock.entity.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository responsável pela persistência da entidade RawMaterial.
 *
 * Ao estender JpaRepository<RawMaterial, Long>, o Spring Data JPA
 * cria automaticamente a implementação dessa interface em tempo de execução.
 *
 * Generics:
 * - RawMaterial → entidade que será gerenciada
 * - Long → tipo da chave primária (ID)
 *
 * Métodos já disponíveis automaticamente:
 * - save()
 * - findById()
 * - findAll()
 * - deleteById()
 * - existsById()
 * - count()
 *
 * Nenhuma implementação manual é necessária.
 */
public interface RawMaterialRepository extends JpaRepository<RawMaterial, Long> {

    /**
     * Aqui você pode adicionar consultas personalizadas,
     * seguindo o padrão de nomenclatura do Spring Data.
     *
     * Exemplos:
     *
     * Optional<RawMaterial> findByName(String name);
     *
     * List<RawMaterial> findByQuantityLessThan(Integer quantity);
     *
     * O Spring gera automaticamente as queries com base no nome do metodo.
     */
}