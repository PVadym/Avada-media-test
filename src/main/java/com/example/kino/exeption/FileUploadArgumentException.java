package com.example.kino.exeption;

public class FileUploadArgumentException extends RuntimeException{

    public FileUploadArgumentException(String message) {
        super(message);
    }
}
