package com.example.kino.service.api;

import org.springframework.web.multipart.MultipartFile;

/**
 * Interface for image logic
 */
public interface ImageService {
    String uploadToLocalFileSystem(MultipartFile file);

    byte[] getImageWithMediaType(String imageName);

    boolean deleteImage(String imageName);
}
