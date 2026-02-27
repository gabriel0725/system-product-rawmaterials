/**
 * @author Gabri
 */

package com.gabriel.stock.service;

import com.gabriel.stock.entity.RawMaterial;
import com.gabriel.stock.exception.ResourceNotFoundException;
import com.gabriel.stock.repository.RawMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RawMaterialService {

    private final RawMaterialRepository repository;

    public RawMaterial create(RawMaterial rawMaterial) {
        return repository.save(rawMaterial);
    }

    public List<RawMaterial> findAll() {
        return repository.findAll();
    }

    public RawMaterial findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Raw material not found"));
    }

    public RawMaterial update(Long id, RawMaterial updatedRawMaterial) {
        RawMaterial existingRawMaterial = findById(id);
        existingRawMaterial.setCode(updatedRawMaterial.getCode());
        existingRawMaterial.setName(updatedRawMaterial.getName());
        existingRawMaterial.setStockQuantity(updatedRawMaterial.getStockQuantity());
        return repository.save(existingRawMaterial);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
