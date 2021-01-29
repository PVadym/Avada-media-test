package com.example.kino.config.dto;

import lombok.Data;

import java.util.List;

@Data
public class NewsDto {
    private Long id;

    private String name;

    private String description;

    private String video;

    private SEOInfoDto seoInfo;

    private String mainImage;

    private List<String> images;

    private boolean active;

    private String publishDate;

    private String creationDate;
}
