/**
 * @author Gabri
 */

package com.gabriel.stock.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "raw_material")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RawMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String name;

    private Integer stockQuantity;

    @OneToMany(mappedBy = "rawMaterial")
    @JsonManagedReference(value = "rawMaterial-productMaterial")
    private List<ProductMaterial> products;
}
