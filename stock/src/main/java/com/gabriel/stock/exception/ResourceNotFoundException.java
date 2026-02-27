/**
 * @author Gabri
 */

package com.gabriel.stock.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
