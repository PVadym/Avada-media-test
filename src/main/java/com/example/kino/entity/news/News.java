package com.example.kino.entity.news;

import com.example.kino.entity.BaseEntity;
import com.example.kino.entity.film.SEOInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "news")
@Data
@NoArgsConstructor
public class News extends BaseEntity {

    private String name;

    private String description;

    private LocalDate publishDate;

    private String mainImage;

    @ElementCollection
    private List<String> images;

    private String video;

    @OneToOne(cascade = CascadeType.ALL)
    private SEOInfo seoInfo;

    @Column(nullable = false)
    private boolean active = false;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime creationDate;
}
