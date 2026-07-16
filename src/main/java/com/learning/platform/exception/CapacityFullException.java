package com.learning.platform.exception;

public class CapacityFullException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CapacityFullException(String message) {
        super(message);
    }
}
