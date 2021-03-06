package com.exceptions;

import java.util.function.Supplier;

/**
 * Custom exception for NoSuchElementException
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

public class ResourceNotFoundException extends RuntimeException  {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
