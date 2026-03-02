/**
 * @author Gabri
 */

package com.gabriel.stock.entity;

// Controla serialização JSON para evitar loop infinito em relacionamentos bidirecionais
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;


@Entity
@Table(name = "raw_material")

/**
 * Anotações do Lombok:
 * @Getter -> Gera automaticamente os métodos getters
 * @Setter -> Gera automaticamente os métodos setters
 * @NoArgsConstructor -> Construtor vazio (necessário para JPA)
 * @AllArgsConstructor -> Construtor com todos os atributos
 * @Builder -> Permite usar o padrão Builder para criação de objetos
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RawMaterial {


    @Id

    /**
     * Define que o valor será gerado automaticamente pelo banco de dados
     * utilizando auto incremento (IDENTITY).
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String name;

    private Integer stockQuantity;

    /**
     * Relacionamento OneToMany:
     * - Uma matéria-prima pode estar associada a vários ProductMaterial.
     * - "mappedBy = rawMaterial" indica que o lado dono do relacionamento
     *   está na entidade ProductMaterial.
     * - Como não há cascade definido, as operações não são propagadas automaticamente.
     */
    @OneToMany(mappedBy = "rawMaterial")

    /**
     * Controla a serialização JSON para evitar recursão infinita
     * no relacionamento bidirecional com ProductMaterial.
     * Esse é o lado "gerenciador" da relação no contexto de serialização.
     */
    @JsonManagedReference(value = "rawMaterial-productMaterial")

    /**
     * Lista de associações entre esta matéria-prima e os produtos
     * que a utilizam.
     */
    private List<ProductMaterial> products;
}