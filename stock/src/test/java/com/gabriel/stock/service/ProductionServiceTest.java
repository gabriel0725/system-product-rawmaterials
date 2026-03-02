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

/**
 * Testes unitários da classe ProductionService.
 *
 * Tipo de teste:
 * - Unit Test
 * - Não acessa banco de dados
 * - Usa implementação Fake do ProductFinder
 *
 * Estratégia:
 * Criados objetos manualmente em memória
 * e validamos apenas a regra de negócio do cálculo.
 *
 * Aqui estamos testando exclusivamente:
 * - Cálculo matemático de produção
 * - Consumo de estoque
 * - Priorização por preço
 */
public class ProductionServiceTest {

    /**
     * Cenário:
     * Existe 1 matéria-prima com estoque suficiente.
     *
     * Estoque: 10 unidades
     * Produto exige: 2 unidades por produção
     *
     * Cálculo matemático:
     * 10 / 2 = 5 unidades possíveis
     *
     * Faturamento:
     * 5 × 100 = 500
     */
    @Test
    void shouldCalculateProductionWhenStockIsEnough() {

        RawMaterial material = new RawMaterial();
        material.setId(1L);
        material.setStockQuantity(10);

        Product product = new Product();
        product.setId(1L);
        product.setPrice(BigDecimal.valueOf(100));

        ProductMaterial relation = new ProductMaterial();
        relation.setProduct(product);
        relation.setRawMaterial(material);
        relation.setRequiredQuantity(2);

        product.setMaterials(List.of(relation));

        // Fake que simula retorno do repositório
        ProductFinder fakeFinder = () -> List.of(product);

        ProductionService service = new ProductionService(fakeFinder);

        var response = service.calculateProduction();

        // Verifica se apenas 1 produto foi produzido
        assertEquals(1, response.products().size());

        // Verifica cálculo matemático: 10 / 2 = 5
        assertEquals(5, response.products().get(0).quantityPossible());

        // Verifica faturamento: 5 × 100 = 500
        assertEquals(BigDecimal.valueOf(500), response.grandTotal());
    }

    /**
     * Cenário com duas matérias-primas.
     *
     * Estoque:
     * - Plástico: 10 (exige 2) → 10 / 2 = 5
     * - Ferro: 9 (exige 3) → 9 / 3 = 3
     *
     * O menor valor limita a produção:
     * min(5, 3) = 3 unidades
     *
     * Faturamento:
     * 3 × 100 = 300
     */
    @Test
    void shouldCalculateProductionWithTwoRawMaterials(){

        RawMaterial plastic = new RawMaterial();
        plastic.setStockQuantity(10);

        RawMaterial iron = new RawMaterial();
        iron.setStockQuantity(9);

        Product product = new Product();
        product.setPrice(BigDecimal.valueOf(100));

        ProductMaterial relation1 = new ProductMaterial();
        relation1.setProduct(product);
        relation1.setRawMaterial(plastic);
        relation1.setRequiredQuantity(2);

        ProductMaterial relation2 = new ProductMaterial();
        relation2.setProduct(product);
        relation2.setRawMaterial(iron);
        relation2.setRequiredQuantity(3);

        product.setMaterials(List.of(relation1, relation2));

        ProductFinder fakeFinder = () -> List.of(product);

        ProductionService service = new ProductionService(fakeFinder);

        var response = service.calculateProduction();

        assertEquals(1, response.products().size());

        // Matéria-prima mais escassa limita produção
        assertEquals(3, response.products().get(0).quantityPossible());

        assertEquals(BigDecimal.valueOf(300), response.grandTotal());
    }

    /**
     * Cenário:
     * Estoque insuficiente.
     *
     * Estoque: 1
     * Exige: 5
     *
     * 1 / 5 = 0 (divisão inteira)
     *
     * Nenhuma unidade pode ser produzida.
     */
    @Test
    void shouldReturnEmptyWhenStockIsInsufficient(){

        RawMaterial material = new RawMaterial();
        material.setStockQuantity(1);

        Product product = new Product();
        product.setPrice(BigDecimal.valueOf(100));

        ProductMaterial relation = new ProductMaterial();
        relation.setProduct(product);
        relation.setRawMaterial(material);
        relation.setRequiredQuantity(5);

        product.setMaterials(List.of(relation));

        ProductFinder fakeFinder = () -> List.of(product);

        ProductionService service = new ProductionService(fakeFinder);

        var response = service.calculateProduction();

        // Nenhum produto produzido
        assertTrue(response.products().isEmpty());

        // Faturamento zero
        assertEquals(BigDecimal.ZERO, response.grandTotal());
    }

    /**
     * Valida a regra de negócio principal:
     *
     * Produtos devem ser priorizados por maior preço.
     *
     * Mesmo que ambos usem a mesma matéria-prima,
     * o mais caro deve ser produzido primeiro.
     */
    @Test
    void shouldPrioritizeMoreExpensiveProduct() {

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

        // Garante que o produto caro foi produzido primeiro
        assertEquals("Expensive", response.products().get(0).productName());
    }

    /**
     * Testa se o estoque é consumido
     * pelo produto mais caro antes do barato.
     *
     * Isso valida o comportamento guloso (Greedy Algorithm).
     */
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

    /**
     * Se o produto não possui matérias-primas,
     * ele deve ser ignorado.
     *
     * Isso evita divisão por zero
     * e evita produção inválida.
     */
    @Test
    void shouldIgnoreProductWithoutMaterials() {

        Product product = new Product();
        product.setPrice(BigDecimal.valueOf(100));
        product.setMaterials(List.of());

        ProductFinder finder = () -> List.of(product);

        ProductionService service = new ProductionService(finder);

        var response = service.calculateProduction();

        assertTrue(response.products().isEmpty());
    }
}