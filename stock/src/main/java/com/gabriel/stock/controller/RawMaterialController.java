/**
 * @author Gabri
 */

package com.gabriel.stock.controller;

import com.gabriel.stock.entity.RawMaterial;
import com.gabriel.stock.service.RawMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST responsável por expor os endpoints
 * relacionados à entidade RawMaterial (matéria-prima).
 *
 * @RestController:
 * Indica que a classe é um controller REST e que os métodos
 * retornarão dados diretamente no corpo da resposta (JSON).
 *
 * @RequestMapping("/raw-materials"):
 * Define o endpoint base para todos os métodos da classe.
 */
@RestController
@RequestMapping("/raw-materials")
@RequiredArgsConstructor
public class RawMaterialController {

    /**
     * Service responsável pela regra de negócio da matéria-prima.
     * É injetado automaticamente pelo Spring.
     */
    private final RawMaterialService service;

    /**
     * Endpoint para criar uma nova matéria-prima.
     *
     * POST /raw-materials
     *
     * @RequestBody:
     * Converte o JSON enviado na requisição em um objeto RawMaterial.
     */
    @PostMapping
    public RawMaterial create(@RequestBody RawMaterial rawMaterial) {
        return service.create(rawMaterial);
    }

    /**
     * Endpoint para listar todas as matérias-primas.
     *
     * GET /raw-materials
     */
    @GetMapping
    public List<RawMaterial> findAll() {
        return service.findAll();
    }

    /**
     * Endpoint para buscar uma matéria-prima pelo ID.
     *
     * GET /raw-materials/{id}
     *
     * @PathVariable:
     * Captura o valor do ID presente na URL.
     */
    @GetMapping("/{id}")
    public RawMaterial findById(@PathVariable Long id) {
        return service.findById(id);
    }

    /**
     * Endpoint para atualizar uma matéria-prima existente.
     *
     * PUT /raw-materials/{id}
     *
     * Recebe o ID pela URL e os novos dados pelo corpo da requisição.
     */
    @PutMapping("/{id}")
    public RawMaterial update(@PathVariable Long id,
                              @RequestBody RawMaterial rawMaterial) {
        return service.update(id, rawMaterial);
    }

    /**
     * Endpoint para remover uma matéria-prima pelo ID.
     *
     * DELETE /raw-materials/{id}
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}