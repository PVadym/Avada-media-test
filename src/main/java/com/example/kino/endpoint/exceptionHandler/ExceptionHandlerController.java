package com.example.kino.endpoint.exceptionHandler;

import com.example.kino.exeption.FileUploadArgumentException;
import com.example.kino.exeption.ResourceAlreadyExistsException;
import com.example.kino.exeption.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;


@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String duplicateEntity(Exception e) {
        log.error("Error: " + e.getMessage(), e);
        return e.getMessage();
    }

    @ExceptionHandler({ResourceNotFoundException.class,
            EmptyResultDataAccessException.class, EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundEntity(Exception e) {
        log.error("Error: " + e.getMessage(), e);
        return e.getMessage();
    }

    @ExceptionHandler(FileUploadArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String uploadImageError(Exception e) {
        log.error("Error: " + e.getMessage(), e);
        return e.getMessage();
    }

}
