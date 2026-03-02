/**
 * @author Gabri
 */

package com.gabriel.stock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Classe responsável por tratar exceções de forma global
 * em toda a aplicação.
 *
 * @RestControllerAdvice:
 * Combina:
 * - @ControllerAdvice → Permite interceptar exceções globalmente
 * - @ResponseBody → Retorna automaticamente a resposta em JSON
 *
 * Isso evita a necessidade de tratar exceções manualmente
 * em cada controller.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Metodo responsável por tratar exceções do tipo ResourceNotFoundException.
     *
     * @ExceptionHandler(ResourceNotFoundException.class):
     * Indica que este metodo será chamado automaticamente
     * sempre que essa exceção for lançada em qualquer controller.
     *
     * @ResponseStatus(HttpStatus.NOT_FOUND):
     * Define que o status HTTP retornado será 404.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleNotFound(ResourceNotFoundException ex) {

        /**
         * Retorna um Map como corpo da resposta.
         *
         * Estrutura do JSON retornado:
         * {
         *   "timestamp": "data e hora atual",
         *   "message": "mensagem da exceção"
         * }
         *
         * LocalDateTime.now():
         * Registra o momento exato em que o erro ocorreu.
         *
         * ex.getMessage():
         * Retorna a mensagem personalizada definida na exceção.
         */
        return Map.of(
                "timestamp", LocalDateTime.now(),
                "message", ex.getMessage()
        );
    }
}