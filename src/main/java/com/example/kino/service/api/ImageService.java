package com.example.kino.service.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    String uploadToLocalFileSystem(MultipartFile file);

    byte[] getImageWithMediaType(String imageName) throws IOException;

    boolean deleteImage(String imageName);
}
