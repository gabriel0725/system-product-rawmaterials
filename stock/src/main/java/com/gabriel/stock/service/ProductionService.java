/**
 * @author Gabri
 */

package com.gabriel.stock.service;

import com.gabriel.stock.dto.production.ProductProductionDTO;
import com.gabriel.stock.dto.production.ProductionResponse;
import com.gabriel.stock.entity.Product;
import com.gabriel.stock.entity.ProductMaterial;
import com.gabriel.stock.port.ProductFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Serviço responsável por calcular a capacidade máxima de produção
 * com base no estoque atual de matérias-primas.
 *
 * Estratégia utilizada:
 * - Os produtos são processados em ordem decrescente de preço.
 * - Isso implementa uma abordagem de Greedy Algorithm,
 *   priorizando produtos de maior valor unitário para maximizar
 *   o faturamento total.
 *
 * Conceito matemático aplicado:
 * - Para cada produto, calcula-se o limite de produção baseado
 *   na matéria-prima mais restritiva (menor disponibilidade relativa).
 *
 * Fórmula base utilizada:
 *
 *      unidadesPossiveis = estoqueDisponivel / quantidadeNecessaria
 *
 * O menor valor entre todas as matérias-primas do produto
 * determina o total máximo fabricável.
 */
@Service
@RequiredArgsConstructor
public class ProductionService {

    /**
     * Porta de acesso para buscar produtos.
     * Retorna produtos ordenados por preço decrescente.
     */
    private final ProductFinder productRepository;

    /**
     * Calcula a produção máxima possível e o faturamento total.
     *
     * Etapas do algoritmo:
     *
     * 1. Buscar produtos ordenados por maior preço.
     * 2. Criar um mapa auxiliar representando o estoque disponível.
     * 3. Para cada produto:
     *    - Calcular quantas unidades podem ser produzidas.
     *    - Atualizar o estoque.
     *    - Calcular faturamento.
     * 4. Retornar lista detalhada + total geral.
     *
     * @return ProductionResponse contendo os produtos produzidos
     *         e o faturamento total.
     */
    public ProductionResponse calculateProduction() {

        // Produtos ordenados por preço DESC
        // Isso garante que o produto mais valioso seja processado primeiro.
        List<Product> products = productRepository.findAllByOrderByPriceDesc();

        // Mapa auxiliar para controlar o estoque disponível
        // <ID da matéria-prima, quantidade disponível>
        Map<Long, Integer> stockMap = new HashMap<>();

        /*
         * Preenchimento do mapa de estoque.
         *
         * Percorre todos os produtos e todas as suas matérias-primas
         * para capturar o estoque atual.
         */
        products.stream()
                .flatMap(p -> p.getMaterials().stream())
                .forEach(pm -> stockMap.put(
                        pm.getRawMaterial().getId(),
                        pm.getRawMaterial().getStockQuantity()
                ));

        List<ProductProductionDTO> result = new ArrayList<>();

        // Faturamento total acumulado
        BigDecimal grandTotal = BigDecimal.ZERO;

        /*
         * Loop principal:
         * Para cada produto, calcula a produção máxima possível.
         */
        for (Product product : products) {

            /*
             * maxUnits começa com valor máximo possível.
             * Ele será reduzido conforme encontramos limites
             * nas matérias-primas.
             */
            int maxUnits = Integer.MAX_VALUE;

            /*
             * Cálculo matemático central:
             *
             * Para cada matéria-prima do produto:
             *
             *      possible = estoqueDisponivel / quantidadeNecessaria
             *
             * Exemplo:
             * Se há 10 unidades em estoque
             * e o produto exige 2 por unidade:
             *
             *      10 / 2 = 5 unidades possíveis
             *
             * O menor valor encontrado define o limite real.
             * Isso ocorre porque a matéria-prima mais escassa
             * limita a produção total.
             */
            for (ProductMaterial pm : product.getMaterials()) {

                int available = stockMap.get(pm.getRawMaterial().getId());

                // Divisão inteira → representa número máximo de unidades completas
                int possible = available / pm.getRequiredQuantity();

                /*
                 * Math.min garante que estamos pegando
                 * o fator limitante da produção.
                 *
                 * Matematicamente:
                 *
                 * maxUnits = min(possible1, possible2, ..., possibleN)
                 */
                maxUnits = Math.min(maxUnits, possible);
            }

            /*
             * Só produz se:
             * - for possível produzir ao menos 1 unidade
             * - e o valor não permaneceu infinito
             */
            if (maxUnits > 0 && maxUnits != Integer.MAX_VALUE) {

                /*
                 * Atualização do estoque:
                 *
                 * novaQuantidade =
                 *     estoqueAtual - (quantidadeNecessaria × unidadesProduzidas)
                 *
                 * Isso evita que outro produto utilize matéria-prima já consumida.
                 */
                for (ProductMaterial pm : product.getMaterials()) {

                    Long materialId = pm.getRawMaterial().getId();

                    stockMap.put(
                            materialId,
                            stockMap.get(materialId)
                                    - (pm.getRequiredQuantity() * maxUnits)
                    );
                }

                /*
                 * Cálculo do faturamento do produto:
                 *
                 * totalProduto = preçoUnitário × quantidadeProduzida
                 */
                BigDecimal total = product.getPrice()
                        .multiply(BigDecimal.valueOf(maxUnits));

                /*
                 * Acumula no faturamento geral:
                 *
                 * faturamentoTotal = faturamentoTotal + totalProduto
                 */
                grandTotal = grandTotal.add(total);

                /*
                 * Armazena resultado detalhado
                 */
                result.add(new ProductProductionDTO(
                        product.getName(),
                        maxUnits,
                        product.getPrice(),
                        total
                ));
            }
        }

        /*
         * Retorna:
         * - Lista de produtos produzidos
         * - Faturamento total acumulado
         */
        return new ProductionResponse(result, grandTotal);
    }
}