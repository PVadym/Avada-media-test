package com.example.kino.config.dto;

import lombok.Data;

import java.util.List;

@Data
public class FilmDto {

    private Long id;

    private String name;

    private String description;

    private String trailer;

    private String type;

    private SEOInfoDto seoInfo;

    private String mainImage;

    private List<String> images;
}
