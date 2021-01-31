package com.example.kino.service.impl;

import com.example.kino.exeption.FileUploadArgumentException;
import com.example.kino.exeption.ResourceNotFoundException;
import com.example.kino.service.api.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;


@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    @Value("${imageContent.uploadBase}")
    private String uploadBase;
    @Value("${imageContent.uploadImagePath}")
    private String uploadImageFolder;

    private Path storageDirectory;

    @PostConstruct
    public void initDirectory() {
        try {
            Path path = Paths.get(uploadBase + uploadImageFolder);
            if(!Files.exists(path)){
                this.storageDirectory = Files.createDirectories(path);
            } else {
                this.storageDirectory = path;
            }
        } catch (Exception e) {
            log.error("Can't created directory for media! Error : {}", e.getMessage());
        }
    }

    @Override
    public String uploadToLocalFileSystem(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID().toString() + "_" +StringUtils.cleanPath(file.getOriginalFilename());
            Path destination = Paths.get(storageDirectory.toString() + "/" + fileName);
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (Exception e) {
            log.warn("Can't created media! Error : {}",  e.getMessage());
        }
        throw new FileUploadArgumentException("Error upload file");
    }

    @Override
    public  byte[] getImageWithMediaType(String imageName) {
        Path destination = Paths.get(storageDirectory.toString()+"/"+imageName);

        try {
            return IOUtils.toByteArray(destination.toUri());
        } catch (IOException e) {
            throw  new ResourceNotFoundException("Coldn't load image = " + imageName);
        }
    }

    @Override
    public  boolean deleteImage(String imageName) {
        Path destination = Paths.get(storageDirectory.toString()+"/"+imageName);
        log.info("Image to delete = {}", destination);
        try {
            return Files.deleteIfExists(destination);
        } catch (IOException e) {
            log.warn("Can't delete image = {}", destination);
        }
        return false;
    }



}
