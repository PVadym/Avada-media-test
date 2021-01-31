package com.example.kino.config.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NewsAdminDto extends NewsDto{

    private SEOInfoDto seoInfo;
    private boolean active;
    private String creationDate;
}
