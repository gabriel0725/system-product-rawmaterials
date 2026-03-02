/**
 * @author Gabri
 */

package com.gabriel.stock.controller;

import com.gabriel.stock.dto.production.ProductionResponse;
import com.gabriel.stock.service.ProductionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller REST responsável pelas operações
 * relacionadas ao cálculo de produção.
 *
 * Esse controller expõe um endpoint que calcula
 * sugestões ou possibilidades de produção com base
 * nas matérias-primas disponíveis.
 */
@RestController

/**
 * Define o endpoint base da classe.
 * Todas as rotas começam com /production.
 */
@RequestMapping("/production")
@RequiredArgsConstructor
public class ProductionController {

    /**
     * Service responsável pela regra de negócio
     * relacionada ao cálculo de produção.
     */
    private final ProductionService service;

    /**
     * Endpoint responsável por calcular a sugestão de produção.
     *
     * GET /production/suggestion
     *
     * Não recebe parâmetros.
     * Retorna um DTO contendo as informações calculadas.
     */
    @GetMapping("/suggestion")
    public ProductionResponse calculate() {

        /**
         * Delegação da regra de negócio para a camada de serviço.
         * O controller apenas expõe o endpoint.
         */
        return service.calculateProduction();
    }
}