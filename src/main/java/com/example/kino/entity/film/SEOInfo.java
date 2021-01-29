package com.example.kino.entity.film;

import com.example.kino.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "seo_info")
@Data
@NoArgsConstructor
public class SEOInfo extends BaseEntity {

    private String url;

    private String title;

    @ElementCollection
    private List<String> keywords;

    @Column(columnDefinition = "TEXT")
    private String description;

}
