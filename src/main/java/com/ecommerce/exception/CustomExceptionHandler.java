package com.ecommerce.exception;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public  ResponseEntity<ProblemDetail> handleSecurityException(Exception e){
        
        ProblemDetail errorDetail = null;
        

        //excepcion credenciales incorrectas
        if(e instanceof BadCredentialsException){
            
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
            errorDetail.setProperty("access_denied_reason", "Authentication failure");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetail);
        }

        //excepcion accesso denegado (falta verificar)
        if(e instanceof AccessDeniedException) {

            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
            errorDetail.setProperty("access_denied_reason", "not authorized");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDetail);

        }

        //excepcion token incorrecto (falta verificar)
        if(e instanceof SignatureException) {

            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
            errorDetail.setProperty("access_denied_reason", "JWT signature not valid");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDetail);
        }

        //excepcion al buscar un recurso
        if(e instanceof ResourceNotFoundException) {

            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
            errorDetail.setProperty("error_type", "resource not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetail);
        }

        if (e instanceof DatabaseException) {

            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

            errorDetail.setProperty("error_type", "Database error");
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetail);
        }

        //errores diferentes a los clasificados
        errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetail);
    }
}
