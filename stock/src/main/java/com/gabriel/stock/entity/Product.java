/**
 * @author Gabri
 */

package com.gabriel.stock.entity;

// Import responsável por controlar serialização JSON e evitar loop infinito
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Indica que esta classe é uma entidade JPA
 * e será mapeada para uma tabela no banco de dados.
 */
@Entity

/**
 * Define explicitamente o nome da tabela no banco.
 */
@Table(name = "product")

/**
 * Anotações do Lombok:
 * @Getter -> Gera automaticamente os métodos getters
 * @Setter -> Gera automaticamente os métodos setters
 * @NoArgsConstructor -> Gera construtor sem argumentos
 * @AllArgsConstructor -> Gera construtor com todos os atributos
 * @Builder -> Permite usar o padrão Builder para criar objetos
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    /**
     * Define o campo como chave primária da entidade.
     */
    @Id

    /**
     * Define que o valor será gerado automaticamente pelo banco,
     * usando estratégia de auto incremento (IDENTITY).
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    //BigDecimal é utilizado para evitar problemas de precisão em valores monetários.
    @Column(nullable = false)
    private BigDecimal price;

    /**
     * Define um relacionamento OneToMany:
     * - Um produto pode ter vários materiais associados (ProductMaterial).
     * - "mappedBy = product" indica que o lado dono do relacionamento
     *   está na entidade ProductMaterial.
     * - cascade = CascadeType.ALL -> qualquer operação (persist, remove, update)
     *   feita em Product será propagada para ProductMaterial.
     * - orphanRemoval = true -> se um material for removido da lista,
     *   ele será deletado automaticamente do banco.
     */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)

    /**
     * Controla a serialização JSON para evitar recursão infinita
     * no relacionamento bidirecional com ProductMaterial.
     * Esse é o lado "gerenciador" da relação.
     */
    @JsonManagedReference(value = "product-material")

    /**
     * Lista de materiais associados ao produto.
     * Inicializada para evitar NullPointerException.
     */
    private List<ProductMaterial> materials = new ArrayList<>();

}