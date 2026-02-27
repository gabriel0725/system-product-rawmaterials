package com.gabriel.stock.repository;

import com.gabriel.stock.entity.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawMaterialRepository extends JpaRepository<RawMaterial,Long> {
}
