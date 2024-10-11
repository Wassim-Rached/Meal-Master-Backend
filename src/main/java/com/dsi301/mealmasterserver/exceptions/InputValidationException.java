package com.dsi301.mealmasterserver.exceptions;

public class InputValidationException extends RuntimeException {
    public InputValidationException(String message) {
        super(message);
    }
}