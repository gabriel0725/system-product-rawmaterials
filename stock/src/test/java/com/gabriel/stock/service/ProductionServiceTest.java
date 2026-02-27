/**
 * @author Gabri
 */

package com.gabriel.stock.service;

import com.gabriel.stock.entity.Product;
import com.gabriel.stock.entity.ProductMaterial;
import com.gabriel.stock.entity.RawMaterial;
import com.gabriel.stock.port.ProductFinder;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductionServiceTest {



    @Test
    void shouldCalculateProductionWhenStockIsEnough() {

        RawMaterial material = new RawMaterial();
        material.setId(1L);
        material.setCode("RM01");
        material.setName("Raw Material A");
        material.setStockQuantity(10);

        Product product = new Product();
        product.setId(1L);
        product.setCode("P01");
        product.setName("Product A");
        product.setPrice(BigDecimal.valueOf(100));

        ProductMaterial relation = new ProductMaterial();
        relation.setId(1L);
        relation.setProduct(product);
        relation.setRawMaterial(material);
        relation.setRequiredQuantity(2);

        product.setMaterials(List.of(relation));

        ProductFinder fakeFinder = new ProductFinder() {
            @Override
            public List<Product> findAllByOrderByPriceDesc() {
                return List.of(product);
            }
        };

        ProductionService service = new ProductionService(fakeFinder);

        var response = service.calculateProduction();

        assertEquals(1, response.products().size());
        assertEquals(5, response.products().get(0).quantityPossible());
        assertEquals(BigDecimal.valueOf(500), response.grandTotal());
    }

    @Test
    void shouldCalculateProductionWithTwoRawMaterials(){
        RawMaterial plastic = new RawMaterial();
        plastic.setId(1L);
        plastic.setCode("RM01");
        plastic.setName("Raw Material A");
        plastic.setStockQuantity(10);

        RawMaterial iron = new RawMaterial();
        iron.setId(2L);
        iron.setCode("RM02");
        iron.setName("Raw Material B");
        iron.setStockQuantity(9);

        Product product = new Product();
        product.setId(1L);
        product.setCode("P01");
        product.setName("Product A");
        product.setPrice(BigDecimal.valueOf(100));

        ProductMaterial relation1 = new ProductMaterial();
        relation1.setId(1L);
        relation1.setProduct(product);
        relation1.setRawMaterial(plastic);
        relation1.setRequiredQuantity(2);

        ProductMaterial relation2 = new ProductMaterial();
        relation2.setId(1L);
        relation2.setProduct(product);
        relation2.setRawMaterial(iron);
        relation2.setRequiredQuantity(3);

        product.setMaterials(List.of(relation1, relation2));

        ProductFinder fakeFinder = new ProductFinder() {
            @Override
            public List<Product> findAllByOrderByPriceDesc() {
                return List.of(product);
            }
        };

        ProductionService service = new ProductionService(fakeFinder);

        var response = service.calculateProduction();

        assertEquals(1, response.products().size());
        assertEquals(3, response.products().get(0).quantityPossible());
        assertEquals(BigDecimal.valueOf(300), response.grandTotal());
    }

    @Test
    void shouldReturnEmptyWhenStockIsInsufficient(){
        RawMaterial material = new RawMaterial();
        material.setId(1L);
        material.setCode("RM01");
        material.setName("Raw Material A");
        material.setStockQuantity(1);

        Product product = new Product();
        product.setId(1L);
        product.setCode("P01");
        product.setName("Product A");
        product.setPrice(BigDecimal.valueOf(100));

        ProductMaterial relation = new ProductMaterial();
        relation.setId(1L);
        relation.setProduct(product);
        relation.setRawMaterial(material);
        relation.setRequiredQuantity(5);

        product.setMaterials(List.of(relation));

        ProductFinder fakeFinder = new ProductFinder() {
            @Override
            public List<Product> findAllByOrderByPriceDesc() {
                return List.of(product);
            }
        };

        ProductionService service = new ProductionService(fakeFinder);

        var response = service.calculateProduction();

        assertTrue(response.products().isEmpty());
        assertEquals(BigDecimal.ZERO, response.grandTotal());
    }

    @Test
    void shouldPrioritizeMoreExpensiveProduct() {

        RawMaterial material = new RawMaterial();
        material.setId(1L);
        material.setStockQuantity(10);

        Product expensive = new Product();
        expensive.setId(1L);
        expensive.setName("Expensive");
        expensive.setPrice(BigDecimal.valueOf(200));

        Product cheap = new Product();
        cheap.setId(2L);
        cheap.setName("Cheap");
        cheap.setPrice(BigDecimal.valueOf(100));

        ProductMaterial rel1 = new ProductMaterial();
        rel1.setProduct(expensive);
        rel1.setRawMaterial(material);
        rel1.setRequiredQuantity(10);

        ProductMaterial rel2 = new ProductMaterial();
        rel2.setProduct(cheap);
        rel2.setRawMaterial(material);
        rel2.setRequiredQuantity(10);

        expensive.setMaterials(List.of(rel1));
        cheap.setMaterials(List.of(rel2));

        ProductFinder finder = () -> List.of(expensive, cheap);

        ProductionService service = new ProductionService(finder);

        var response = service.calculateProduction();

        assertEquals(1, response.products().size());
        assertEquals("Expensive", response.products().get(0).productName());
    }

    @Test
    void shouldConsumeStockFromMoreExpensiveProductFirst() {

        RawMaterial material = new RawMaterial();
        material.setStockQuantity(10);

        Product expensive = new Product();
        expensive.setName("Expensive");
        expensive.setPrice(BigDecimal.valueOf(200));

        Product cheap = new Product();
        cheap.setName("Cheap");
        cheap.setPrice(BigDecimal.valueOf(100));

        ProductMaterial rel1 = new ProductMaterial();
        rel1.setProduct(expensive);
        rel1.setRawMaterial(material);
        rel1.setRequiredQuantity(5);

        ProductMaterial rel2 = new ProductMaterial();
        rel2.setProduct(cheap);
        rel2.setRawMaterial(material);
        rel2.setRequiredQuantity(5);

        expensive.setMaterials(List.of(rel1));
        cheap.setMaterials(List.of(rel2));

        ProductFinder finder = () -> List.of(expensive, cheap);

        ProductionService service = new ProductionService(finder);

        var response = service.calculateProduction();

        assertEquals(1, response.products().size());
        assertEquals("Expensive", response.products().get(0).productName());
    }


    @Test
    void shouldIgnoreProductWithoutMaterials() {
        Product product = new Product();
        product.setName("Empty Product");
        product.setPrice(BigDecimal.valueOf(100));
        product.setMaterials(List.of());

        ProductFinder finder = () -> List.of(product);

        ProductionService service = new ProductionService(finder);

        var response = service.calculateProduction();

        assertTrue(response.products().isEmpty());
    }
}
