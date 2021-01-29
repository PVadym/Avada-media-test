package com.example.kino.entity.film;

import com.example.kino.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "films")
@Data
@NoArgsConstructor
public class Film extends BaseEntity {

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String trailer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FilmType type;

    @OneToOne(cascade = CascadeType.ALL)
    private SEOInfo seoInfo;

    private String mainImage;

    @ElementCollection
    private List<String> images;

    @Enumerated(EnumType.STRING)
    private FilmStatus status;
}
