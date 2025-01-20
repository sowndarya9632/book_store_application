package com.book_store_application.handler;

import com.book_store_application.exception.CustomBadCredentialsException;
import com.book_store_application.exception.CustomException;
import com.book_store_application.exception.InvalidTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(Integer.valueOf("VALIDATION_ERROR"))
                                .businessErrorDescription("Validation failed for one or more fields.")
                                .error(errors.toString())
                                .build()
                );
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> handleCustomException(CustomException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(Integer.valueOf("NOT_FOUND"))
                                .businessErrorDescription("The requested resource was not found.")
                                .error(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidTokenException(InvalidTokenException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(Integer.valueOf("INVALID_TOKEN"))
                                .businessErrorDescription("The provided token is invalid or expired.")
                                .error(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(CustomBadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleCustomBadCredentialsException(CustomBadCredentialsException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(Integer.valueOf("BAD_CREDENTIALS"))
                                .businessErrorDescription("Authentication failed due to invalid credentials.")
                                .error(exception.getMessage())
                                .build()
                );
    }
}
