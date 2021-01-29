package com.example.kino.endpoint.image;

import com.example.kino.service.api.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/images")
@RequiredArgsConstructor
public class ImageController {

    public final ImageService imageService;

    @PostMapping(value ="upload")
    public ResponseEntity<?> uploadImage(@RequestParam MultipartFile file){
        return imageService.uploadToLocalFileSystem(file);
    }
    @GetMapping(
            value = "getImage/{imageName:.+}",
            produces = {MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_GIF_VALUE,MediaType.IMAGE_PNG_VALUE}
    )
    public @ResponseBody byte[] getImageWithMediaType(@PathVariable(name = "imageName") String fileName) throws IOException {
        return imageService.getImageWithMediaType(fileName);
    }
}


