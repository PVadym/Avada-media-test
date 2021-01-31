package com.example.kino.config.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FilmAdminDto extends FilmDto {

    private SEOInfoDto seoInfo;
}
