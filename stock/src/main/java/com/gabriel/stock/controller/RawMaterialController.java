/**
 * @author Gabri
 */

package com.gabriel.stock.controller;

import com.gabriel.stock.entity.RawMaterial;
import com.gabriel.stock.service.RawMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController
    @RequestMapping("/raw-materials")
    @RequiredArgsConstructor
    public class RawMaterialController {

        private final RawMaterialService service;

        @PostMapping
        public RawMaterial create(@RequestBody RawMaterial rawMaterial) {
            return service.create(rawMaterial);
        }

        @GetMapping

        public List<RawMaterial> findAll() {
            return service.findAll();
        }

        @GetMapping("/{id}")
        public RawMaterial findById(@PathVariable Long id) {
            return service.findById(id);
        }

        @PutMapping("/{id}")
        public RawMaterial update(@PathVariable Long id,
                                  @RequestBody RawMaterial rawMaterial) {
            return service.update(id, rawMaterial);
        }

        @DeleteMapping("/{id}")
        public void delete(@PathVariable Long id) {
            service.deleteById(id);
        }
}
