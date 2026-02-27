/**
 * @author Gabri
 */

package com.gabriel.stock.repository;

import com.gabriel.stock.entity.Product;
import com.gabriel.stock.port.ProductFinder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductFinder {

}
