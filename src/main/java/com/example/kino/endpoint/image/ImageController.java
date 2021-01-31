package com.example.kino.endpoint.image;

import com.example.kino.service.api.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/images")
@RequiredArgsConstructor
public class ImageController {

    public final ImageService imageService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value ="upload")
    public ResponseEntity<?> uploadImage(@RequestParam MultipartFile file){
        return ResponseEntity.ok(imageService.uploadToLocalFileSystem(file));
    }

    @GetMapping(value = "getImage/{imageName:.+}",
            produces = {MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_GIF_VALUE,MediaType.IMAGE_PNG_VALUE})
    public @ResponseBody byte[] getImageWithMediaType(@PathVariable(name = "imageName") String fileName) {
        return imageService.getImageWithMediaType(fileName);
    }
}


