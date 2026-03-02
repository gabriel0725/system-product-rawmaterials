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

/**
 * Camada de serviço responsável pelas regras de negócio
 * relacionadas à entidade RawMaterial.
 *
 * @Service:
 * Indica que essa classe é um componente gerenciado pelo Spring.
 */
@Service
@RequiredArgsConstructor
public class RawMaterialService {

    /**
     * Repository responsável pela persistência
     * da entidade RawMaterial.
     */
    private final RawMaterialRepository repository;

    /**
     * Cria uma nova matéria-prima no banco de dados.
     *
     * @param rawMaterial Entidade recebida para persistência.
     * @return Entidade salva com ID gerado.
     */
    public RawMaterial create(RawMaterial rawMaterial) {
        return repository.save(rawMaterial);
    }

    /**
     * Retorna todas as matérias-primas cadastradas.
     *
     * @return Lista de RawMaterial.
     */
    public List<RawMaterial> findAll() {
        return repository.findAll();
    }

    /**
     * Busca uma matéria-prima pelo ID.
     *
     * Caso não exista, lança ResourceNotFoundException,
     * que será tratada pelo GlobalExceptionHandler
     * retornando HTTP 404.
     *
     * @param id Identificador da matéria-prima.
     * @return Entidade encontrada.
     */
    public RawMaterial findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Raw material not found"));
    }

    /**
     * Atualiza os dados de uma matéria-prima existente.
     *
     * Processo:
     * 1. Verifica se a entidade existe.
     * 2. Atualiza apenas os campos permitidos.
     * 3. Salva novamente no banco.
     *
     * @param id ID da matéria-prima a ser atualizada.
     * @param updatedRawMaterial Objeto contendo os novos dados.
     * @return Entidade atualizada.
     */
    public RawMaterial update(Long id, RawMaterial updatedRawMaterial) {

        // Garante que a matéria-prima existe
        RawMaterial existingRawMaterial = findById(id);

        // Atualiza campos controlados
        existingRawMaterial.setCode(updatedRawMaterial.getCode());
        existingRawMaterial.setName(updatedRawMaterial.getName());
        existingRawMaterial.setStockQuantity(updatedRawMaterial.getStockQuantity());

        return repository.save(existingRawMaterial);
    }

    /**
     * Remove uma matéria-prima pelo ID.
     *
     * @param id Identificador da matéria-prima a ser removida.
     */
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}