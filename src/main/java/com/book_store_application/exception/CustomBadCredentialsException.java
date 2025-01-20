package com.book_store_application.exception;

public class CustomBadCredentialsException extends RuntimeException {
    public CustomBadCredentialsException(String message) {
        super(message);
    }
}
