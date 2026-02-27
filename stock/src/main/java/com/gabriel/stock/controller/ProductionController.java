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

@RestController
@RequestMapping("/production")
@RequiredArgsConstructor
public class ProductionController {

    private final ProductionService service;

    @GetMapping("/suggestion")
    public ProductionResponse calculate() {
        return service.calculateProduction();
    }
}
