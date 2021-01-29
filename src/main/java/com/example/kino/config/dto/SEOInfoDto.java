package com.example.kino.config.dto;

import lombok.Data;

import java.util.List;

@Data
public class SEOInfoDto {

    private Long id;
    private String url;
    private String title;
    private List<String> keywords;
    private String description;
}
