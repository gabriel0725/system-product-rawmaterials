/**
 * @author Gabri
 */

package com.gabriel.stock.exception;

/**
 * Exceção personalizada para indicar que um recurso
 * não foi encontrado no sistema.
 *
 * Essa classe estende RuntimeException,
 * o que significa que é uma exceção NÃO verificada (unchecked).
 *
 * Exceções unchecked:
 * - Não precisam ser declaradas com "throws"
 * - São lançadas normalmente quando há erro de regra de negócio
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Construtor que recebe uma mensagem personalizada.
     *
     * A mensagem será repassada para a classe pai (RuntimeException)
     * através do super(message).
     *
     * Essa mensagem poderá ser capturada pelo GlobalExceptionHandler
     * e enviada como resposta ao cliente.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}