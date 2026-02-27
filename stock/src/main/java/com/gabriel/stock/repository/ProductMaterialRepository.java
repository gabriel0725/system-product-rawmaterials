package com.gabriel.stock.repository;

import com.gabriel.stock.entity.ProductMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductMaterialRepository extends JpaRepository<ProductMaterial,Long> {
}
