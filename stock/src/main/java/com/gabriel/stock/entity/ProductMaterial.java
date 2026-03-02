/**
 * @author Gabri
 */

package com.gabriel.stock.entity;

// Controla serialização JSON para evitar recursão infinita
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

/**
 * Indica que esta classe é uma entidade JPA
 * e será mapeada para a tabela "product_material".
 *
 * Essa entidade representa a tabela intermediária do relacionamento
 * entre Product e RawMaterial.
 *
 * Ela funciona como uma entidade de associação (tabela de junção),
 * mas com atributo extra (requiredQuantity).
 */
@Entity
@Table(name = "product_material")

/**
 * Anotações do Lombok:
 * @Getter -> Gera getters
 * @Setter -> Gera setters
 * @NoArgsConstructor -> Construtor vazio (exigido pela JPA)
 * @AllArgsConstructor -> Construtor com todos os atributos
 * @Builder -> Permite construção usando padrão Builder
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductMaterial {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Relacionamento ManyToOne:
     * - Muitos ProductMaterial podem estar associados a um único Product.
     *
     * @JoinColumn(name = "product_id")
     * Define a coluna estrangeira no banco.
     *
     * Esse é o lado dono do relacionamento com Product.
     */
    @ManyToOne
    @JoinColumn(name = "product_id")

    /**
     * Indica que este lado é ignorado durante a serialização JSON
     * para evitar loop infinito.
     *
     * Ele é o lado "filho" do relacionamento
     * correspondente ao @JsonManagedReference em Product.
     */
    @JsonBackReference(value = "product-material")
    private Product product;

    /**
     * Relacionamento ManyToOne:
     * - Muitos ProductMaterial podem estar associados a uma única RawMaterial.
     *
     * fetch = FetchType.EAGER:
     * - A matéria-prima será carregada automaticamente junto com
     *   o ProductMaterial.
     *
     * @JoinColumn(name = "raw_material_id")
     * Define a chave estrangeira no banco.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "raw_material_id")

    /**
     * Indica que este lado é ignorado na serialização JSON
     * para evitar recursão infinita.
     *
     * Corresponde ao @JsonManagedReference definido em RawMaterial.
     */
    @JsonBackReference(value = "rawMaterial-productMaterial")
    private RawMaterial rawMaterial;

    @Column(nullable = false)
    private Integer requiredQuantity;
}