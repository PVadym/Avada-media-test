package com.example.kino.exeption;

public class ResourceAlreadyExistsException extends RuntimeException{

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
