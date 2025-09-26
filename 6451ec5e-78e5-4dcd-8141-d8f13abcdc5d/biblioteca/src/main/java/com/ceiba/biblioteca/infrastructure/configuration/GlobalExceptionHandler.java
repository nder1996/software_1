package com.ceiba.biblioteca.infrastructure.configuration;

import com.ceiba.biblioteca.domain.exception.DomainException;
import com.ceiba.biblioteca.domain.exception.PrestamoNoEncontradoException;
import com.ceiba.biblioteca.infrastructure.adapter.in.web.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PrestamoNoEncontradoException.class)
    public ResponseEntity<ErrorResponseDto> handlePrestamoNoEncontrado(PrestamoNoEncontradoException e) {
        ErrorResponseDto error = new ErrorResponseDto(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponseDto> handleDomainException(DomainException e) {
        ErrorResponseDto error = new ErrorResponseDto(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception e) {
        ErrorResponseDto error = new ErrorResponseDto("Error interno del servidor");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}