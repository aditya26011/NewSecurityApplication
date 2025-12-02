package com.coding.security.securityApplication.advice;

import com.coding.security.securityApplication.exceptions.ResourceNotFound;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ApiError> ResourceNotFoundException(ResourceNotFound exception){
        ApiError apiError=new ApiError(exception.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>( apiError,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handelAuthenticationException(AuthenticationException ex){
        ApiError apiError=new ApiError(ex.getLocalizedMessage(),HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(apiError,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError> handelJwtxception(JwtException ex){
        ApiError apiError=new ApiError(ex.getLocalizedMessage(),HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(apiError,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handelAccessDeniedException(AccessDeniedException ex){
        ApiError apiError=new ApiError(ex.getLocalizedMessage(),HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(apiError,HttpStatus.FORBIDDEN);
    }

}
