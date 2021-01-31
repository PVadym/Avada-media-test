package com.example.kino.config.dto;

import lombok.Data;

import java.util.List;

@Data
public class NewsDto {
    private Long id;
    private String name;
    private String description;
    private String video;
    private String mainImage;
    private List<String> images;
    private String publishDate;
}
